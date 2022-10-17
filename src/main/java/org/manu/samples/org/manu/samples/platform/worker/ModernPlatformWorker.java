package org.manu.samples.org.manu.samples.platform.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModernPlatformWorker implements PlatformWorker {

    private final ExecutorService executorService;

    public ModernPlatformWorker() {
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public <T> Future<T> execute(Callable<T> task) {
        return executorService.submit(task);
    }
}
