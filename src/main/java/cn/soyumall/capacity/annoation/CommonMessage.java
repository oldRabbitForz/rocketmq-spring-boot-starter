package cn.soyumall.capacity.annoation;

import cn.soyumall.capacity.core.producer.DefaultSendCallback;
import org.apache.rocketmq.client.impl.CommunicationMode;
import org.apache.rocketmq.client.producer.SendCallback;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommonMessage {
    /**
     * Message 所属的 Topic
     *
     * @return String
     */
    String topic() default "";

    /**
     * 订阅指定 Topic 下的 Tags：
     * 1. * 表示订阅所有消息
     * 2. TagA || TagB || TagC 表示订阅 TagA 或 TagB 或 TagC 的消息
     *
     * @return String
     */
    String tag() default "*";

    /**
     * 消息发送类型 默认异步
     *
     * @return MessageSendType
     */
    CommunicationMode messageSendType() default CommunicationMode.ASYNC;

    /**
     * 自定义SendCallback类
     *
     * @return callback
     */
    Class<? extends SendCallback> callback() default DefaultSendCallback.class;
}
