package mcc.agh.edu.pl.tests;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import task.SmartInput;

public class TestCase {

    private SmartTask task;
    private SmartInput input;

    public TestCase(SmartTask task, SmartInput input){
        this.task = task;
        this.input = input;
    }

    public SmartTask getTask(){
        return task;
    }

    public SmartInput getInput(){
        return input;
    }

}
