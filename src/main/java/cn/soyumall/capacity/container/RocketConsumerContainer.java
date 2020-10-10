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

package cn.soyumall.capacity.container;

import cn.soyumall.capacity.annoation.MessageListener;
import cn.soyumall.capacity.annoation.RocketListener;
import cn.soyumall.capacity.autoconfigure.RocketMQProperties;
import cn.soyumall.capacity.core.factory.execution.ConsumerFactoryExecution;
import cn.soyumall.capacity.core.factory.execution.MethodFactoryExecution;
import cn.soyumall.capacity.core.factory.execution.ThreadPoolExecutorExecution;
import cn.soyumall.capacity.core.utils.AnnotatedMethodsUtils;
import cn.soyumall.capacity.factory.ThreadPoolFactory;
import cn.soyumall.capacity.serializer.RocketSerializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * ClassName: RocketConsumerContainer
 */

public class RocketConsumerContainer implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private RocketMQProperties rocketProperties;
    private RocketSerializer mqSerializer;

    public RocketConsumerContainer(RocketMQProperties rocketProperties, RocketSerializer rocketSerializer) {
        this.rocketProperties = rocketProperties;
        this.mqSerializer = rocketSerializer;
    }

    @PostConstruct
    public void initialize() {


        java.util.concurrent.ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.createConsumeThreadPoolExecutor (rocketProperties);

        applicationContext.getBeansWithAnnotation (RocketListener.class).forEach ((beanName, bean) -> {
            RocketListener rocketListener = bean.getClass ().getAnnotation (RocketListener.class);
            AnnotatedMethodsUtils.getMethodAndAnnotation (bean, MessageListener.class).
                    forEach ((method, consumerListener) -> {
                        ConsumerFactoryExecution consumerFactoryExecution = new ConsumerFactoryExecution (rocketProperties,
                                rocketListener, consumerListener, new MethodFactoryExecution(bean, method, mqSerializer));
                        ThreadPoolExecutorExecution.statsThread (threadPoolExecutor, consumerFactoryExecution);
                    });
        });

        threadPoolExecutor.shutdown ();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
