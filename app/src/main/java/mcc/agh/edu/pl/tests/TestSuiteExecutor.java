package mcc.agh.edu.pl.tests;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.SmartOffloadingService;

public class TestSuiteExecutor {

    private SmartOffloadingService service;
    private TestSuite suite;

    public TestSuiteExecutor(TestSuite suite, SmartOffloadingService service){
        this.service = service;
        this.suite = suite;
    }

    public void execute(){
        for(TestRound round: suite.getRounds()){
            executeRound(round);
        }
    }

    private void executeRound(TestRound round){
        service.setOffloadingDecider(round.getRoundDecider());
        for(TestCase testCase: round.getCases()){
            service.execute(testCase.getTask(), testCase.getInput());
        }
    }
}
