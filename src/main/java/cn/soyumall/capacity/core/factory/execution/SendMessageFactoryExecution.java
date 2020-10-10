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
import cn.soyumall.capacity.core.strategy.ProducerStrategy;
import cn.soyumall.capacity.thread.AbstractSendMessageThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;


/**
 * ClassName: SendMessageFactoryExecution
 */
@Slf4j
public class SendMessageFactoryExecution extends AbstractSendMessageThread {


	public SendMessageFactoryExecution(Integer startDeliverTime, java.util.Map<String, Object> consumerContainer,
                                       RocketMessage rocketMessage, Object message,
                                       byte[] bytes, ApplicationContext applicationContext) {
		super(startDeliverTime,consumerContainer, rocketMessage, message, bytes, applicationContext);
	}

	/**
	 * 开始发送消息
	 * @param startDeliverTime   startDeliverTime
	 * @param consumerContainer  consumerContainer
	 * @param rocketMessage      rocketMessage
	 * @param message            message
	 * @param bytes              bytes
	 * @param applicationContext applicationContext
	 */
	@Override
	protected void statsSendMessage(Integer startDeliverTime, java.util.Map<String, Object> consumerContainer,
                                    RocketMessage rocketMessage, Object message, byte[] bytes, ApplicationContext applicationContext) {
		try {
			ProducerStrategy.statsSendMessage(startDeliverTime,consumerContainer, rocketMessage, message, bytes, applicationContext);
		} catch (Exception e) {
			log.error("statsSendMessage Error", e);
		}
	}
}

