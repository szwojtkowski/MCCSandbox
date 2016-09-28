package mcc.agh.edu.pl.service.decider;

import mcc.agh.edu.pl.tasks.Task;

public interface OffloadingDeciderService {
    boolean shouldExecuteRemotely(Task task);
}
