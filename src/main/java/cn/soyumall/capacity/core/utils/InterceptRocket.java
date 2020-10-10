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

package cn.soyumall.capacity.core.utils;

import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.core.factory.execution.SendMessageFactoryExecution;
import cn.soyumall.capacity.core.factory.execution.ThreadPoolExecutorExecution;
import cn.soyumall.capacity.serializer.RocketSerializer;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;

/**
 * ClassName: InterceptRocket
 */
public class InterceptRocket {
	private InterceptRocket() {
	}

	public static <T extends Annotation> Object intercept(Integer startDeliverTime, RocketMessage rocketMessage, T annotation,
                                                          Object proceed, java.util.Map<String, Object> consumerContainer,
                                                          java.util.concurrent.ThreadPoolExecutor threadPoolExecutor,
                                                          ApplicationContext applicationContext) {
		RocketSerializer mqSerializer = applicationContext.getBean(RocketSerializer.class);
		byte[] body = mqSerializer.serialize(proceed);

		ThreadPoolExecutorExecution.statsThread(threadPoolExecutor, new SendMessageFactoryExecution(startDeliverTime,
				consumerContainer, rocketMessage, annotation, body, applicationContext));
		return proceed;
	}
}
