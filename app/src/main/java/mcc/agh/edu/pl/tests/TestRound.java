package mcc.agh.edu.pl.tests;

import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;

public class TestRound {

    private List<TestCase> cases;
    private Decider roundDecider;

    public TestRound(List<TestCase> cases, Decider decider){
        this.cases = cases;
        this.roundDecider = decider;
    }

    public List<TestCase> getCases(){
        return cases;
    }

    public Decider getRoundDecider(){
        return roundDecider;
    }

}
