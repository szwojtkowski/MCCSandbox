package mcc.agh.edu.pl.mobilecloudcomputinglibrary.service;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import task.SmartInput;

public interface SmartOffloadingService {
    void execute(SmartTask task, SmartInput input);
    void setKnowledgeRepository(KnowledgeRepository repository);
    void setOffloadingDecider(Decider decider);
}
