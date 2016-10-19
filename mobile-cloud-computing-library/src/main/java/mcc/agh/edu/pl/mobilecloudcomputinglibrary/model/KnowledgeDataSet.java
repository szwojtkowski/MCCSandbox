package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class KnowledgeDataSet implements Constants {

    private Instances dataSet;


    public KnowledgeDataSet(Set<String> registeredTasksNames) {
        List<String> tasksNames = new ArrayList<>(registeredTasksNames);

        Attribute nameAttr = new Attribute(TASK_NAME, tasksNames);
        Attribute batteryAttr = new Attribute(BATTERY_USAGE);
        Attribute timeAttr = new Attribute(TIME_USAGE);
        Attribute wifiAttr = new Attribute(WIFI_ENABLED, trueFalseValues);
        Attribute execAttr = new Attribute(EXECUTION_ENVIRONMENT, ExecutionEnvironment.stringValues());

        ArrayList<Attribute> attributes = new ArrayList<>(Arrays.asList(
                nameAttr, batteryAttr, timeAttr, wifiAttr, execAttr)
        );

        this.dataSet = new Instances("DataSet", attributes, 10);
        this.dataSet.setClassIndex(4);
    }

    public void addKnowledgeInstance(KnowledgeInstance instance){
        String taskName = instance.getTaskName();

        Instance newInstance = new DenseInstance(5);

        newInstance.setValue(dataSet.attribute(TASK_NAME), taskName);
        newInstance.setValue(dataSet.attribute(BATTERY_USAGE), instance.getBatteryUsage());
        newInstance.setValue(dataSet.attribute(TIME_USAGE), instance.getTimeUsage());
        newInstance.setValue(dataSet.attribute(WIFI_ENABLED), Boolean.toString(instance.isWifiEnabled()));
        newInstance.setValue(dataSet.attribute(EXECUTION_ENVIRONMENT), instance.getExecutionEnvironment().toString());

        dataSet.add(newInstance);
    }

    public Instances getDataSet() {
        return dataSet;
    }

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }
}
