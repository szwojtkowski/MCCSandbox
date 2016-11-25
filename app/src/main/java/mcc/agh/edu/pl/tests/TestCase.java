package mcc.agh.edu.pl.tests;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import task.SmartRequest;

public class TestCase {

    private SmartTask task;
    private SmartRequest input;

    public TestCase(SmartTask task, SmartRequest input){
        this.task = task;
        this.input = input;
    }

    public SmartTask getTask(){
        return task;
    }

    public SmartRequest getInput(){
        return input;
    }

}
