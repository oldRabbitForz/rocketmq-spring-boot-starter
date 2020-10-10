/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.soyumall.capacity.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Data
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {

    /**
     * The name server for rocketMQ, formats: `host:port;host:port`.
     */
    private String nameSrvAddr;
    private String nameSapce = "test_namesapce";

    /**
     * Enum type for accessChannel, values: LOCAL, CLOUD
     */
    private String accessChannel;

    private Producer producer;
    /**
     * 设置消息发送的超时时间，单位（毫秒），默认：3000
     */
    private Integer sendMsgTimeoutMillis = 3000;
    /**
     * 设置事务消息第一次回查的最快时间，单位（秒）
     */
    private Integer checkImmunityTimeInSeconds = 5;
    /**
     * 设置 RocketMessage 实例的消费线程数，阿里云默认：20
     * 默认cpu数量*2+1
     */
    private Integer consumeThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 设置消息消费失败的最大重试次数，默认：16
     */
    private Integer maxReconsumeTimes = 16;
    /**
     * 设置每条消息消费的最大超时时间，超过设置时间则被视为消费失败，等下次重新投递再次消费。 每个业务需要设置一个合理的值，单位（分钟）。 默认：15
     */
    private Integer consumeTimeout = 15;
    /**
     * 只适用于顺序消息，设置消息消费失败的重试间隔时间默认100毫秒
     */
    private Integer suspendTimeMilli = 100;
    /**
     * 异步发送消息执行Callback的目标线程池
     */
    private Integer callbackThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 初始化消费者线程数，（尽量和消费者数量一致）默认cpu数量*2+1
     */
    private Integer createConsumeThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 初始化生产者线程数，（尽量和生产者数量一致）默认cpu数量*2+1
     */
    private Integer createProducerThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 生产者发送消息线程数 默认cpu数量*2+1
     */
    private Integer sendMessageThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;


    /**
     * Configure enable listener or not.
     * In some particular cases, if you don't want the the listener is enabled when container startup,
     * the configuration pattern is like this :
     * rocketmq.consumer.listeners.<group-name>.<topic-name>.enabled=<boolean value, true or false>
     * <p>
     * the listener is enabled by default.
     */
    private Consumer consumer = new Consumer();

    public String getNameServer() {
        return nameSrvAddr;
    }

    public void setNameServer(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getAccessChannel() {
        return accessChannel;
    }

    public void setAccessChannel(String accessChannel) {
        this.accessChannel = accessChannel;
    }

    public static class Producer {

        /**
         * Group name of producer.
         */
        private String group;

        /**
         * Millis of send message timeout.
         */
        private int sendMessageTimeout = 3000;

        /**
         * Compress message body threshold, namely, message body larger than 4k will be compressed on default.
         */
        private int compressMessageBodyThreshold = 1024 * 4;

        /**
         * Maximum number of retry to perform internally before claiming sending failure in synchronous mode.
         * This may potentially cause message duplication which is up to application developers to resolve.
         */
        private int retryTimesWhenSendFailed = 2;

        /**
         * <p> Maximum number of retry to perform internally before claiming sending failure in asynchronous mode. </p>
         * This may potentially cause message duplication which is up to application developers to resolve.
         */
        private int retryTimesWhenSendAsyncFailed = 2;

        /**
         * Indicate whether to retry another broker on sending failure internally.
         */
        private boolean retryNextServer = false;

        /**
         * Maximum allowed message size in bytes.
         */
        private int maxMessageSize = 1024 * 1024 * 4;
        /**
         * Switch flag instance for message trace.
         */
        private boolean enableMsgTrace = true;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public int getSendMessageTimeout() {
            return sendMessageTimeout;
        }

        public void setSendMessageTimeout(int sendMessageTimeout) {
            this.sendMessageTimeout = sendMessageTimeout;
        }

        public int getCompressMessageBodyThreshold() {
            return compressMessageBodyThreshold;
        }

        public void setCompressMessageBodyThreshold(int compressMessageBodyThreshold) {
            this.compressMessageBodyThreshold = compressMessageBodyThreshold;
        }

        public int getRetryTimesWhenSendFailed() {
            return retryTimesWhenSendFailed;
        }

        public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
            this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
        }

        public int getRetryTimesWhenSendAsyncFailed() {
            return retryTimesWhenSendAsyncFailed;
        }

        public void setRetryTimesWhenSendAsyncFailed(int retryTimesWhenSendAsyncFailed) {
            this.retryTimesWhenSendAsyncFailed = retryTimesWhenSendAsyncFailed;
        }

        public boolean isRetryNextServer() {
            return retryNextServer;
        }

        public void setRetryNextServer(boolean retryNextServer) {
            this.retryNextServer = retryNextServer;
        }

        public int getMaxMessageSize() {
            return maxMessageSize;
        }

        public void setMaxMessageSize(int maxMessageSize) {
            this.maxMessageSize = maxMessageSize;
        }


        public boolean isEnableMsgTrace() {
            return enableMsgTrace;
        }

        public void setEnableMsgTrace(boolean enableMsgTrace) {
            this.enableMsgTrace = enableMsgTrace;
        }

    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public static final class Consumer {
        /**
         * listener configuration container
         * the pattern is like this:
         * group1.topic1 = false
         * group2.topic2 = true
         * group3.topic3 = false
         */
        private Map<String, java.util.Map<String, Boolean>> listeners = new HashMap<>();

        public Map<String, Map<String, Boolean>> getListeners() {
            return listeners;
        }

        public void setListeners(Map<String, Map<String, Boolean>> listeners) {
            this.listeners = listeners;
        }
    }

}
