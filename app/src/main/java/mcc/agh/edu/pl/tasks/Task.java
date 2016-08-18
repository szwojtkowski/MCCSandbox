package mcc.agh.edu.pl.tasks;

import java.io.Serializable;

public interface Task<T> extends Serializable{
    TaskResult<T> execute();
}
