package cn.soyumall.capacity.core.producer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class DefaultLocalTransactionProcess implements TransactionListener {

    private LocalTransactionState state;
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();

    public DefaultLocalTransactionProcess(LocalTransactionState state) {
        this.state = state;
    }
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info(">>>> Execute local transaction message:{}>>>>", message);
        try {

            log.info("【本地业务执行完毕】 msg:{}, Object:{}", message, o);
            int value = transactionIndex.getAndIncrement();
            int status = value % 3;
            localTrans.put(message.getTransactionId()+"", status);

            return LocalTransactionState.UNKNOW;

        } catch (Exception e) {

            e.printStackTrace();

            log.error("【执行本地业务异常】 exception message:{}", e.getMessage());

            return LocalTransactionState.ROLLBACK_MESSAGE;

        }
    }

    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

        Integer status = (Integer) localTrans.get(messageExt.getTransactionId());

        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
