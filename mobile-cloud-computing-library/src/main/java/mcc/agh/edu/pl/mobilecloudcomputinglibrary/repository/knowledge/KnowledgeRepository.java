package mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeDataSet;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.KnowledgeInstance;

public interface KnowledgeRepository {
    KnowledgeDataSet getKnowledgeData();
    void addKnowledgeInstance(KnowledgeInstance instance);
    void clearKnowledgeDataSet();
}
