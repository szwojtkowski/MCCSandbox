package mcc.agh.edu.pl.service.decider.local;

import mcc.agh.edu.pl.service.decider.IOffloadingDeciderService;
import mcc.agh.edu.pl.tasks.Task;

public class OffloadingDeciderLocalService implements IOffloadingDeciderService{

    @Override
    public boolean shouldExecuteRemotely(Task task) {
        return false;
    }

}
