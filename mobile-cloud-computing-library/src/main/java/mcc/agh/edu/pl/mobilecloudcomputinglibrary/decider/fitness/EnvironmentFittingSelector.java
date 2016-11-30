package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness;

import java.util.TreeMap;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;

public class EnvironmentFittingSelector {

    TreeMap<Double, ExecutionEnvironment> fitnessResults = new TreeMap<>();

    public void addEnvironmentFitness(ExecutionEnvironment env, Double fitness){
        this.fitnessResults.put(fitness, env);
    }

    public ExecutionEnvironment getBestFittingEnvironment(){
        return fitnessResults.firstEntry().getValue();
    }


}
