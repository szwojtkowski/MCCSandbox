package mcc.agh.edu.pl.mobilecloudcomputinglibrary.service;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import task.SmartRequest;

public interface SmartOffloadingService {
    void execute(SmartTask task, SmartRequest input);
}
