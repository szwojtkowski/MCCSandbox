package mcc.agh.edu.pl.service.decider.local;

import mcc.agh.edu.pl.service.decider.OffloadingDeciderService;
import mcc.agh.edu.pl.tasks.Task;

public class OffloadingDeciderLocalService implements OffloadingDeciderService {

    @Override
    public boolean shouldExecuteRemotely(Task task) {
        return false;
    }

}
