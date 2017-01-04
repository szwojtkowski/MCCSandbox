package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;

public class AlwaysCloudDecider implements Decider {

    private KnowledgeRepository repository;

    public AlwaysCloudDecider(KnowledgeRepository repository){
        this.repository = repository;
    }

    @Override
    public ExecutionEnvironment getExecutionEnvironment(PredictionInstance instance) {
        String taskName = instance.getTaskName();

        if(!repository.isRegistered(taskName)){
            repository.registerTask(taskName);
        }
        return ExecutionEnvironment.CLOUD;
    }

}
