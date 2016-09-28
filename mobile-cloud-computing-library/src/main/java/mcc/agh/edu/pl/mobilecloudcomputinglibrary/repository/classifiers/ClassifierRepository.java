package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.classifiers;

import weka.classifiers.Classifier;

public interface ClassifierRepository {
    Classifier getClassifier(String name);
    void save(String name, Classifier classifier);
}
