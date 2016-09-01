package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.amazonaws.regions.Regions;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.example.IArraySum;
import com.example.smartmcc.ProxyFactory;
import com.example.smartmcc.ProxyFactoryConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import mcc.agh.edu.pl.lambdaproxy.IArraySumLambdaProxy;
import proxy.SmartProxy;

interface A {
    void methodA();
}
interface B extends A {
    @Override
    void methodA();
    void methodB();
}


public class ArraySumTask extends AsyncTask<ArraySumRequest, Void, ArraySumResponse> {

    private final Activity caller;
    private SmartProxy smartProxy;

    public ArraySumTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory(caller.getApplicationContext(), pfc);
        this.smartProxy = factory.create(IArraySumLambdaProxy.class, new ArraySum());
    }

    @Override
    protected ArraySumResponse doInBackground(ArraySumRequest... arg0) {
        /*
            TODO: sprawdzić jak proxy z biblioteki wpływa na annotacje,
            dlaczego nie ma anotacji w metodzie process?
            czy da sie to zamockować, rozszerzyć klasę?
        */
        System.out.println(" _____________________________________");

        System.out.println(" _____________________________________");

        this.smartProxy.processRemotly(arg0[0]);
        return null;
    }

    @Override
    protected void onPostExecute(ArraySumResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %f", result.getSum()), Toast.LENGTH_SHORT).show();
    }
}
