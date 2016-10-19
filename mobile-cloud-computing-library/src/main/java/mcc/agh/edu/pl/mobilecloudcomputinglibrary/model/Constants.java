package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.Arrays;
import java.util.List;

public interface Constants {
    String TASK_NAME = "taskName";
    String BATTERY_USAGE = "batteryUsage";
    String TIME_USAGE = "timeUsage";
    String WIFI_ENABLED = "wifiEnabled";
    String EXECUTION_ENVIRONMENT = "executionEnvironment";

    List<String> trueFalseValues = Arrays.asList(
            Boolean.toString(true), Boolean.toString(false)
    );
}
