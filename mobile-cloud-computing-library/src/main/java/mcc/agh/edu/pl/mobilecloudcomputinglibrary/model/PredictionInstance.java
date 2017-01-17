package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.HashMap;
import java.util.Map;

//TODO add additional parameters to PredictionInstance - make them generic
public class PredictionInstance {

    private String taskName;
    private boolean wifiEnabled;
    private Map<String, String> params;

    public PredictionInstance(String taskName, boolean wifiEnabled) {
        this.taskName = taskName;
        this.wifiEnabled = wifiEnabled;
        this.params = new HashMap<>();
    }

    public PredictionInstance(String taskName, boolean wifiEnabled, Map<String, String> params) {
        this.taskName = taskName;
        this.wifiEnabled = wifiEnabled;
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }
}
