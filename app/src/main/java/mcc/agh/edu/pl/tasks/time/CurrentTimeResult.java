package mcc.agh.edu.pl.tasks.time;

import mcc.agh.edu.pl.tasks.TaskResult;

public class CurrentTimeResult implements TaskResult<String> {

    private String msg;

    public CurrentTimeResult(String msg){
        this.msg = msg;
    }

    @Override
    public String getResult() {
        return msg;
    }

}
