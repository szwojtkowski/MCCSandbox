package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import android.os.Environment;

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

    private File getFile() throws IOException {
        File myFile = new File(Environment.getExternalStorageDirectory(), filePath);
        if (!myFile.exists()) {
            myFile.mkdirs();
            myFile.createNewFile();
        }
        return myFile;
    }

    @Override
    public KnowledgeDataSet getKnowledgeData() {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(getFile());
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
            saver.setFile(getFile());
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
            saver.setFile(getFile());
            saver.setInstances(dataSet.getDataSet());
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
