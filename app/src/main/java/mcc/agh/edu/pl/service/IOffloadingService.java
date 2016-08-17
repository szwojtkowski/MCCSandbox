package mcc.agh.edu.pl.service;

import mcc.agh.edu.pl.tasks.Task;
import mcc.agh.edu.pl.tasks.TaskResult;

public interface IOffloadingService {
    TaskResult execute(Task task);
}
