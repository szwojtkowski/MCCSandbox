package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

public class KnowledgeInstance {

    private int batteryUsage;
    private int timeUsage;
    private boolean wifiEnabled;
    private boolean executeRemotely;

    public KnowledgeInstance(int batteryUsage, int timeUsage, boolean wifiEnabled, boolean executeRemotely){
        this.batteryUsage = batteryUsage;
        this.timeUsage = timeUsage;
        this.wifiEnabled = wifiEnabled;
        this.executeRemotely = executeRemotely;
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

    public boolean shouldBeExecutedRemotely() {
        return executeRemotely;
    }
}
