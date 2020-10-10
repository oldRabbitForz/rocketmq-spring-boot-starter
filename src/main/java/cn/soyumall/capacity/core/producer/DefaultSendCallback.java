package cn.soyumall.capacity.core.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

@Slf4j
public class DefaultSendCallback implements SendCallback {
    public void onSuccess(SendResult sendResult) {
        log.info("Message sent successfully sendResult{}", sendResult);
    }

    public void onException(Throwable throwable) {
        log.info("Failed to send message context{}", throwable.getMessage());
    }
}
