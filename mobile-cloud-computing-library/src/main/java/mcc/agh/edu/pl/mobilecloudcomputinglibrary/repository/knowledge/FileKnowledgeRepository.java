package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.Constants;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.ArffHelper;
import weka.core.Instances;

public class FileKnowledgeRepository implements KnowledgeRepository{

    private final String TAG = getClass().getSimpleName();

    private String filePath;
    private KnowledgeDataSet dataSet;
    private Set<String> registeredTasks;
    private ArffHelper arffHelper;


    public FileKnowledgeRepository(String filePath){
        this.registeredTasks = new HashSet<>();
        this.filePath = filePath;
        this.arffHelper = new ArffHelper();
        this.dataSet = new KnowledgeDataSet(registeredTasks);
        load();
    }

    private void load(){
        try {
            Instances set = arffHelper.load(filePath);
            registeredTasks.addAll(Collections.list(set.attribute(Constants.TASK_NAME).enumerateValues()));
            dataSet.setDataSet(set);
        } catch (IOException e) {}
    }

    @Override
    public KnowledgeDataSet getKnowledgeData() {
        load();
        return dataSet;
    }

    @Override
    public void addKnowledgeInstance(KnowledgeInstance instance) {
        registerTask(instance.getTaskName());
        dataSet.addKnowledgeInstance(instance);
        Instances instances = dataSet.getDataSet();
        arffHelper.save(filePath, instances);
        Log.i(TAG, "Added new instance to knowledge repository");
        instances.setRelationName("");
        Log.d(TAG, instances.toString());
    }

    @Override
    public void clearKnowledgeDataSet() {
        arffHelper.clear(filePath, new KnowledgeDataSet(new HashSet<String>()).getDataSet());
    }

    @Override
    public void registerTask(String name) {
        dataSet.addNewTask(name);
    }

    @Override
    public boolean isRegistered(String name) {
        return registeredTasks.contains(name);
    }
}
