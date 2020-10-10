/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.soyumall.capacity.core.factory;

import cn.soyumall.capacity.annoation.CommonMessage;
import cn.soyumall.capacity.annoation.OrderMessage;
import cn.soyumall.capacity.annoation.TransactionMessage;
import cn.soyumall.capacity.core.strategy.SendMessageStrategy;
import cn.soyumall.capacity.core.utils.ApplicationContextUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.context.ApplicationContext;


/**
 * ClassName: SendMessageFactory
 */
public class SendMessageFactory {
    private SendMessageFactory() {
    }

    public static void sendMessage(Integer startDeliverTime, DefaultMQProducer producer, CommonMessage commonMessage, byte[] bytes, ApplicationContext applicationContext) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = cn.soyumall.capacity.core.factory.MessageFactory.createMessage (commonMessage, bytes);
        if (null != startDeliverTime) {
            message.setDelayTimeLevel(startDeliverTime);
        }
        SendMessageStrategy.send (commonMessage, producer, message, applicationContext);
    }

    public static void sendMessage(DefaultMQProducer orderProducer, OrderMessage orderMessage, byte[] bytes) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = cn.soyumall.capacity.core.factory.MessageFactory.createMessage (orderMessage, bytes);
        orderProducer.send (message);
    }

    public static void sendMessage(TransactionMQProducer transactionProducer, TransactionMessage transactionMessage,
                                   byte[] bytes, ApplicationContext applicationContext) throws MQClientException {
        Message message = cn.soyumall.capacity.core.factory.MessageFactory.createMessage (transactionMessage, bytes);
        transactionProducer.sendMessageInTransaction (message, ApplicationContextUtils.getLocalTransactionExecuter (applicationContext,
                transactionMessage.executer ()));
    }
}
