package mcc.agh.edu.pl.tasks.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mcc.agh.edu.pl.tasks.Task;

public class CurrentTimeTask implements Task {

    @Override
    public CurrentTimeResult execute() {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        return new CurrentTimeResult(String.format("Now is %s", timeStamp));
    }
}
