package mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.fitness;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class WeightedArithmeticMeanTest {

    @Test
    public void returnsZeroForNoWeights() throws Exception {
        double expected = 0;
        double delta = 0.00001;

        WeightedArithmeticMean algo = new WeightedArithmeticMean(new ArrayList<Double>());
        double result = algo.resultFor(Arrays.asList(2.0, 3.0, 14.1, 60.3));

        assertEquals(expected, result, delta);
    }

    @Test
    public void returnsZeroForNoValues() throws Exception {
        double expected = 0;
        double delta = 0.00001;

        WeightedArithmeticMean algo = new WeightedArithmeticMean(Arrays.asList(2.0, 3.0, 14.1, 60.3));
        double result = algo.resultFor(new ArrayList<Double>());

        assertEquals(expected, result, delta);
    }

    @Test
    public void returnsResultForAllMatchedWeightsAndValuesWhenValueMissing() throws Exception {
        double expected = 14.0;
        double delta = 0.00001;

        WeightedArithmeticMean algo = new WeightedArithmeticMean(Arrays.asList(1.0, 2.0, 3.0, 4.0));
        double result = algo.resultFor(Arrays.asList(1.0, 2.0, 3.0));

        assertEquals(expected, result, delta);
    }


    @Test
    public void returnsResultForAllMatchedWeightsAndValuesWhenWeightMissing() throws Exception {
        double expected = 14.0;
        double delta = 0.00001;

        WeightedArithmeticMean algo = new WeightedArithmeticMean(Arrays.asList(1.0, 2.0, 3.0));
        double result = algo.resultFor(Arrays.asList(1.0, 2.0, 3.0, 4.0));

        assertEquals(expected, result, delta);
    }

    @Test
    public void returnsCorrectResult() throws Exception {
        double expected = 34.1;
        double delta = 0.00001;

        WeightedArithmeticMean algo = new WeightedArithmeticMean(Arrays.asList(1.1, 2.2, 3.3, 4.4));
        double result = algo.resultFor(Arrays.asList(1.1, 2.0, 3.3, 4.0));

        assertEquals(expected, result, delta);
    }

}