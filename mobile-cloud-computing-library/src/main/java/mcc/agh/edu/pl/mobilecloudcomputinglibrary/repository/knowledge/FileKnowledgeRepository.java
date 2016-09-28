package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import java.io.File;
import java.io.IOException;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

public class FileKnowledgeRepository implements KnowledgeRepository{

    private String filePath;
    private KnowledgeDataSet dataSet;

    public FileKnowledgeRepository(String filePath){
        this.filePath = filePath;
        this.dataSet = new KnowledgeDataSet();
    }

    @Override
    public KnowledgeDataSet getKnowledgeData() {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(filePath));
            Instances set = loader.getDataSet();
            dataSet.setDataSet(set);
            return dataSet;
        } catch (IOException e) {}
        return new KnowledgeDataSet();
    }

    @Override
    public void addKnowledgeInstance(KnowledgeInstance instance) {
        try {
            dataSet.addKnowledgeInstance(instance);
            ArffSaver saver = new ArffSaver();
            saver.setFile(new File(filePath));
            saver.setInstances(dataSet.getDataSet());
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearKnowledgeDataSet() {
        try {
            dataSet.getDataSet().clear();
            ArffSaver saver = new ArffSaver();
            saver.setFile(new File(filePath));
            saver.setInstances(dataSet.getDataSet());
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
