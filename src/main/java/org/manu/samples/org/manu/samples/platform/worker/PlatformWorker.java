package org.manu.samples.org.manu.samples.platform.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface PlatformWorker {

    <T> Future<T> execute(Callable<T> task);

    default void finish() {

    }

}
