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
import cn.soyumall.capacity.core.factory.execution.ProducerFactoryExecution;
import cn.soyumall.capacity.core.factory.execution.ThreadPoolExecutorExecution;
import cn.soyumall.capacity.core.utils.AnnotatedMethodsUtils;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: RocketConsumerStrategy
 */

public class RocketConsumerStrategy {
	private RocketConsumerStrategy() {
	}

	public static void putProducer(java.util.concurrent.ThreadPoolExecutor threadPoolExecutor, java.util.Map<String, Object> producerConsumer, Object bean, RocketMQProperties rocketProperties, ApplicationContext applicationContext) {
		RocketMessage rocketMessage = bean.getClass().getAnnotation(RocketMessage.class);
		AnnotatedMethodsUtils.getMethodAndAnnotation(bean, CommonMessage.class).
				forEach((method, commonMessage) -> {
					ProducerFactoryExecution producerFactoryExecution = new ProducerFactoryExecution(producerConsumer, rocketMessage, commonMessage, rocketProperties, applicationContext);
					ThreadPoolExecutorExecution.statsThread(threadPoolExecutor, producerFactoryExecution);
				});
		AnnotatedMethodsUtils.getMethodAndAnnotation(bean, OrderMessage.class).
				forEach((method, orderMessage) -> {
					ProducerFactoryExecution producerFactoryExecution = new ProducerFactoryExecution(producerConsumer, rocketMessage, orderMessage, rocketProperties, applicationContext);
					ThreadPoolExecutorExecution.statsThread(threadPoolExecutor, producerFactoryExecution);
				});
		AnnotatedMethodsUtils.getMethodAndAnnotation(bean, TransactionMessage.class).
				forEach((method, transactionMessage) -> {
					ProducerFactoryExecution producerFactoryExecution = new ProducerFactoryExecution(producerConsumer, rocketMessage, transactionMessage, rocketProperties, applicationContext);
					ThreadPoolExecutorExecution.statsThread(threadPoolExecutor, producerFactoryExecution);
				});
	}
}
