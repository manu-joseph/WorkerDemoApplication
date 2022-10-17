package org.manu.samples.service;

import org.manu.samples.client.model.RemoteResource;
import org.manu.samples.model.Resource;

public class ClassificationClient {

    public Resource classify(RemoteResource remoteResource) {
        try {
            //System.out.printf("Trying to get remote resource");
            Thread.sleep(1000);
            //System.out.printf("Got the expensive resource!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Resource(remoteResource.id(), remoteResource.name(),
                remoteResource.value(), "Silver");
    }

}
