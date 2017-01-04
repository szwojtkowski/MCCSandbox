package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.example.ArraySum;
import com.example.ArraySumInput;
import com.example.ArraySumOutput;

import java.util.HashMap;
import java.util.Map;

import mcc.agh.edu.pl.lambdaproxy.IArraySumLambdaProxy;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.tests.TestSuiteExecutor;
import task.SmartInput;

public class ArraySumTask extends SmartTask<ArraySumInput, ArraySumOutput> {

    private final Activity caller;

    public ArraySumTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<ArraySumInput, ArraySumOutput>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(IArraySumLambdaProxy.class, new ArraySum(), "ArraySum", ArraySumOutput.class));
    }

    @Override
    public void executeRemotely(ArraySumInput arg) {
        this.addParam("size", String.valueOf(arg.getArray().length*1000));
        super.executeRemotely(arg);
    }

    @Override
    public void executeLocally(ArraySumInput arg) {
        this.addParam("size", String.valueOf(arg.getArray().length*1000));
        super.executeLocally(arg);
    }

    @Override
    public Map<String, String> getParamsFromInput(SmartInput input) {
        Map<String, String> params = new HashMap<>();
        params.put("size", String.valueOf(((ArraySumInput) input).getArray().length*1000));
        return params;
    }

    @Override
    public void end(ArraySumOutput result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %f", result.getSum()), Toast.LENGTH_SHORT).show();
        TestSuiteExecutor.getInstance().executeNext();
    }
}
