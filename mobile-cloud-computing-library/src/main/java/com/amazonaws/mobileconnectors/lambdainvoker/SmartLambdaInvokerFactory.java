package com.amazonaws.mobileconnectors.lambdainvoker;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.util.ClientContext;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;

import java.lang.reflect.Proxy;

public class SmartLambdaInvokerFactory extends LambdaInvokerFactory {
    private AWSLambda lambda;
    private ClientContext clientContext;

    public SmartLambdaInvokerFactory(Context context, Regions region, AWSCredentialsProvider provider) {
        this(context, region, provider, new ClientConfiguration());
    }

    public SmartLambdaInvokerFactory(Context context, Regions region, AWSCredentialsProvider provider,
                                     ClientConfiguration clientConfiguration) {
        super(context, region, provider, clientConfiguration);
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("provider can't be null");
        }

        lambda = new AWSLambdaClient(provider, clientConfiguration);
        lambda.setRegion(Region.getRegion(region));
        clientContext = new ClientContext(context);
    }

    // Method traps for inherited methods
    public <T> T build(Class<T> interfaceClass) {
        throw new RuntimeException("SmartLambdaInvokerFactory requires AWS Lambda function name to work properly.");
    }

    public <T> T build(Class<T> interfaceClass, LambdaDataBinder binder) {
        throw new RuntimeException("SmartLambdaInvokerFactory requires AWS Lambda function name to work properly.");
    }


    public <T> T build(Class<T> interfaceClass, String functionName, Class returnType) {
        return build(interfaceClass, new LambdaJsonBinder(), functionName, returnType);
    }


    public <T> T build(Class<T> interfaceClass, LambdaDataBinder binder, String functionName, Class returnType) {
        Object proxy = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{
                        interfaceClass
                },
                new SmartLambdaInvocationHandler(lambda, binder, clientContext, functionName, returnType));
        return interfaceClass.cast(proxy);
    }

}
