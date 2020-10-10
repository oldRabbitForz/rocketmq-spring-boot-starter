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

import cn.soyumall.capacity.annoation.MessageListener;
import cn.soyumall.capacity.annoation.RocketListener;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.factory.execution.MethodFactoryExecution;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * ClassName: AbstractConsumerThread
 */
@Data
public abstract class AbstractConsumerThread implements Runnable {
	private RocketMQProperties rocketProperties;
	private RocketListener rocketListener;
	private MessageListener consumerListener;
	private MethodFactoryExecution methodFactoryExecution;

	public AbstractConsumerThread(RocketMQProperties rocketProperties, RocketListener rocketListener, MessageListener consumerListener,
                                  MethodFactoryExecution methodFactoryExecution) {
		this.rocketProperties = rocketProperties;
		this.rocketListener = rocketListener;
		this.consumerListener = consumerListener;
		this.methodFactoryExecution = methodFactoryExecution;
	}

	/**
	 * 消费者开始监听
	 *
	 * @param rocketProperties       rocketProperties
	 * @param rocketListener         rocketListener
	 * @param consumerListener       consumerListener
	 * @param methodFactoryExecution methodFactoryExecution
	 */
	protected abstract void statsConsumer(RocketMQProperties rocketProperties,
                                          RocketListener rocketListener,
                                          MessageListener consumerListener,
                                          MethodFactoryExecution methodFactoryExecution) throws MQClientException;

	@SneakyThrows
	@Override
	public void run() {
		statsConsumer(this.getRocketProperties(),
				this.getRocketListener(),
				this.getConsumerListener(),
				this.getMethodFactoryExecution());
	}
}
