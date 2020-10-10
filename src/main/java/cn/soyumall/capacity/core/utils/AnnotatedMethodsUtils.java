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

import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * ClassName: AnnotatedMethodsUtils
 */
public class AnnotatedMethodsUtils {
	private AnnotatedMethodsUtils() {
	}

	public static <T extends Annotation> java.util.Map<Method, T> getMethodAndAnnotation(Object bean, Class<T> annotation) {
		return MethodIntrospector.selectMethods(bean.getClass(),
				(MethodIntrospector.MetadataLookup<T>) method -> AnnotatedElementUtils
						.findMergedAnnotation(method, annotation));
	}
}
