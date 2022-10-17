package org.manu.samples.org.manu.samples.platform.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClassicPlatformWorker implements PlatformWorker {

    private final ExecutorService executorService;

    public ClassicPlatformWorker() {
        this.executorService = Executors.newFixedThreadPool(100);
    }

    public <T> Future<T> execute(Callable<T> task) {
        try {
            return executorService.submit(task);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void finish() {
        executorService.shutdown();
    }
}
