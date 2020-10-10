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

import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.producer.DefaultLocalTransactionProcess;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

/**
 * ClassName: ProducerFactory
 */
public class ProducerFactory {
	private ProducerFactory() {
	}

	public static DefaultMQProducer createProducer(RocketMessage rocketMessage, RocketMQProperties rocketProperties) {
		DefaultMQProducer producer = new DefaultMQProducer(rocketProperties.getNameSapce(), rocketMessage.groupID());
		producer.setNamesrvAddr(rocketProperties.getNameSrvAddr());
		return producer;
	}

	public static DefaultMQProducer createOrderProducer(RocketMessage rocketMessage, RocketMQProperties rocketProperties) {
		DefaultMQProducer producer = new DefaultMQProducer(rocketProperties.getNameSapce(), rocketMessage.groupID());
		producer.setNamesrvAddr(rocketProperties.getNameSrvAddr());
		return producer;
	}

	public static TransactionMQProducer createTransactionProducer(RocketMessage rocketMessage, RocketMQProperties rocketProperties,
                                                                  DefaultLocalTransactionProcess localTransactionChecker) {
		TransactionMQProducer transactionMQProducer = new TransactionMQProducer(rocketProperties.getNameSapce(),
				rocketMessage.groupID());
		transactionMQProducer.setTransactionListener(localTransactionChecker);
		return new TransactionMQProducer();
	}
}

