/**
 * ExecutorLoader.java
 * zmm
 * 20172017年9月14日下午10:05:44
 */
package org.azkaban.common.executor;

/**
 * @author zmm
 *
 */
public interface ExecutorLoader {
    public ExecutableFlow fetchExecutableFlow(int execid) throws Exception;
    public void updateExecutableFlow(ExecutableFlow flow) throws Exception;
}
