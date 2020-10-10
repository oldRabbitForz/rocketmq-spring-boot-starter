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

package cn.soyumall.capacity.thread;

import cn.soyumall.capacity.annoation.RocketMessage;
import lombok.Data;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: AbstractSendMessageThread
 */
@Data
public abstract class AbstractSendMessageThread implements Runnable {
	private Integer startDeliverTime;
	private java.util.Map<String, Object> consumerContainer;
	private RocketMessage rocketMessage;
	private Object message;
	private byte[] bytes;
	private ApplicationContext applicationContext;

	public AbstractSendMessageThread(Integer startDeliverTime, java.util.Map<String, Object> consumerContainer,
                                     RocketMessage rocketMessage, Object message,
                                     byte[] bytes, ApplicationContext applicationContext) {
		this.startDeliverTime = startDeliverTime;
		this.consumerContainer = consumerContainer;
		this.rocketMessage = rocketMessage;
		this.message = message;
		this.bytes = bytes;
		this.applicationContext = applicationContext;
	}

	/**
	 * 开始发送消息
	 *
	 * @param startDeliverTime   startDeliverTime
	 * @param consumerContainer  consumerContainer
	 * @param rocketMessage      rocketMessage
	 * @param message            message
	 * @param bytes              bytes
	 * @param applicationContext applicationContext
	 */
	protected abstract void statsSendMessage(Integer startDeliverTime, java.util.Map<String, Object> consumerContainer,
                                             RocketMessage rocketMessage, Object message, byte[] bytes,
                                             ApplicationContext applicationContext);

	@Override
	public void run() {
		statsSendMessage(startDeliverTime, consumerContainer,
				rocketMessage,
				message,
				bytes,
				applicationContext);
	}
}
