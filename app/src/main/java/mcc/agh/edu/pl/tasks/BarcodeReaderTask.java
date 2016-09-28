package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.example.smartmcc.ProxyFactory;
import com.example.smartmcc.ProxyFactoryConfiguration;
import com.mccfunction.BarcodeReader;
import com.mccfunction.BarcodeReaderRequest;
import com.mccfunction.BarcodeReaderResponse;

import mcc.agh.edu.pl.lambdaproxy.IBarcodeReaderLambdaProxy;
import proxy.SmartProxy;

public class BarcodeReaderTask extends AsyncTask<BarcodeReaderRequest, Void, BarcodeReaderResponse> {

    private final Activity caller;
    private SmartProxy <BarcodeReaderRequest, BarcodeReaderResponse> smartProxy;

    public BarcodeReaderTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<BarcodeReaderRequest, BarcodeReaderResponse>(caller.getApplicationContext(), pfc);
        this.smartProxy = factory.create(IBarcodeReaderLambdaProxy.class, new BarcodeReader(), "BarcodeReader", BarcodeReaderResponse.class);
    }

    @Override
    protected BarcodeReaderResponse doInBackground(BarcodeReaderRequest... arg0) {
        return this.smartProxy.processRemotely(arg0[0]);
    }

    @Override
    protected void onPostExecute(BarcodeReaderResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %s", result.getResponse()), Toast.LENGTH_SHORT).show();
    }
}
