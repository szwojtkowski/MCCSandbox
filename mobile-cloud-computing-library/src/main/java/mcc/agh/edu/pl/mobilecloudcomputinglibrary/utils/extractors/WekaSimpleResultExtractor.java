package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.extractors;

public class WekaSimpleResultExtractor implements Extractor {

    @Override
    public double extractResult(String attrName, Double res) {
        return res;
    }
}
