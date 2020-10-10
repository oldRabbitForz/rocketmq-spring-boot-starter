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

package cn.soyumall.capacity.container;


import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.strategy.RocketConsumerStrategy;
import cn.soyumall.capacity.factory.ThreadPoolFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * ClassName: RocketProducerContainer
 */
public class RocketProducerContainer implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	private RocketMQProperties rocketProperties;
	private java.util.Map<String, Object> consumerContainer;

	public RocketProducerContainer(java.util.Map<String, Object> consumerContainer, RocketMQProperties rocketProperties) {
		this.consumerContainer = consumerContainer;
		this.rocketProperties = rocketProperties;
	}

	@PostConstruct
	public void initialize() {
		java.util.concurrent.ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.createProducerThreadPoolExecutor(rocketProperties);
		applicationContext.getBeansWithAnnotation(RocketMessage.class).forEach((beanName, bean) ->
				RocketConsumerStrategy.putProducer(threadPoolExecutor, consumerContainer, bean, rocketProperties, applicationContext));
		threadPoolExecutor.shutdown();
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
