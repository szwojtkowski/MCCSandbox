package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;

import mcc.agh.edu.pl.lambdaproxy.IArraySumLambdaProxy;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import proxy.SmartProxy;

public class ArraySumTask extends SmartTask<ArraySumRequest, ArraySumResponse> {

    private final Activity caller;

    public ArraySumTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<ArraySumRequest, ArraySumResponse>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(IArraySumLambdaProxy.class, new ArraySum(), "ArraySum", ArraySumResponse.class));
    }

    @Override
    public void end(ArraySumResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %f", result.getSum()), Toast.LENGTH_SHORT).show();
    }
}
