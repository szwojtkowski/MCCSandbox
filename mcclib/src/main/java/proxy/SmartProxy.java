package proxy;

import task.ISharedResource;

public class SmartProxy <Q, R> {
    public ISharedResource<Q,R> amazonProxy;
    public ISharedResource<Q,R> localInstance;

    public SmartProxy(ISharedResource<Q,R> amazonProxy, ISharedResource<Q,R> localInstance) {
        this.localInstance = localInstance;
        this.amazonProxy = amazonProxy;
    }

    public R processLocally(Q request) {
        return this.localInstance.process(request);
    }

    public R processRemotly(Q request) {
        System.out.println("Processing remotly");
        return this.amazonProxy.process(request);
    }

}
