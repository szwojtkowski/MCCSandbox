package mcc.agh.edu.pl.tests;

import android.app.Activity;
import android.widget.TextView;

import com.example.ArraySumRequest;
import com.mccfunction.OCRLang;
import com.mccfunction.QuickSortRequest;
import com.mccfunction.SimpleOCRRequest;

import java.util.ArrayList;
import java.util.List;

import mcc.agh.edu.pl.sandbox.R;
import mcc.agh.edu.pl.sandbox.handlers.TextHandler;
import mcc.agh.edu.pl.sandbox.handlers.TextViewSetTextHandler;
import mcc.agh.edu.pl.tasks.ArraySumTask;
import mcc.agh.edu.pl.tasks.QuickSortTask;
import mcc.agh.edu.pl.tasks.SimpleOCRTask;
import mcc.agh.edu.pl.util.FileHelper;

public class TestCaseFactory {

    private Activity caller;

    public TestCaseFactory(Activity caller){
        this.caller = caller;
    }


    public TestCase getTestCase(String testCase){
        String[] criteria = testCase.split(",");
        String taskName = criteria[0];
        TestCase taskCase = null;
        switch(taskName) {
            case "ArraySumTask": taskCase = createArraySumTestCase(criteria); break;
            case "QuickSortTask": taskCase = createQuickSortTestCase(criteria); break;
            case "SimpleOCRTask": taskCase = createSimpleOCRTestCase(criteria); break;
        }
        return taskCase;
    }

    private TestCase createQuickSortTestCase(String[] criteria){
        QuickSortTask task = new QuickSortTask(caller);
        QuickSortRequest request = new QuickSortRequest(getNumbersArray(criteria[1]));
        return new TestCase(task, request);
    }

    private TestCase createSimpleOCRTestCase(String[] criteria){
        byte[] b = FileHelper.getImageAsByteArray(criteria[1]);
        TextHandler handler = new TextViewSetTextHandler((TextView) caller.findViewById(R.id.text));
        SimpleOCRRequest ocrRequest = new SimpleOCRRequest(b, OCRLang.POL);
        SimpleOCRTask ocrTask = new SimpleOCRTask(caller, handler);
        return new TestCase(ocrTask, ocrRequest);
    }

    private TestCase createArraySumTestCase(String[] criteria){
        ArraySumTask task = new ArraySumTask(caller);
        ArraySumRequest request = new ArraySumRequest(getNumbersArray(criteria[1]));
        return new TestCase(task, request);
    }

    private double[] getNumbersArray(String numbers){
        List<Double> nums = new ArrayList<>();
        String[] stringNumbers = numbers.split(";");
        for(String number: stringNumbers){
            nums.add(Double.parseDouble(number));
        }
        double[] doubleNums = new double[nums.size()];
        for(int i = 0; i < nums.size(); ++i) {
            doubleNums[i] = nums.get(i);
        }
        return doubleNums;
    }



}
