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
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.factory.ProducerConsumerFactory;
import cn.soyumall.capacity.core.factory.ProducerFactory;
import cn.soyumall.capacity.core.producer.DefaultLocalTransactionProcess;
import cn.soyumall.capacity.core.utils.ApplicationContextUtils;
import cn.soyumall.capacity.factory.ThreadPoolFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: PutProducerStrategy
 */
public class PutProducerStrategy {
	private PutProducerStrategy() {
	}

	public static void putProducer(java.util.Map<String, Object> producerConsumer, RocketMessage rocketMessage, Object bean,
                                   RocketMQProperties rocketProperties, ApplicationContext applicationContext) throws MQClientException {
		if (bean instanceof CommonMessage) {
			CommonMessage commonMessage = (CommonMessage) bean;
			String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, commonMessage);
			DefaultMQProducer producer = ProducerFactory.createProducer(rocketMessage, rocketProperties);
			java.util.concurrent.ThreadPoolExecutor callbackThreadPoolExecutor = ThreadPoolFactory.createCallbackThreadPoolExecutor(rocketProperties);
			producer.start();
			producer.setCallbackExecutor(callbackThreadPoolExecutor);
			producerConsumer.put(producerConsumerKey, producer);
			return;
		}
		if (bean instanceof OrderMessage) {
			OrderMessage orderMessage = (OrderMessage) bean;
			String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, orderMessage);
			DefaultMQProducer orderProducer = ProducerFactory.createOrderProducer(rocketMessage, rocketProperties);
			orderProducer.start();
			producerConsumer.put(producerConsumerKey, orderProducer);
			return;
		}
		if (bean instanceof TransactionMessage) {
			TransactionMessage transactionMessage = (TransactionMessage) bean;
			String producerConsumerKey = ProducerConsumerFactory.getProducerConsumerKey(rocketMessage, transactionMessage);
			DefaultLocalTransactionProcess localTransactionChecker = ApplicationContextUtils.getLocalTransactionChecker(applicationContext, transactionMessage.transactionStatus(),transactionMessage.checker());
			TransactionMQProducer transactionProducer = ProducerFactory.createTransactionProducer(rocketMessage, rocketProperties, localTransactionChecker);
			transactionProducer.start();
			producerConsumer.put(producerConsumerKey, transactionProducer);
		}
	}
}
