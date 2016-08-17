package mcc.agh.edu.pl.tasks;

public interface Task<T> {
    TaskResult<T> execute();
}
