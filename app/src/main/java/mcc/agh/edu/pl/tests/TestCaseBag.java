package mcc.agh.edu.pl.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestCaseBag {

    private List<TestCase> cases = new ArrayList<>();
    private String[] tests;
    private TestCaseFactory factory;

    public TestCaseBag(List<TestCase> cases){
        this.cases = cases;
    }

    public TestCaseBag(String[] tests, TestCaseFactory factory){
        this.tests = tests;
        this.factory = factory;
    }

    public void addTestCase(TestCase testCase){
        this.cases.add(testCase);
    }

    public List<TestCase> getRandomCases(int numberOfCases){
        List<String> availableTests = new ArrayList<>(Arrays.asList(tests));

        List<TestCase> randomizedCases = new ArrayList<>();

        Random random = new Random(System.currentTimeMillis());

        for(int i = 0; i < numberOfCases; i++){
            String selectedTest = availableTests.remove(random.nextInt(availableTests.size()));
            TestCase test = factory.getTestCase(selectedTest);
            randomizedCases.add(test);
        }

        return randomizedCases;
    }

}
