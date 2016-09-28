package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

//TODO add additional parameters to PredictionInstance - make them generic
public class PredictionInstance {

    private String taskName;
    private boolean wifiEnabled;

    public PredictionInstance(String taskName, boolean wifiEnabled) {
        this.taskName = taskName;
        this.wifiEnabled = wifiEnabled;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }
}
