package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.SmartLambdaInvokerFactory;

import proxy.SmartProxy;
import task.ISharedResource;

public class ProxyFactory <Q,S> {
    private static ProxyFactory instance;
    private CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider;
    private Context applicationContext;
    private SmartLambdaInvokerFactory lambdaInvokerFactory;

    public ProxyFactory(Context applicationContext, ProxyFactoryConfiguration configuration) {
        this.init(applicationContext, configuration);
    }

    private void init(Context context, ProxyFactoryConfiguration config) {
        this.cognitoCachingCredentialsProvider =  new CognitoCachingCredentialsProvider(context, config.identityPoolId, config.region);
        this.applicationContext = context;
        this.lambdaInvokerFactory = new SmartLambdaInvokerFactory(this.applicationContext, config.region, this.cognitoCachingCredentialsProvider);
    }

    public SmartProxy<Q, S> create(Class <IRemoteSharedResource<Q,S>> amazonProxyClass, ISharedResource <Q, S> localInstance, String functionName, Class returnType) {
        final ISharedResource<Q,S> myInterface = this.lambdaInvokerFactory.build(amazonProxyClass, functionName, returnType);
        return new SmartProxy<Q,S>(myInterface, localInstance);
    }
}
