package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors;

import weka.core.Instances;

public class WekaTreeResultExtractor implements Extractor {

    private Instances set;

    public WekaTreeResultExtractor(Instances set) {
        this.set = set;
    }

    @Override
    public double extractResult(String attrName, Double res) {
        int index = (int) res.doubleValue();
        set.setClass(set.attribute(attrName));
        return Double.valueOf(set.classAttribute().value(index));
    }
}
