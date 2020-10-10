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
import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.annoation.TransactionMessage;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

/**
 * ClassName: ProducerConsumerFactory
 */
public class ProducerConsumerFactory {
	private ProducerConsumerFactory() {
	}

	public static String getProducerConsumerKey(RocketMessage rocketMessage, CommonMessage commonMessage) {
		return rocketMessage.groupID() +
				commonMessage.topic() +
				commonMessage.tag();
	}

	public static String getProducerConsumerKey(RocketMessage rocketMessage, OrderMessage orderMessage) {
		return rocketMessage.groupID() +
				orderMessage.topic() +
				orderMessage.tag();
	}

	public static String getProducerConsumerKey(RocketMessage rocketMessage, TransactionMessage transactionMessage) {
		return rocketMessage.groupID() +
				transactionMessage.topic() +
				transactionMessage.tag();
	}

	public static DefaultMQProducer getProducer(java.util.Map<String, Object> consumerContainer, RocketMessage rocketMessage, CommonMessage commonMessage){
		String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, commonMessage);
		return (DefaultMQProducer) consumerContainer.get(producerConsumerKey);
	}

	public static DefaultMQProducer getProducer(java.util.Map<String, Object> consumerContainer, RocketMessage rocketMessage, OrderMessage orderMessage){
		String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, orderMessage);
		return (DefaultMQProducer) consumerContainer.get(producerConsumerKey);
	}

	public static TransactionMQProducer getProducer(java.util.Map<String, Object> consumerContainer, RocketMessage rocketMessage, TransactionMessage transactionMessage){
		String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, transactionMessage);
		return (TransactionMQProducer) consumerContainer.get(producerConsumerKey);
	}
}
