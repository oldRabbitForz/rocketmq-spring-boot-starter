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

package cn.soyumall.capacity.core.factory.execution;

import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.strategy.PutProducerStrategy;
import cn.soyumall.capacity.thread.AbstractProducerThread;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: ProducerFactoryExecution
 */
public class ProducerFactoryExecution extends AbstractProducerThread {

	public ProducerFactoryExecution(java.util.Map<String, Object> producerConsumer, RocketMessage rocketMessage, Object bean,
                                    RocketMQProperties rocketProperties, ApplicationContext applicationContext) {
		super(producerConsumer, rocketMessage, bean, rocketProperties, applicationContext);
	}

	/**
	 * 开始向容器装填
	 *
	 * @param producerConsumer   producerConsumer
	 * @param rocketMessage      rocketMessage
	 * @param bean               bean
	 * @param rocketProperties   rocketProperties
	 * @param applicationContext applicationContext
	 */
	@Override
	protected void statsPutProducer(java.util.Map<String, Object> producerConsumer, RocketMessage rocketMessage, Object bean,
                                    RocketMQProperties rocketProperties, ApplicationContext applicationContext) throws MQClientException {
		PutProducerStrategy.putProducer(producerConsumer, rocketMessage, bean, rocketProperties, applicationContext);
	}
}
