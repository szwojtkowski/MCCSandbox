package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.mccfunction.IQuickSort;
import com.mccfunction.QuickSort;
import com.mccfunction.QuickSortRequest;
import com.mccfunction.QuickSortResponse;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.tests.TestSuiteExecutor;

public class QuickSortTask extends SmartTask<QuickSortRequest, QuickSortResponse> {

    private final Activity caller;

    public QuickSortTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<QuickSortRequest, QuickSortResponse>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(IQuickSort.class, new QuickSort(), "quicksort", QuickSortResponse.class));
    }


    @Override
    public void end(QuickSortResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %s", Arrays.toString(result.getOutputArray())), Toast.LENGTH_SHORT).show();
        TestSuiteExecutor.getInstance().executeNext();
    }
}
