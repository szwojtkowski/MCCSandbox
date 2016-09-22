package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class KnowledgeDataSet {

    private static final String BATTERY_USAGE = "batteryUsage";
    private static final String TIME_USAGE = "timeUsage";
    private static final String WIFI_ENABLED = "wifiEnabled";
    private static final String EXECUTE_REMOTELY = "executeRemotely";

    private Instances dataSet;

    public KnowledgeDataSet() {

        List<String> trueFalseValues = Arrays.asList(
                Boolean.toString(true), Boolean.toString(false)
        );

        Attribute batteryAttr = new Attribute(BATTERY_USAGE);
        Attribute timeAttr = new Attribute(TIME_USAGE);
        Attribute wifiAttr = new Attribute(WIFI_ENABLED, trueFalseValues);
        Attribute execAttr = new Attribute(EXECUTE_REMOTELY, trueFalseValues);

        ArrayList<Attribute> attributes = new ArrayList<>(Arrays.asList(
                batteryAttr, timeAttr, wifiAttr, execAttr)
        );

        this.dataSet = new Instances("DataSet", attributes, 10);
        this.dataSet.setClassIndex(3);
    }

    public void addKnowledgeInstance(KnowledgeInstance instance){
        Instance newInstance = new DenseInstance(4);

        newInstance.setValue(dataSet.attribute(BATTERY_USAGE), instance.getBatteryUsage());
        newInstance.setValue(dataSet.attribute(TIME_USAGE), instance.getTimeUsage());
        newInstance.setValue(dataSet.attribute(WIFI_ENABLED), Boolean.toString(instance.isWifiEnabled()));
        newInstance.setValue(dataSet.attribute(EXECUTE_REMOTELY), Boolean.toString(instance.shouldBeExecutedRemotely()));

        dataSet.add(newInstance);
    }

    public Instances getDataSet() {
        return dataSet;
    }

}
