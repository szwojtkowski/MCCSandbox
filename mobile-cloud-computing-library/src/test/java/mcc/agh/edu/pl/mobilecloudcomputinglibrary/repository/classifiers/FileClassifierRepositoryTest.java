package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.classifiers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import static org.junit.Assert.assertEquals;

public class FileClassifierRepositoryTest {

    private static final String PATH = "./weka";
    private FileClassifierRepository repository;
    private Instances dataSet;

    @Before
    public void createRepository(){
        this.repository = new FileClassifierRepository(PATH);
    }

    @Before
    public void prepareDataSet(){
        Attribute a = new Attribute("zero");
        Attribute b = new Attribute("one");
        Attribute result = new Attribute("result", Arrays.asList("0", "1"));
        ArrayList<Attribute> attributes = new ArrayList<>(Arrays.asList(a, b, result));

        this.dataSet = new Instances("Set", attributes, 10);
        this.dataSet.setClassIndex(2);

        Instance example = new DenseInstance(3);
        example.setValue(a, 1.0);
        example.setValue(b, 1.0);
        example.setValue(result, "0");

        dataSet.add(example);
    }

    @Test
    public void persistsClassifier() throws Exception {
        String clsName = "testCls";
        Classifier cls = new J48();
        cls.buildClassifier(dataSet);

        repository.save(clsName, cls);
        Classifier loaded = repository.getClassifier(clsName);
        assertEquals(cls.toString(), loaded.toString());
    }

}