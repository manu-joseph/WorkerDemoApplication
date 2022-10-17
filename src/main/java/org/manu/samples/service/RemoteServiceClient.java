package org.manu.samples.service;

import io.reactivex.Flowable;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvoker;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvokerProvider;
import org.manu.samples.client.model.RemoteResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.ThreadLocalRandom;

public class RemoteServiceClient {

    private final WebTarget webTarget;

    public RemoteServiceClient() {
        this.webTarget = ClientBuilder.newClient().
                    target("http://localhost:8080/v1/persons/{personId}");
    }

    public RemoteResource getRemoteResource(Integer id) {
        try {
            //System.out.printf("Trying to get remote resource");
            Thread.sleep(1000);
            //System.out.printf("Got the expensive resource!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new RemoteResource(ThreadLocalRandom.current().nextInt(), "Test", 20);
    }

    public RemoteResource retrieveById(Integer id) {
        Client client = ClientBuilder.newClient();
        String resp = this.webTarget
        .resolveTemplate("personId", id).
                request().get(String.class);
        return new RemoteResource(id, resp, 20);
    }
}
