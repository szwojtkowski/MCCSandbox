package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import java.util.HashMap;
import java.util.Map;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;

public class ExecutionModel {

    private String name;
    private long millisElapsed;
    private long batteryUsage;
    private ExecutionEnvironment environment;
    private boolean wifiEnabled = false;
    private Map<String, String> params = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMillisElapsed() {
        return millisElapsed;
    }

    public void setMillisElapsed(long millisElapsed) {
        this.millisElapsed = millisElapsed;
    }

    public void setExecutionEnvironment(ExecutionEnvironment env){
        this.environment = env;
    }

    public ExecutionEnvironment getExecutionEnvironment(){
        return this.environment;
    }

    public long getBatteryUsage() {
        return batteryUsage;
    }

    public void setBatteryUsage(long batteryUsage) {
        this.batteryUsage = batteryUsage;
    }

    public boolean getWifiEnabled(){
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean enabled){
        this.wifiEnabled = enabled;
    }

    public void setParams(Map<String, String> params){
        this.params = params;
    }

    public Map<String, String> getParams(){
        return this.params;
    }
}
