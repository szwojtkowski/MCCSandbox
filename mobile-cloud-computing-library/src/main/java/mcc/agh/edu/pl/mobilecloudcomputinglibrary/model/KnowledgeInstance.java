package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

public class KnowledgeInstance {

    private String taskName;
    private int batteryUsage;
    private int timeUsage;
    private boolean wifiEnabled;
    private ExecutionEnvironment env;

    public KnowledgeInstance(String taskName, int batteryUsage, int timeUsage, boolean wifiEnabled, ExecutionEnvironment env){
        this.taskName = taskName;
        this.batteryUsage = batteryUsage;
        this.timeUsage = timeUsage;
        this.wifiEnabled = wifiEnabled;
        this.env = env;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getBatteryUsage() {
        return batteryUsage;
    }

    public int getTimeUsage() {
        return timeUsage;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public ExecutionEnvironment getExecutionEnvironment() {
        return env;
    }
}
