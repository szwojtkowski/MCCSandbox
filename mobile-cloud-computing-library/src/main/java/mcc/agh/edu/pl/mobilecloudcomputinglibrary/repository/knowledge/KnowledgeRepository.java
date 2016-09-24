package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;
import weka.core.Instances;

public interface KnowledgeRepository {
    Instances getKnowledgeData();
    void addKnowledgeInstance(KnowledgeInstance instance);
}
