package mcc.agh.edu.pl.tasks;

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
