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

package cn.soyumall.capacity.core.factory;


import cn.soyumall.capacity.annoation.RocketListener;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;


/**
 * ClassName: ConsumerFactory
 */
public class ConsumerFactory {
	private ConsumerFactory() {
	}

	public static DefaultMQPushConsumer createConsumer(RocketMQProperties properties, RocketListener lister) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getNameSapce(), lister.groupID());
		consumer.setNamesrvAddr(properties.getNameSrvAddr());
		return consumer;
	}
}
