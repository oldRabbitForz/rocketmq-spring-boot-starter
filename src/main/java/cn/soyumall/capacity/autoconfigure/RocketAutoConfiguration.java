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

package cn.soyumall.capacity.autoconfigure;

import cn.soyumall.capacity.annoation.EnableRocketMQ;
import cn.soyumall.capacity.aspect.RocketAspect;
import cn.soyumall.capacity.container.RocketConsumerContainer;
import cn.soyumall.capacity.container.RocketProducerContainer;
import cn.soyumall.capacity.serializer.ProtoBufSerializer;
import cn.soyumall.capacity.serializer.RocketSerializer;
import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;


/**
 * ClassName: RocketProperties
 */
@Configuration
@ConditionalOnBean(annotation = EnableRocketMQ.class)
public class RocketAutoConfiguration {
    @Resource
    private RocketMQProperties rocketProperties;
    @Resource
    private java.util.Map<String, Object> consumerContainer;
    @Resource
    private RocketSerializer rocketSerializer;

    @org.springframework.context.annotation.Bean
    @ConditionalOnMissingBean(RocketConsumerContainer.class)
    public RocketConsumerContainer rocketConsumerContainer() {
        return new RocketConsumerContainer(rocketProperties, rocketSerializer);
    }

    @org.springframework.context.annotation.Bean
    @ConditionalOnMissingBean(RocketSerializer.class)
    public RocketSerializer rocketSerializer() {
        return new ProtoBufSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(java.util.Map.class)
    public java.util.Map<String, Object> consumerContainer() {
        return Maps.newConcurrentMap ();
    }

    @Bean
    @ConditionalOnMissingBean(RocketProducerContainer.class)
    public RocketProducerContainer rocketProducerContainer() {
        return new RocketProducerContainer (consumerContainer, rocketProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RocketAspect.class)
    public RocketAspect rockerAspect() {
        return new RocketAspect(consumerContainer, rocketProperties);
    }
}
