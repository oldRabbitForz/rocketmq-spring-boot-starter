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
import cn.soyumall.capacity.core.utils.ApplicationContextUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.CommunicationMode;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.context.ApplicationContext;


/**
 * ClassName: SendMessageStrategy
 */
public class SendMessageStrategy {
	private SendMessageStrategy() {
	}

	public static void send(CommonMessage commonMessage, DefaultMQProducer producer, Message message, ApplicationContext applicationContext) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		if (commonMessage.messageSendType().equals(CommunicationMode.SYNC)) {
			producer.send(message);
			return;
		}
		if (commonMessage.messageSendType().equals(CommunicationMode.ASYNC)) {
			producer.send(message, ApplicationContextUtils.getSendCallback(applicationContext, commonMessage.callback()));
			return;
		}
		if (commonMessage.messageSendType().equals(CommunicationMode.ONEWAY)) {
			producer.sendOneway(message);
		}
	}
}
