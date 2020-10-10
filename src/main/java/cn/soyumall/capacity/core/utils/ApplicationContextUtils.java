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

import cn.soyumall.capacity.core.producer.DefaultLocalTransactionProcess;
import cn.soyumall.capacity.core.producer.DefaultSendCallback;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: ApplicationContextUtils
 */
public class ApplicationContextUtils {
	private ApplicationContextUtils() {
	}

	public static SendCallback getSendCallback(ApplicationContext applicationContext, Class<? extends SendCallback> callback) {
		SendCallback sendCallback;
		if (DefaultSendCallback.class.equals(callback)) {
			sendCallback = new DefaultSendCallback();
		} else {
			sendCallback = applicationContext.getBean(callback);
		}
		return sendCallback;
	}

	public static DefaultLocalTransactionProcess getLocalTransactionChecker(ApplicationContext applicationContext,
                                                                            LocalTransactionState transactionStatus,
                                                                            Class<? extends DefaultLocalTransactionProcess> checker) {
		DefaultLocalTransactionProcess localTransactionChecker;
		if (DefaultLocalTransactionProcess.class.equals(checker)) {
			localTransactionChecker = new DefaultLocalTransactionProcess(transactionStatus);
		} else {
			localTransactionChecker = applicationContext.getBean(checker);
		}
		return localTransactionChecker;
	}

	public static DefaultLocalTransactionProcess getLocalTransactionExecuter(ApplicationContext applicationContext,
                                                                             Class<? extends DefaultLocalTransactionProcess> executer) {
		DefaultLocalTransactionProcess localTransactionExecuter;
		if (DefaultLocalTransactionProcess.class.equals(executer)) {
			localTransactionExecuter = new DefaultLocalTransactionProcess(null);
		} else {
			localTransactionExecuter = applicationContext.getBean(executer);
		}
		return localTransactionExecuter;
	}
}
