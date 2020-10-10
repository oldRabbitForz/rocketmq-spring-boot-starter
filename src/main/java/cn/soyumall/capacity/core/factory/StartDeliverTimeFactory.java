/**
 * Copyright 2020 the original author or authors.
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


import cn.soyumall.capacity.annoation.StartDeliverTime;
import cn.soyumall.capacity.core.utils.ObjectIsEmptyUtils;

/**
 * ClassName: StartDeliverTimeFactory
 */
public class StartDeliverTimeFactory {

    private StartDeliverTimeFactory() {
    }

    public static Integer getStartDeliverTime(Object[] args, java.lang.reflect.Parameter[] params) {
        for (int i = 0; i < args.length; i++) {
            StartDeliverTime annotation = params[i].getAnnotation (StartDeliverTime.class);
            if (ObjectIsEmptyUtils.isNotEmpty (annotation)) {
                return (Integer) args[i];
            }
        }
        return null;
    }
}
