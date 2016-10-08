package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Random;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;

public class RandomDecider implements Decider {

    private Random random;

    public RandomDecider(){
        this.random = new Random();
    }

    @Override
    public ExecutionEnvironment whereExecute(PredictionInstance instance) {
        ExecutionEnvironment[] envs = ExecutionEnvironment.values();
        int index = random.nextInt(envs.length);
        return envs[index];
    }

}
