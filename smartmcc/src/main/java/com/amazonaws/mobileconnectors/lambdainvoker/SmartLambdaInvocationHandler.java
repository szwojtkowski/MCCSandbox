package com.amazonaws.mobileconnectors.lambdainvoker;
import android.util.Log;

import com.amazonaws.mobileconnectors.util.ClientContext;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;

/**
 * SmartLambdaInvocationHandler extends default LambdaInvocationHandler in a way
 * that no LambdaFunction annotation parameters are needed.
 * Currently functionName and responseType are passed through constructor arguments.
 * Additionally it disallows to use any different invocation type than Blocking Request-Response.
 *
 * ~W.Adaszynski
 */

class SmartLambdaInvocationHandler extends LambdaInvocationHandler {

    private static final String TAG = "LambdaInvocationHandler";
    private final AWSLambda lambda;
    private final LambdaDataBinder binder;
    private final ClientContext clientContext;
    private final String functionName;
    private final Class returnType;

    public SmartLambdaInvocationHandler(AWSLambda lambda, LambdaDataBinder binder,
                                        ClientContext clientContext, String functionName, Class returnType) {
        super(lambda, binder, clientContext);
        this.lambda = lambda;
        this.binder = binder;
        this.clientContext = clientContext;
        this.functionName = functionName;
        this.returnType = returnType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        validateInterfaceMethod(method, args);

        final Object buildArg = (args == null || args.length == 0) ? null : args[0];
        InvokeRequest invokeRequest = buildInvokeRequest(method, buildArg);
        InvokeResult invokeResult = lambda.invoke(invokeRequest);

        return processInvokeResult(method, invokeResult);
    }

    void validateInterfaceMethod(Method method, Object[] args) {
        if (args != null && args.length > 1) {
            throw new UnsupportedOperationException(
                    "LambdaFunctions take either 0 or 1 arguments.");
        }
    }

    InvokeRequest buildInvokeRequest(Method method, Object object) throws IOException {
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.setFunctionName(this.functionName);
        invokeRequest.setLogType("None");
        invokeRequest.setInvocationType(InvocationType.RequestResponse);

        if (clientContext != null) {
            invokeRequest.setClientContext(clientContext.toBase64String());
        }

        invokeRequest.setPayload(ByteBuffer.wrap(binder.serialize(object)));

        return invokeRequest;
    }

    Object processInvokeResult(Method method, InvokeResult invokeResult)
            throws IOException {
        if (invokeResult.getLogResult() != null) {
            Log.d(TAG, method.getName() + " log: "
                    + new String(Base64.decode(invokeResult.getLogResult()), StringUtils.UTF8));
        }

        if (invokeResult.getFunctionError() != null) {
            throw new LambdaFunctionException(invokeResult.getFunctionError(),
                    new String(invokeResult.getPayload().array(), StringUtils.UTF8));
        }

        if (invokeResult.getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT
                || method.getReturnType().equals(void.class)) {
            return null;
        }
        return binder.deserialize(invokeResult.getPayload().array(),
                returnType);
    }
}
