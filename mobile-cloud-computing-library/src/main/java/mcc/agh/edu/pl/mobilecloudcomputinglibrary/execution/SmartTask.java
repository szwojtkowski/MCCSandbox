package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.BatteryMonitor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import proxy.SmartProxy;
import task.SmartInput;
import task.SmartOutput;


public abstract class SmartTask <Q extends SmartInput, R extends SmartOutput> extends AsyncTask<Q, Void, R> {

    private SmartProxy <Q, R> smartProxy;
    private ExecutionEnvironment type = ExecutionEnvironment.LOCAL;
    protected ExecutionModel executionModel;

    private ExecutionRegistry executionRegistry;
    private BatteryMonitor batteryMonitor;
    private WifiManager wifi;

    private long startTime = 0;
    private long startBatteryLevel = 0;
    private Map<String, String> params;

    public SmartTask() {
        this.executionModel = new ExecutionModel();
        this.params = new HashMap<>();
    }

    public R processLocally(Q arg) {
        return this.smartProxy.processLocally(arg);
    }

    public void executeRemotely(Q arg) {
        this.startTime = System.currentTimeMillis();
        if(batteryMonitor != null) this.startBatteryLevel = batteryMonitor.getBatteryLevel();
        this.type = ExecutionEnvironment.CLOUD;
        this.execute(arg);
    }

    public void executeLocally(Q arg) {
        this.startTime = System.currentTimeMillis();
        if(batteryMonitor != null) this.startBatteryLevel = batteryMonitor.getBatteryLevel();
        this.type = ExecutionEnvironment.LOCAL;
        this.execute(arg);
    }

    public abstract void end(R result);

    @Override
    protected final R doInBackground(Q ... arg) {
        switch (this.type) {
            case CLOUD:
                return this.smartProxy.processRemotely(arg[0]);
            case LOCAL:
                return this.processLocally(arg[0]);
        }
        return this.processLocally(arg[0]);
    }

    @Override
    protected void onPostExecute(R result) {
        if(batteryMonitor != null) {
            this.executionModel.setBatteryUsage(batteryMonitor.getBatteryLevel() - this.startBatteryLevel);
        }
        if(wifi != null) {
            this.executionModel.setWifiEnabled(wifi.isWifiEnabled());
        }
        this.executionModel.setMillisElapsed(System.currentTimeMillis() - this.startTime);
        this.executionModel.setName(this.getName());
        this.executionModel.setExecutionEnvironment(this.type);
        if(executionRegistry != null) {
            this.executionRegistry.registerExecution(executionModel);
        }
        this.end(result);
    }

    public String getName(){
        return this.getClass().getSimpleName();
    }

    public void setSmartProxy(SmartProxy<Q, R> smartProxy) {
        this.smartProxy = smartProxy;
    }

    public void setExecutionRegistry(ExecutionRegistry registry){
        this.executionRegistry = registry;
    }

    public void setBatteryMonitor(BatteryMonitor batteryMonitor){
        this.batteryMonitor = batteryMonitor;
    }

    public void setWifiManager(WifiManager manager){
        this.wifi = manager;
    }

    public void addParam(String key, String value){
        this.params.put(key, value);
    }

    public Map<String, String> getParams(){
        return this.params;
    }
}
