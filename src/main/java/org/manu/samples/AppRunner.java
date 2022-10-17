package org.manu.samples;

import io.reactivex.Flowable;
import org.manu.samples.client.model.RemoteResource;
import org.manu.samples.org.manu.samples.platform.worker.ClassicPlatformWorker;
import org.manu.samples.org.manu.samples.platform.worker.ModernPlatformWorker;
import org.manu.samples.org.manu.samples.platform.worker.PlatformWorker;
import org.manu.samples.service.ReactiveRemoteServiceClient;
import org.manu.samples.service.RemoteServiceClient;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppRunner {

    private static PlatformWorker classicPlatformWorker = new ClassicPlatformWorker();

    private static PlatformWorker modernPlatformWorker = new ModernPlatformWorker();

    private static RemoteServiceClient remoteServiceClient = new RemoteServiceClient();

    private static ReactiveRemoteServiceClient reactiveRemoteServiceClient = new ReactiveRemoteServiceClient();

    public static void main(String[] args) {
        System.out.printf("Staring Worker Application....");
        long start = System.currentTimeMillis();
        triggerNonReactiveExecution();
        //triggerReactiveExecution();
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("Finished execution in time=%s milli-seconds", elapsed);
        cleanUp();
    }

    private static void triggerNonReactiveExecution() {
        List<Future<RemoteResource>> resultsFuture = IntStream.rangeClosed(0, 1000).boxed().
                map(i -> getRemoteResource(i)).collect(Collectors.toList());
        resultsFuture.stream().
                map(AppRunner :: fromFuture).
                forEach(System.out :: println);
    }

    private static void cleanUp() {
        classicPlatformWorker.finish();;
        modernPlatformWorker.finish();;
    }

    private static <T> T fromFuture(Future<T> future) {
        try {
            return future.get();
        }catch(Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Future<RemoteResource> getRemoteResource(Integer id) {
        String executionModel = System.getProperty("execution.model", "classic");
        if(executionModel.equalsIgnoreCase("classic")) {
            return classicPlatformWorker.execute(() -> remoteServiceClient.retrieveById(id));
        }
        else {
            return modernPlatformWorker.execute(() -> remoteServiceClient.retrieveById(id));
        }

    }

    private static Flowable<RemoteResource> retrieveByReactive(Integer id) {
        return reactiveRemoteServiceClient.retrieveById(id);
    }

    private static void triggerReactiveExecution() {
       IntStream.rangeClosed(0, 1000).boxed().
                map(id -> retrieveRemoteResourceReactively(id)).
                forEach(remoteResourceObservable -> remoteResourceObservable.subscribe(System.out :: println));

        //Observable.zip(observables)
    }

    private static Flowable<RemoteResource> retrieveRemoteResourceReactively(Integer id) {
        return reactiveRemoteServiceClient.retrieveById(id);
    }

}
