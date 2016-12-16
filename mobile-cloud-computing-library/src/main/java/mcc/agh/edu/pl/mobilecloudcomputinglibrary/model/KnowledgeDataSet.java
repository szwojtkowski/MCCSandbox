package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.AddValues;
import weka.filters.unsupervised.attribute.Reorder;

public class KnowledgeDataSet implements Constants {

    private Instances dataSet;
    private final int CAPACITY = 10000;


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

        this.dataSet = new Instances("DataSet", attributes, CAPACITY);
        this.dataSet.setClassIndex(4);
    }

    public void addKnowledgeInstance(KnowledgeInstance instance){
        String taskName = instance.getTaskName();

        addNewTask(taskName);

        for(Map.Entry<String, String> param: instance.getParams().entrySet()){
            addAttribute(param.getKey(), param.getValue());
        }

        Instance newInstance = new DenseInstance(dataSet.numAttributes());

        newInstance.setValue(dataSet.attribute(TASK_NAME), taskName);
        newInstance.setValue(dataSet.attribute(BATTERY_USAGE), instance.getBatteryUsage());
        newInstance.setValue(dataSet.attribute(TIME_USAGE), instance.getTimeUsage());
        newInstance.setValue(dataSet.attribute(WIFI_ENABLED), Boolean.toString(instance.isWifiEnabled()));
        newInstance.setValue(dataSet.attribute(EXECUTION_ENVIRONMENT), instance.getExecutionEnvironment().toString());

        for(Map.Entry<String, String> param: instance.getParams().entrySet()){
            newInstance.setValue(dataSet.attribute(param.getKey()), param.getValue());
        }

        dataSet.add(newInstance);
    }

    private void addAttribute(String key, String value){
        if(dataSet.attribute(key) != null){
            addValues(key, value);
        } else {
            try {
                Add add = new Add();
                add.setAttributeName(key);
                add.setNominalLabels(value);
                add.setInputFormat(dataSet);
                dataSet = Filter.useFilter(dataSet, add);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveToLast(String attributeName) throws Exception {
        Reorder r = new Reorder();
        String range = "first";
        int index = dataSet.attribute(attributeName).index()+1;

        for (int i = 2; i < dataSet.numAttributes()+1; i++) {
            if (index != i)
                range += "," + i;
        }
        range += "," + index;
        r.setAttributeIndices(range);
        r.setInputFormat(dataSet);
        dataSet = Filter.useFilter(dataSet, r);
    }

    public void addNewTask(String taskName){
        try {
            AddValues addValues = new AddValues();
            addValues.setAttributeIndex("first");
            addValues.setLabels(taskName);
            addValues.setInputFormat(dataSet);
            dataSet = Filter.useFilter(dataSet, addValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValues(String attributeName, String value){
        try {
            moveToLast(attributeName);
            AddValues addValues = new AddValues();
            addValues.setLabels(value);
            addValues.setInputFormat(dataSet);
            dataSet = Filter.useFilter(dataSet, addValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Instances getDataSet() {
        return dataSet;
    }

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }
}
