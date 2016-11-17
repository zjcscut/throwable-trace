/*
 * Copyright 2015-2016 http://hsweb.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.throwable.trace.core.utils.extend;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * @author zhangjinci
 * @version 2016/11/9 11:49
 * @function Aop工具类
 */
public final class AopUtils {

	/**
	 * 通过Around的ProceedingJoinPoint拼接方法名
	 *
	 * @param pjp 切点信息对象
	 * @return 方法和参数名 cn.hello.helloWorld（java.lang.String name1,java.lang.String name2）
	 */
	public static String getMethodAndParamsName(ProceedingJoinPoint pjp) {
		StringBuilder methodName = new StringBuilder(pjp.getSignature().getName()).append("(");
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		String[] names = signature.getParameterNames();
		Class[] args = signature.getParameterTypes();
		for (int i = 0, len = args.length; i < len; i++) {
			if (i != 0) {
				methodName.append(",");
			}
			methodName.append(args[i].getSimpleName()).append(" ").append(names[i]);
		}
		return methodName.append(")").toString();
	}
}
