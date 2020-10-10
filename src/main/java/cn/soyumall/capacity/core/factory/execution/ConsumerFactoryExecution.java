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

import cn.soyumall.capacity.annoation.MessageListener;
import cn.soyumall.capacity.annoation.RocketListener;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.factory.ConsumerFactory;
import cn.soyumall.capacity.thread.AbstractConsumerThread;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * ClassName: ConsumerFactoryExecution
 */
public class ConsumerFactoryExecution extends AbstractConsumerThread {


	public ConsumerFactoryExecution(RocketMQProperties rocketProperties, RocketListener rocketListener, MessageListener consumerListener, MethodFactoryExecution methodFactoryExecution) {
		super(rocketProperties, rocketListener, consumerListener, methodFactoryExecution);
	}

	@Override
	public void statsConsumer(RocketMQProperties rocketProperties,
	                          RocketListener rocketListener,
	                          MessageListener consumerListener,
	                          MethodFactoryExecution methodFactoryExecution) throws MQClientException {
		DefaultMQPushConsumer consumer = ConsumerFactory.createConsumer(rocketProperties, rocketListener);
		consumer.setMessageModel(rocketListener.messageModel());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe(consumerListener.topic(), consumerListener.tag());
		if(consumerListener.orderConsumer()) {
			consumer.registerMessageListener(new MessageListenerOrderly() {
				AtomicLong consumeTimes = new AtomicLong(0);
				@SneakyThrows
				@Override
				public ConsumeOrderlyStatus consumeMessage(java.util.List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
					// 设置自动提交
					consumeOrderlyContext.setAutoCommit(true);
					for (MessageExt msg : list) {
						methodFactoryExecution.methodExecution(msg.getBody());
					}
					try {
						TimeUnit.SECONDS.sleep(5L);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					return ConsumeOrderlyStatus.SUCCESS;
				}
			});
		}
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@SneakyThrows
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(java.util.List<MessageExt> mgs,
                                                            ConsumeConcurrentlyContext consumeconcurrentlycontext) {
				for (MessageExt msg : mgs) {
					methodFactoryExecution.methodExecution(msg.getBody());
				}
				// ConsumeConcurrentlyStatus.RECONSUME_LATER boker会根据设置的messageDelayLevel发起重试，默认16次
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		consumer.start();
		return;
	}
}
