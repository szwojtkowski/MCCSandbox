package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeInstance {

    private String taskName;
    private long batteryUsage;
    private long timeUsage;
    private boolean wifiEnabled;
    private ExecutionEnvironment env;
    private Map<String, String> params;

    public KnowledgeInstance(String taskName, long batteryUsage, long timeUsage, boolean wifiEnabled,
                             Map<String, String> params, ExecutionEnvironment env){
        this.taskName = taskName;
        this.batteryUsage = batteryUsage;
        this.timeUsage = timeUsage;
        this.wifiEnabled = wifiEnabled;
        this.params = params;
        this.env = env;
    }

    public KnowledgeInstance(String taskName, long batteryUsage, long timeUsage,
                             boolean wifiEnabled, ExecutionEnvironment env){
        this(taskName, batteryUsage, timeUsage, wifiEnabled, new HashMap<String, String>(), env);
    }

    public String getTaskName() {
        return taskName;
    }

    public long getBatteryUsage() {
        return batteryUsage;
    }

    public long getTimeUsage() {
        return timeUsage;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public ExecutionEnvironment getExecutionEnvironment() {
        return env;
    }

    public Map<String, String> getParams(){
        return params;
    }
}
