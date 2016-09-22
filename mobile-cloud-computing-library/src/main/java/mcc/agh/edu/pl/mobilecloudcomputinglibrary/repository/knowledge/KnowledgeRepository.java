package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import weka.core.Instances;

public interface KnowledgeRepository {
    Instances getKnowledgeData();
    void addKnowledgeInstance(KnowledgeInstance instance);
}
