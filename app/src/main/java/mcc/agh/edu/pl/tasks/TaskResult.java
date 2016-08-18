package mcc.agh.edu.pl.tasks;

import java.io.Serializable;

public interface TaskResult<T> extends Serializable {
    T getResult();
}
