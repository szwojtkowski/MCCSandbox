package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;

public class AttributeReorder {

    private Reorder reorder;
    private Instances inputSet;

    public AttributeReorder(Instances inputSet){
        this.inputSet = inputSet;
        this.reorder = new Reorder();
    }


    public Instances moveToLast(String attributeName) throws Exception {
        String range = "first";
        int index = inputSet.attribute(attributeName).index()+1;

        for (int i = 2; i < inputSet.numAttributes()+1; i++) {
            if (index != i)
                range += "," + i;
        }
        range += "," + index;
        reorder.setAttributeIndices(range);
        reorder.setInputFormat(inputSet);
        return Filter.useFilter(inputSet, reorder);
    }

}
