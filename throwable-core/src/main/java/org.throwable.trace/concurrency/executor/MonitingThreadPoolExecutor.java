package org.throwable.trace.concurrency.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zjc
 * @version 2016/11/5 13:37
 * @description 提供监控的ThreadPoolExecutor
 * @see ThreadPoolExecutor
 */
public class MonitingThreadPoolExecutor extends ThreadPoolExecutor {

	private final Logger log = LoggerFactory.getLogger(MonitingThreadPoolExecutor.class);

	//记录任务开始时间
	private final ThreadLocal<Long> startTime = new ThreadLocal<>();
	//记录任务数量
	private final AtomicLong numTasks = new AtomicLong();
	//记录执行所有任务的总时间
	private final AtomicLong totalTime = new AtomicLong();

	public MonitingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
									  BlockingQueue<Runnable> workQueue,
									  ThreadFactory threadFactory,
									  RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		log.debug(String.format("Thread %s: start %s", t, r));
		startTime.set(System.nanoTime());
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.debug(String.format("Thread %s: end,cost time = %d ms", r, taskTime / 1000));
		} finally {
			super.afterExecute(r, t);
		}
	}

	@Override
	protected void terminated() {
		try {
			log.debug(String.format("Terminated: avg time = %d ms", totalTime.get() / 1000 / numTasks.get()));
		} finally {
			super.terminated();
		}
	}
}
