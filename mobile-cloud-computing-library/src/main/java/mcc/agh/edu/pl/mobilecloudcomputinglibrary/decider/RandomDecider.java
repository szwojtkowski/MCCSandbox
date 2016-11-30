package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import java.util.Random;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

public class RandomDecider implements Decider {

    private Random random;
    private KnowledgeRepository repository;

    public RandomDecider(KnowledgeRepository repository){
        this.random = new Random();
        this.repository = repository;
    }

    @Override
    public ExecutionEnvironment whereExecute(PredictionInstance instance) {
        String taskName = instance.getTaskName();

        if(!repository.isRegistered(taskName)){
            repository.registerTask(taskName);
        }

        ExecutionEnvironment[] envs = ExecutionEnvironment.values();
        int index = random.nextInt(envs.length);
        return envs[index];
    }

}
