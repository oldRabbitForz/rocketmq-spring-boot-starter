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

package cn.soyumall.capacity.aspect;

import cn.soyumall.capacity.annoation.CommonMessage;
import cn.soyumall.capacity.annoation.OrderMessage;
import cn.soyumall.capacity.annoation.RocketMessage;
import cn.soyumall.capacity.annoation.TransactionMessage;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.factory.StartDeliverTimeFactory;
import cn.soyumall.capacity.core.utils.AspectUtils;
import cn.soyumall.capacity.core.utils.InterceptRocket;
import cn.soyumall.capacity.factory.ThreadPoolFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: RocketAspect
 */
@Aspect
@Slf4j
@Data
public class RocketAspect implements ApplicationContextAware {
	private Map<String, Object> consumerContainer;
	private RocketMQProperties rocketProperties;
	private ThreadPoolExecutor threadPoolExecutor;
	private ApplicationContext applicationContext;

	public RocketAspect(Map<String, Object> consumerContainer, RocketMQProperties rocketProperties) {
		this.consumerContainer = consumerContainer;
		this.rocketProperties = rocketProperties;
		this.threadPoolExecutor = ThreadPoolFactory.createSendMessageThreadPoolExecutor(rocketProperties);
	}

	@Pointcut("@annotation(cn.soyumall.capacity.annoation.CommonMessage)")
	public void commonMessagePointcut() {
		log.debug("Start sending CommonMessage");
	}

	@Pointcut("@annotation(cn.soyumall.capacity.annoation.OrderMessage)")
	public void orderMessagePointcut() {
		log.debug("Start sending OrderMessage");
	}

	@Pointcut("@annotation(cn.soyumall.capacity.annoation.TransactionMessage)")
	public void transactionMessagePointcut() {
		log.debug("Start sending TransactionMessage");
	}

	@Around("commonMessagePointcut()")
	public Object rockerMessageSend(ProceedingJoinPoint point) throws Throwable {
		return InterceptRocket.intercept(
				StartDeliverTimeFactory.getStartDeliverTime(point.getArgs(), AspectUtils.getParams(point)),
				AspectUtils.getDeclaringClassAnnotation(point, RocketMessage.class),
				AspectUtils.getAnnotation(point, CommonMessage.class),
				point.proceed(),
				consumerContainer,
				threadPoolExecutor,
				applicationContext);
	}

	@Around("orderMessagePointcut()")
	public Object orderMessageSend(ProceedingJoinPoint point) throws Throwable {
		return InterceptRocket.intercept(
				StartDeliverTimeFactory.getStartDeliverTime(point.getArgs(),AspectUtils.getParams(point)),
				AspectUtils.getDeclaringClassAnnotation(point, RocketMessage.class),
				AspectUtils.getAnnotation(point, OrderMessage.class),
				point.proceed(),
				consumerContainer,
				threadPoolExecutor,
				applicationContext);
	}

	@Around("transactionMessagePointcut()")
	public Object transactionMessageSend(ProceedingJoinPoint point) throws Throwable {
		return InterceptRocket.intercept(
				StartDeliverTimeFactory.getStartDeliverTime(point.getArgs(),AspectUtils.getParams(point)),
				AspectUtils.getDeclaringClassAnnotation(point, RocketMessage.class),
				AspectUtils.getAnnotation(point, TransactionMessage.class),
				point.proceed(),
				consumerContainer,
				threadPoolExecutor,
				applicationContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
