package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;

public class EnvironmentFittingSelector {

    private Map<ExecutionEnvironment, Double> fitnessResults = new HashMap<>();

    public void addEnvironmentFitness(ExecutionEnvironment env, Double fitness){
        this.fitnessResults.put(env, fitness);
    }

    private ExecutionEnvironment randomEnvironment(){
        Random random = new Random();
        List<ExecutionEnvironment> envs = new ArrayList(fitnessResults.keySet());
        return envs.get(random.nextInt(1));
    }

    public ExecutionEnvironment getBestFittingEnvironment(){
        Double cloud = fitnessResults.get(ExecutionEnvironment.CLOUD);
        Double local = fitnessResults.get(ExecutionEnvironment.LOCAL);

        if(cloud == null || local == null) return ExecutionEnvironment.LOCAL;

        if(cloud.doubleValue() == local.doubleValue()) return randomEnvironment();

        if(cloud < local)
            return ExecutionEnvironment.CLOUD;
        else
            return ExecutionEnvironment.LOCAL;

    }


}
