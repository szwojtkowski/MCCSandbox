package mcc.agh.edu.pl.mobilecloudcomputinglibrary.utils;

import java.util.ArrayList;
import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;

public class SmartTaskTransformer {

    public List<String> toSimpleTasksNameList(List<SmartTask> smartTasks){
        List<String> names = new ArrayList<>();
        for(SmartTask task: smartTasks){
            names.add(task.getName());
        }
        return names;
    }

}
