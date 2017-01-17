package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddValues;

public class AttributeValueAdder {

    private AddValues add;
    private Instances inputSet;
    private Instances added;

    public AttributeValueAdder(Instances inputSet, String attributeName, String values){
        this.inputSet = inputSet;
        this.add = new AddValues();
        add(attributeName, values);
    }

    public Instances valuesAdded(){
        if(added != null){
            return added;
        } else {
            return inputSet;
        }
    }

    private void add(String attributeName, String values){
        try {
            AttributeReorder reorder = new AttributeReorder(inputSet);
            Instances data = reorder.moveToLast(attributeName);
            add.setLabels(values);
            add.setInputFormat(data);
            added =  Filter.useFilter(data, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
