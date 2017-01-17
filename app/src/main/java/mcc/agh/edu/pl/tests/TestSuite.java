package mcc.agh.edu.pl.tests;

import java.util.List;

public class TestSuite {

    private List<TestRound> rounds;

    public TestSuite(List<TestRound> rounds){
        this.rounds = rounds;
    }

    public List<TestRound> getRounds(){
        return rounds;
    }


}
