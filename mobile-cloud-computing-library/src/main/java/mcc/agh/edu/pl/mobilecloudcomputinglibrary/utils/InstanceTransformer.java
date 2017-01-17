package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ExecutionModel;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class InstanceTransformer implements Constants{

    private KnowledgeDataSet knowledgeDataSet;

    public InstanceTransformer(KnowledgeDataSet knowledgeDataSet) {
        this.knowledgeDataSet = knowledgeDataSet;
    }

    public Instance toInstance(PredictionInstance instance){
        Instances set = knowledgeDataSet.getDataSet();
        Instance i = new DenseInstance(5);

        i.setValue(set.attribute(TASK_NAME), instance.getTaskName());
        i.setValue(set.attribute(WIFI_ENABLED), Boolean.toString(instance.isWifiEnabled()));

        i.setMissing(set.attribute(BATTERY_USAGE));
        i.setMissing(set.attribute(TIME_USAGE));

        i.setDataset(set);

        return i;
    }

    public Instance addEnvironment(Instance instance, ExecutionEnvironment env){
        instance.setValue(knowledgeDataSet.getDataSet().attribute(EXECUTION_ENVIRONMENT), env.toString());
        return instance;
    }

    public Instances toNewKnowledgeDataSetFormat(Instances format){
        for(Instance i: knowledgeDataSet.getDataSet()){
            i.setDataset(format);
            format.add(i);
        }
        return format;
    }

    //TODO remove default values
    public KnowledgeInstance toKnowledgeInstance(ExecutionModel model){
        KnowledgeInstance instance = new KnowledgeInstance(model.getName(), model.getBatteryUsage(),
                model.getMillisElapsed(), model.getWifiEnabled(), model.getExecutionEnvironment());
        return instance;
    }
}
