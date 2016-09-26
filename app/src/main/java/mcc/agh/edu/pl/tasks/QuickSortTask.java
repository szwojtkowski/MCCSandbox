package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.example.smartmcc.ProxyFactory;
import com.example.smartmcc.ProxyFactoryConfiguration;
import com.mccfunction.IQuickSort;
import com.mccfunction.QuickSort;
import com.mccfunction.QuickSortRequest;
import com.mccfunction.QuickSortResponse;

import java.util.Arrays;
import proxy.SmartProxy;

public class QuickSortTask extends AsyncTask<QuickSortRequest, Void, QuickSortResponse> {

    private final Activity caller;
    private SmartProxy<QuickSortRequest, QuickSortResponse> smartProxy;

    public QuickSortTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<QuickSortRequest, QuickSortResponse>(caller.getApplicationContext(), pfc);
        this.smartProxy = factory.create(IQuickSort.class, new QuickSort(), "quicksort", QuickSortResponse.class);
    }


    @Override
    protected QuickSortResponse doInBackground(QuickSortRequest... arg0) {
        return this.smartProxy.processRemotely(arg0[0]);
    }

    @Override
    protected void onPostExecute(QuickSortResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %s", Arrays.toString(result.getOutputArray())), Toast.LENGTH_SHORT).show();
    }
}
