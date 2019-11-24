package utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CThreadPoolExecutor {
    // private static final String TAG = CThreadPoolExecutor.class.getSimpleName();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();// CPU个数
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));// 线程池中核心线程的数量
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;// 线程池中最大线程数量
    private static final long KEEP_ALIVE_TIME = 30L;// 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
    private static final int WAIT_COUNT = 128; // 最多排队个数，这里控制线程创建的频率

    private static ThreadPoolExecutor pool = createThreadPoolExecutor();

    private static ThreadPoolExecutor createThreadPoolExecutor() {
        if (pool == null) {
            pool = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(WAIT_COUNT),
                    new CThreadFactory("CThreadPool", Thread.NORM_PRIORITY - 2),
                    new CHandlerException());
        }
        return pool;
    }

    // 线程池工厂
    public static class CThreadFactory implements ThreadFactory {
        private AtomicInteger counter = new AtomicInteger(1);
        private String prefix = "";
        private int priority = Thread.NORM_PRIORITY;

        public CThreadFactory(String prefix, int priority) {
            this.prefix = prefix;
            this.priority = priority;
        }

        public CThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        public Thread newThread(Runnable r) {
            Thread executor = new Thread(r, prefix + " #" + counter.getAndIncrement());
            executor.setDaemon(true);
            executor.setPriority(priority);
            return executor;
        }
    }

    /**
     * 抛弃当前的任务
     */
    private static class CHandlerException extends ThreadPoolExecutor.AbortPolicy {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            // Log.d(TAG, "rejectedExecution:" + r);
            // Log.e(TAG, logAllThreadStackTrace().toString());
            if (!pool.isShutdown()) {
                pool.shutdown();
                pool = null;
            }
            pool = createThreadPoolExecutor();
        }
    }

    private static ExecutorService jobsForUI = Executors.newFixedThreadPool(
            CORE_POOL_SIZE, new CThreadFactory("CJobsForUI", Thread.NORM_PRIORITY - 1));

    /**
     * 启动一个消耗线程，常驻后台
     *
     * @param r
     */
    public static void startConsumer(final Runnable r, final String name) {
        runInBackground(new Runnable() {
            public void run() {
                new CThreadFactory(name, Thread.NORM_PRIORITY - 3).newThread(r).start();
            }
        });
    }

    /**
     * 提交到其他线程去跑，需要取数据的时候会等待任务完成再继续
     *
     * @param task
     * @return
     */
    public static <T> Future<T> submitTask(Callable<T> task) {
        return jobsForUI.submit(task);
    }

    /**
     * 强制清理任务
     *
     * @param task
     * @return
     */
    public static <T> void cancelTask(Future<T> task) {
        if (task != null) {
            task.cancel(true);
        }
    }

    /**
     * 从 Future 中获取值，如果发生异常，打日志
     *
     * @param future
     * @param tag
     * @param name
     * @return
     */
    public static <T> T getFromTask(Future<T> future, String tag, String name) {
        try {
            return future.get();
        } catch (Exception e) {
            // Log.e(tag, (name != null ? name + ": " : "") + e.toString());
        }
        return null;
    }

    /**
     * 创建一个运行在后台的线程
     *
     * @param runnable
     */
    public static void runInBackground(Runnable runnable) {
        if (pool == null) {
            createThreadPoolExecutor();
        }
        pool.execute(runnable);
    }


}
