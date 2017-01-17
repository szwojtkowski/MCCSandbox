package mcc.agh.edu.pl.tests;

import android.app.Activity;

import java.util.Arrays;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.RandomDecider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.WekaRandomForestDecider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.FitnessAlgorithm;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness.WeightedArithmeticMean;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.SmartOffloadingService;

public class TestLauncher {

    private String[] tests = new String[]{
            "ArraySumTask,1;2;3;4;5;6;7;8;9;10;11;19;22;13;13;12"
    };

    private TestCaseFactory factory;
    private KnowledgeRepository repository;
    private SmartOffloadingService service;

    public TestLauncher(Activity caller, SmartOffloadingService service, KnowledgeRepository repository){
        this.factory = new TestCaseFactory(caller);
        this.repository = repository;
        this.service = service;
        service.setKnowledgeRepository(repository);
    }

    public void launch() {
        TestCaseBag bag = new TestCaseBag(tests, factory);

        FitnessAlgorithm algorithm = new WeightedArithmeticMean(Arrays.asList(1.0, 10.0));
        Decider randomDecider = new RandomDecider(repository);
        Decider decider = new WekaRandomForestDecider(repository, algorithm);

        TestSuiteCreator testSuiteCreator = new TestSuiteCreator(bag, randomDecider, decider);
        TestSuite testSuite = testSuiteCreator.create(8, 15);

        TestSuiteExecutor executor = TestSuiteExecutor.getInstance();
        executor.init(testSuite, service);
        executor.executeNext();
    }
}
