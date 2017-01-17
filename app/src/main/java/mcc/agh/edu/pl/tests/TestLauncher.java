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
            "ArraySumTask,1;2;3;4;5;6;7;8;9;10;11;19;22;13;13;12",
    //        "ArraySumTask,12;22;23;24;25;26;27;28;29",
            "ArraySumTask,11;21;31;4;15;16;71;8;91;110;11",
            "ArraySumTask,31;32;33;34;53;63;73;38;39;103;113;33;999;11;1;2;3;4;5;6;7;8;9;10",
            "QuickSortTask,1;2;3;4;5;6;7;8;9;10;11;0;8;8",
            "QuickSortTask,12;22;23;24;25;26;27;28;29;210;211",
    //        "QuickSortTask,11;21;31;4;15;16;71;8",
            "QuickSortTask,31;32;33;34;53;63;73;38;39;103;113;1;2;3;4;22;14",
            "SimpleOCRTask,/storage/emulated/0/Documents/andromed.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/barak.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/bh.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/bht.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/davis.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/inwaz.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/kans.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/kansas.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/nasa.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/ncrp.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/procinw.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/rak.png",
    //        "SimpleOCRTask,/storage/emulated/0/Documents/rewkub.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/rtsz.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/wjntrz.png",
            "SimpleOCRTask,/storage/emulated/0/Documents/zebra.png"
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
