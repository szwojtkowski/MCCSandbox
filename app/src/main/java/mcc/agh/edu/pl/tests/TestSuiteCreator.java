package mcc.agh.edu.pl.tests;

import java.util.ArrayList;
import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;

public class TestSuiteCreator {

    private TestCaseBag bag;
    private Decider firstRoundDecider;
    private Decider otherRoundsDecider;

    public TestSuiteCreator(TestCaseBag bag, Decider firstRoundDecider, Decider otherRoundsDecider){
        this.bag = bag;
        this.firstRoundDecider = firstRoundDecider;
        this.otherRoundsDecider = otherRoundsDecider;
    }

    public TestSuite create(int numberOfRounds, int numberOfCases){
        List<TestRound> rounds = new ArrayList<>();

        rounds.add(new TestRound(bag.getRandomCases(numberOfCases), firstRoundDecider));

        for(int i = 1; i < numberOfRounds; i++){
            rounds.add(new TestRound(bag.getRandomCases(numberOfCases), otherRoundsDecider));
        }

        return new TestSuite(rounds);
    }


}
