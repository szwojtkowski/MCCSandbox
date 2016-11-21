package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

public class ArffHelper {

    private File getFile(String filePath) throws IOException {
        File myFile = new File(Environment.getExternalStorageDirectory(), filePath);
        if (!myFile.exists()) {
            myFile.mkdirs();
            myFile.createNewFile();
        }
        return myFile;
    }

    public Instances load(String path) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setFile(getFile(path));
        return loader.getDataSet();
    }

    public void save(String path, Instances data) {
        ArffSaver saver = new ArffSaver();
        try {
            saver.setFile(getFile(path));
            saver.setInstances(data);
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(String path, Instances data) {
        data.clear();
        save(path, data);
    }

}
