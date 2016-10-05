package task;

public interface ISharedResource<Q, S> {
    public S process (Q request);
}
