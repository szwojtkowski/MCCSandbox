package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeightedArithmeticMean implements FitnessAlgorithm {

    private List<Double> weights;

    public WeightedArithmeticMean(List<Double> weights) {
        if(weights != null)
            this.weights = weights;
        else
            this.weights = new ArrayList<>();
    }

    public Double resultFor(List<Double> values){
        Iterator<Double> weightsIter = weights.iterator();
        Iterator<Double> valuesIter = values.iterator();
        double result = 0;
        while(weightsIter.hasNext() && valuesIter.hasNext()){
            result += weightsIter.next() * valuesIter.next();
        }
        return result;
    }


}
