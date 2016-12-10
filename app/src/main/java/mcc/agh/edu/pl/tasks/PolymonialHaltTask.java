package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.mccfunction.IPolymonialHalt;
import com.mccfunction.PolymonialHalt;
import com.mccfunction.PolymonialHaltInput;
import com.mccfunction.PolymonialHaltOutput;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.tests.TestSuiteExecutor;

public class PolymonialHaltTask extends SmartTask<PolymonialHaltInput, PolymonialHaltOutput> {

    private final Activity caller;

    public PolymonialHaltTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<PolymonialHaltInput, PolymonialHaltOutput>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(IPolymonialHalt.class, new PolymonialHalt(), "PolymonialHalt", PolymonialHaltOutput.class));
    }

    @Override
    public void end(PolymonialHaltOutput result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %b", result.isStatus()), Toast.LENGTH_SHORT).show();
        TestSuiteExecutor.getInstance().executeNext();

    }
}
