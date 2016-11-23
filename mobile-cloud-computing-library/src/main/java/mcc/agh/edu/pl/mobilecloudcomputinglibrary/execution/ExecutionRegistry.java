package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils.InstanceTransformer;

public class ExecutionRegistry {

    private KnowledgeRepository repository;
    private InstanceTransformer transformer;

    public ExecutionRegistry(KnowledgeRepository repository){
        this.repository = repository;
        this.transformer = new InstanceTransformer(repository.getKnowledgeData());
    }

    public void registerExecution(ExecutionModel model){
        KnowledgeInstance instance = transformer.toKnowledgeInstance(model);
        repository.addKnowledgeInstance(instance);
    }
}
