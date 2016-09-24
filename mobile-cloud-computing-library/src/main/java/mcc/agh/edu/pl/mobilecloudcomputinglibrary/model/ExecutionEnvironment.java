package mcc.agh.edu.pl.mobilecloudcomputinglibrary.model;

import java.util.ArrayList;
import java.util.List;

public enum ExecutionEnvironment {
    CLOUD("cloud"),
    LOCAL("local");

    private final String name;

    ExecutionEnvironment(String s) {
        name = s;
    }

    public static List<String> stringValues(){
        List<String> strings = new ArrayList<>();
        for(ExecutionEnvironment val: values()){
            strings.add(val.toString());
        }
        return strings;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}