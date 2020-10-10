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

package cn.soyumall.capacity.core.strategy;

import cn.soyumall.capacity.annoation.CommonMessage;
import cn.soyumall.capacity.annoation.OrderMessage;
import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.annoation.TransactionMessage;
import cn.soyumall.capacity.core.factory.ProducerConsumerFactory;
import cn.soyumall.capacity.core.factory.SendMessageFactory;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: ProducerStrategy
 */
public class ProducerStrategy {
	private ProducerStrategy() {
	}

	public static void statsSendMessage(Integer startDeliverTime, java.util.Map<String, Object> consumerContainer,
                                        RocketMessage rocketMessage, Object message,
                                        byte[] bytes, ApplicationContext applicationContex) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		if (message instanceof CommonMessage) {
			CommonMessage commonMessage = (CommonMessage) message;
			DefaultMQProducer producer = ProducerConsumerFactory.getProducer(consumerContainer, rocketMessage, commonMessage);
			SendMessageFactory.sendMessage(startDeliverTime,producer, commonMessage, bytes, applicationContex);
			return;
		}
		if (message instanceof OrderMessage) {
			OrderMessage orderMessage = (OrderMessage) message;
			DefaultMQProducer orderProducer = ProducerConsumerFactory.getProducer(consumerContainer, rocketMessage, orderMessage);
			SendMessageFactory.sendMessage(orderProducer, orderMessage, bytes);
			return;
		}
		if (message instanceof TransactionMessage) {
			TransactionMessage transactionMessage = (TransactionMessage) message;
			TransactionMQProducer transactionProducer = ProducerConsumerFactory.getProducer(consumerContainer,
					rocketMessage, transactionMessage);
			SendMessageFactory.sendMessage(transactionProducer, transactionMessage, bytes, applicationContex);
		}
	}
}
