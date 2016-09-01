package com.example.smartmcc;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import proxy.SmartProxy;
import task.ISharedResource;

class TestingInocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("Invoking proxy");
        if (method.getAnnotation(LambdaFunction.class) == null) {
            throw new UnsupportedOperationException("JA PIERDOLEEEE!!!"
                    + method.getName());
        }
        return null;
    }
}

public class ProxyFactory <Q,S> {
    private static ProxyFactory instance;
    private CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider;
    private Context applicationContext;
    private LambdaInvokerFactory lambdaInvokerFactory;

    public ProxyFactory(Context applicationContext, ProxyFactoryConfiguration configuration) {
        this.init(applicationContext, configuration);
    }

    private void init(Context context, ProxyFactoryConfiguration config) {
        this.cognitoCachingCredentialsProvider =  new CognitoCachingCredentialsProvider(context, config.identityPoolId, config.region);
        this.applicationContext = context;
        this.lambdaInvokerFactory = new LambdaInvokerFactory(this.applicationContext, config.region, this.cognitoCachingCredentialsProvider);
    }

    public SmartProxy<Q, S> create(Class <ISharedResource<Q,S>> amazonProxyClass, ISharedResource<Q,S> localInstance) {
        final ISharedResource<Q,S> myInterface = this.lambdaInvokerFactory.build(amazonProxyClass);
        System.out.println("PROCESSING FROM CREATE FACTORY");
        Object proxy = Proxy.newProxyInstance(amazonProxyClass.getClassLoader(),
                new Class<?>[] {
                        amazonProxyClass
                },
                new TestingInocationHandler());

        amazonProxyClass.cast(proxy).process(null);


        return new SmartProxy<Q, S>(myInterface, localInstance);
    }
}
