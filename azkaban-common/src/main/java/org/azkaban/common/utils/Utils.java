/*
 * Copyright 2012 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.azkaban.common.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;


/**
 * A util helper class full of static methods that are commonly used.
 */
public class Utils {

    /**
     * Private constructor.
     */
    private Utils() {
    }

    public static Object callConstructor(Class<?> c, Object... args) {
	return callConstructor(c, getTypes(args), args);
    }

    public static Class<?>[] getTypes(Object... args) {
	Class<?>[] argTypes = new Class<?>[args.length];
	for (int i = 0; i < args.length; i++) {
	    argTypes[i] = args[i].getClass();
	}
	return argTypes;
    }

    /**
     * Call the class constructor with the given arguments
     * 通过class对象调用指定公共构造方法（作用：代替了用工厂类去实现对象的实例化）
     *
     * @param c
     *            The class
     * @param argTypes
     *            The arguments of class Type
     * @param args
     *            The arguments
     * @return The constructed object
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Object callConstructor(Class<?> c, Class<?>[] argTypes,Object... args) {
	try {
	    Constructor<?> constructor = c.getConstructor(argTypes);
	    return constructor.newInstance(args);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    throw new IllegalStateException(e);
	}
    }

    /** 
    * @Title: nonNull 
    * @Description: TODO 
    * @param @param request
    * @param @return  
    * @return HttpServletRequest
    * @throws 
    */
    public static <T> T nonNull(T t) {
      if (t == null) {
        throw new IllegalArgumentException("Null value not allowed.");
      } else {
        return t;
      }
    }
    
    public static String getRootFilePath() throws IOException{
	File file = new File(".");
	return file.getCanonicalPath();
    }
}
