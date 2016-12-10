package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;

public class ExecutionRegistry {

    private KnowledgeRepository repository;

    public ExecutionRegistry(KnowledgeRepository repository){
        this.repository = repository;
    }

    public void registerExecution(ExecutionModel model){
        InstanceTransformer transformer = new InstanceTransformer(repository.getKnowledgeData());
        KnowledgeInstance instance = transformer.toKnowledgeInstance(model);
        repository.addKnowledgeInstance(instance);
    }
}
