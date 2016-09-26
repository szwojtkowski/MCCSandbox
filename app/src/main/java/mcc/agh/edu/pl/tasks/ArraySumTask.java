package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.example.smartmcc.ProxyFactory;
import com.example.smartmcc.ProxyFactoryConfiguration;

import mcc.agh.edu.pl.lambdaproxy.IArraySumLambdaProxy;
import proxy.SmartProxy;

public class ArraySumTask extends AsyncTask<ArraySumRequest, Void, ArraySumResponse> {

    private final Activity caller;
    private SmartProxy <ArraySumRequest, ArraySumResponse> smartProxy;

    public ArraySumTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<ArraySumRequest, ArraySumResponse>(caller.getApplicationContext(), pfc);
        this.smartProxy = factory.create(IArraySumLambdaProxy.class, new ArraySum(), "ArraySum", ArraySumResponse.class);
    }

    @Override
    protected ArraySumResponse doInBackground(ArraySumRequest... arg0) {
        System.out.println(this.smartProxy.processRemotely(arg0[0]));
        return this.smartProxy.processRemotely(arg0[0]);
    }

    @Override
    protected void onPostExecute(ArraySumResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %f", result.getSum()), Toast.LENGTH_SHORT).show();
    }
}
