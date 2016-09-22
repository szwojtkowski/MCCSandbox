package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.classifiers;

import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public class FileClassifierRepository implements ClassifierRepository{

    private String classifiersDirPath;

    public FileClassifierRepository(String classifiersDirPath) {
        this.classifiersDirPath = classifiersDirPath;
    }

    @Override
    public Classifier getClassifier(String name) {
        try {
            return (Classifier) SerializationHelper.read(path(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(String name, Classifier classifier) {
        try {
            SerializationHelper.write(path(name), classifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String path(String name){
        return String.format("%s/%s", classifiersDirPath, name);
    }
}
