package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;

public interface Decider {
    ExecutionEnvironment whereExecute(PredictionInstance instance);
}
