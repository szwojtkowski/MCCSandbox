package mcc.agh.edu.pl.tests;

import java.util.List;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.SmartOffloadingService;

public class TestSuiteExecutor {

    private static final TestSuiteExecutor instance = new TestSuiteExecutor();

    private SmartOffloadingService service;
    private TestSuite suite;
    private int roundNo = 0;
    private int caseNo = 0;

    private TestSuiteExecutor(){}

    public static TestSuiteExecutor getInstance(){
        return instance;
    }

    public void init(TestSuite suite, SmartOffloadingService service){
        this.service = service;
        this.suite = suite;
        this.roundNo = 0;
        this.caseNo = 0;
    }

    public synchronized void executeNext(){
        if(suite == null)
            return;

        List<TestRound> rounds = suite.getRounds();

        if(roundNo < rounds.size()){
            executeNextRoundCase(rounds.get(roundNo));
        }
    }

    private void executeNextRoundCase(TestRound round){
        service.setOffloadingDecider(round.getRoundDecider());
        List<TestCase> testCases = round.getCases();
        if(caseNo < testCases.size()){
            TestCase testCase = testCases.get(caseNo);
            service.execute(testCase.getTask(), testCase.getInput());
            caseNo += 1;
        } else {
            roundNo += 1;
            caseNo = 0;
            executeNext();
        }
    }
}
