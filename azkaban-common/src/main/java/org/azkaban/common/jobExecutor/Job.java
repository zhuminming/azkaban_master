package org.azkaban.common.jobExecutor;

import org.azkaban.common.utils.Props;

public interface Job {
	/**
	 * 返回一个job唯一的Id.
	 *
	 * @return
	 */
	public String getId();

	/**
	 * 执行一个作业
	 */
	public void run() throws Exception;

	/**
	 * 尝试去关闭一个job
	 */
	public void cancel() throws Exception;

	/**
	 * 返回一个job的进展报告[0-1.0]的百分比
	 */
	public double getProcess();

	/**
	 * 获取job的生成的属性
	 */
	public Props getJobGeneratedProperties();

	public boolean isCancle();

}
