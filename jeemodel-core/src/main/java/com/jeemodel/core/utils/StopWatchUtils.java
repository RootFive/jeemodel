package com.jeemodel.core.utils;

import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

/**
 * @author Rootfive 2021-3-14	 联系方式: QQ群：2236067977  
 * <p>计时器工具类</p>
 * </blockquote>
 * @since   JeeModel 1.0.0
 */

public class StopWatchUtils {
	/**
	 * 	<pre>这个方法打印在我们记录日志时是非常友好的 还有百分比的分析</pre>
	 * @param sw 计时器
	 * @param 描述
	 * @return
	 */
	public static String prettyPrint(StopWatch sw, String workName) {
		if (sw.isRunning()) {
			sw.stop();
		}
		long totalTimeNanos = sw.getTotalTimeNanos();
		String format = formatComplement("", true, 9);
		StringBuilder sb = new StringBuilder();
		sb.append('\n');
		sb.append("---------------------------------------------------------\n");
		if (StringUtils.isNotBlank(workName)) {
			sb.append(workName);
		}
		sb.append("    执行结束").append("    任务分解数：").append(sw.getTaskCount()).append("    总耗时：")
				.append(sw.getTotalTimeMillis()).append("ms \n");
		sb.append("---------------------------------------------------------\n");
		sb.append("    Used(ms)").append(format).append("PCT(%)").append(format).append("Task\n");
		sb.append("---------------------------------------------------------\n");
		for (TaskInfo task : sw.getTaskInfo()) {

			double timeNanos = (double) task.getTimeNanos();
			double used = NumberUtils.mul(NumberUtils.div(timeNanos, totalTimeNanos, 4), 100);

			String timeFormat = formatComplement(task.getTimeMillis(), false, 9);
			String timeUsedFormat = formatComplement(used, false, 9);
			String taskName = task.getTaskName();

			sb.append(timeFormat).append("ms").append(format);
			sb.append(timeUsedFormat).append("%").append(format);
			sb.append(taskName).append("\n");
		}
		sb.append("---------------------------------------------------------\n");
		return sb.toString();
	}

	/**
	 * String position5 = String.format("%-5s", args);   //表示 args 左对齐占用5个字符，不足的用空格补位
	 * String position5 = String.format("%5s", args);   //表示 args 右对齐占用5个字符，不足的用空格补位
	 * @param args
	 * @param isRight
	 * @param total
	 * @return
	 */
	private static String formatComplement(Object args, boolean isRight, int total) {
		StringBuilder sb = new StringBuilder("%");
		if (isRight) {
			sb.append('-');
		}
		sb.append(total).append("s");
		return String.format(sb.toString(), args);

	}
}