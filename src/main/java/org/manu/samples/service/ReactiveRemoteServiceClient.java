package org.manu.samples.service;

import io.reactivex.Flowable;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvoker;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvokerProvider;
import org.manu.samples.client.model.RemoteResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ReactiveRemoteServiceClient {

    private final WebTarget webTarget;


    /**
     * A client reactively monitoring the
     * code path and the execution of kghhlkj
     */
    public ReactiveRemoteServiceClient() {
        Client client = ClientBuilder.newClient();
        client.register(RxFlowableInvokerProvider.class);
        this.webTarget = client.target("http://localhost:8080/v1/persons/{personId}");
    }

    public Flowable<RemoteResource> getRemoteResource(Integer id) {
        //Flowable.timer(1L, TimeUnit.SECONDS).
       return withoutDelay(id);

    }

    private Flowable<RemoteResource> withoutDelay(Integer id) {
        return Flowable.just(new RemoteResource(id,
                "Test", 20));
    }

    private Flowable<RemoteResource> resource() {
        return Flowable.just(new RemoteResource(ThreadLocalRandom.current().nextInt(),
                "Test", 20));
    }
    public Flowable<RemoteResource> retrieveById(Integer id) {
        Client client = ClientBuilder.newClient();
        client.register(RxFlowableInvokerProvider.class);

        Flowable<String> responseFlowable =
                this.webTarget
                        .resolveTemplate("personId", id)
                        .request()
                        .rx(RxFlowableInvoker.class)
                        .get(String.class);
        return responseFlowable.map(resp -> new RemoteResource(id, resp, 20));
    }

}
