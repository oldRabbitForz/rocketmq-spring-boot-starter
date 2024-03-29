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

package cn.soyumall.capacity.factory;

import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * ClassName: ThreadPoolFactory
 */
public class ThreadPoolFactory {
	private ThreadPoolFactory() {
	}

	public static ThreadPoolExecutor createConsumeThreadPoolExecutor(RocketMQProperties rocketProperties) {
		Integer threadNums = rocketProperties.getCreateConsumeThreadNums();

		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("InitializeConsumerListener").build();

		return new ThreadPoolExecutor(threadNums,
				threadNums,
				0,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(1024),
				threadFactory,
				new ThreadPoolExecutor.AbortPolicy()
		);
	}

	public static ThreadPoolExecutor createProducerThreadPoolExecutor(RocketMQProperties rocketProperties) {

		Integer threadNums = rocketProperties.getCreateProducerThreadNums();

		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("InitializeProducer").build();

		return new ThreadPoolExecutor(threadNums,
				threadNums,
				0,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(1024),
				threadFactory,
				new ThreadPoolExecutor.AbortPolicy()
		);
	}

	public static ThreadPoolExecutor createSendMessageThreadPoolExecutor(RocketMQProperties rocketProperties) {

		Integer threadNums = rocketProperties.getSendMessageThreadNums();

		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("SendMessage").build();

		return new ThreadPoolExecutor(threadNums,
				threadNums,
				0,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(),
				threadFactory,
				new ThreadPoolExecutor.AbortPolicy()
		);
	}

	public static ThreadPoolExecutor createCallbackThreadPoolExecutor(RocketMQProperties rocketProperties) {

		Integer threadNums = rocketProperties.getCallbackThreadNums();

		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("callback").build();
		return new ThreadPoolExecutor(threadNums,
				threadNums,
				0,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(),
				threadFactory,
				new ThreadPoolExecutor.AbortPolicy()
		);
	}
}
