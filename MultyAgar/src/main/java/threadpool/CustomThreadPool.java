package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {

    private BlockingQueue<Runnable> runnableQueue;

    private List<WorkerThread> threads;

    private AtomicBoolean isThreadPoolShutDownInitiated;

    public CustomThreadPool(final int noOfThreads) {
        this.runnableQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>(noOfThreads);
        this.isThreadPoolShutDownInitiated = new AtomicBoolean(false);
        // create worker threads
        for (int i = 1; i <= noOfThreads; i++) {
            WorkerThread worker = new WorkerThread(runnableQueue, this);
            worker.setName("Worker Thread - " + i);
            worker.start();
            threads.add(worker);
        }
    }

    public void execute(Runnable r) throws InterruptedException {
        if (!isThreadPoolShutDownInitiated.get()) {
            runnableQueue.put(r);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to execute task");
        }
    }

    public void shutdown() {
        isThreadPoolShutDownInitiated = new AtomicBoolean(true);
    }

    private class WorkerThread extends Thread {

        private BlockingQueue<Runnable> taskQueue;

        private CustomThreadPool threadPool;

        public WorkerThread(BlockingQueue<Runnable> taskQueue, CustomThreadPool threadPool) {
            this.taskQueue = taskQueue;
            this.threadPool = threadPool;
        }

        @Override
        public void run() {
            try {
                // continue until all tasks finished processing
                while (!threadPool.isThreadPoolShutDownInitiated.get() || !taskQueue.isEmpty()) {
                    Runnable r;
                    // Poll a runnable from the queue and execute it
                    while ((r = taskQueue.poll()) != null) {
                        r.run();
                    }
                    Thread.sleep(1);
                }
            } catch (RuntimeException | InterruptedException e) {
                throw new CustomThreadPoolException(e);
            }
        }
    }

    private static class CustomThreadPoolException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomThreadPoolException(Throwable t) {
            super(t);
        }
    }

}
