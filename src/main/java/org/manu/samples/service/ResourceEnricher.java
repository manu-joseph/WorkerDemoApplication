package org.manu.samples.service;

import io.reactivex.Flowable;
import org.manu.samples.client.model.RemoteResource;
import org.manu.samples.model.Resource;

public class ResourceEnricher {

    public Flowable<Resource> enrich(RemoteResource remoteResource) {
        String tag = "";
        if(remoteResource.value() > 100) {
            tag = "Diamond";
        }
        else if(remoteResource.value() > 50){
            tag = "Gold";
        }
        else {
            tag = "Silver";
        }
        return Flowable.just(new Resource(remoteResource.id(),
                remoteResource.name(), remoteResource.value(), tag));
    }
}
