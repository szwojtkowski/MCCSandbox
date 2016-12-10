package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.amazonaws.regions.Regions;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.mccfunction.ISimpleOCR;
import com.mccfunction.OCRLang;
import com.mccfunction.SimpleOCR;
import com.mccfunction.SimpleOCRInput;
import com.mccfunction.SimpleOCROutput;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.sandbox.handlers.TextHandler;
import mcc.agh.edu.pl.tests.TestSuiteExecutor;

public class SimpleOCRTask extends SmartTask<SimpleOCRInput, SimpleOCROutput> {

    private final Activity caller;
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/MCCSandbox/";
    private final TextHandler setTextHandler;
    private TessBaseAPI tessBaseApi;
    private String lang = OCRLang.getLanguage(OCRLang.ENG);


    public SimpleOCRTask(Activity caller, TextHandler handler) {
        this.caller = caller;
        this.setTextHandler = handler;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<SimpleOCRInput, SimpleOCROutput>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(ISimpleOCR.class, new SimpleOCR(), "SimpleOCR", SimpleOCROutput.class));
    }

    @Override
    public void end(SimpleOCROutput result) {
        if (result != null) {
            this.setTextHandler.setText(result.getText());
        }
        TestSuiteExecutor.getInstance().executeNext();
    }

    @Override
    public SimpleOCROutput processLocally(SimpleOCRInput arg) {
        this.lang = OCRLang.getLanguage(arg.getLanguage());
        Bitmap bitmap = BitmapFactory.decodeByteArray(arg.getPayload(), 0, arg.getPayload().length);
        String result = startOCR(bitmap);
        return new SimpleOCROutput(result);
    }

    private String startOCR(Bitmap bitmap) {
        String result = "";
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.

            return extractText(bitmap);


        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
        return result;
    }

    private String extractText(Bitmap bitmap) {
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            System.out.println((e.getMessage()));
            if (tessBaseApi == null) {
                System.out.println(("TessBaseAPI is null. TessFactory not returning tess object."));
            }
        }

        tessBaseApi.init(DATA_PATH, lang);

//       //EXTRA SETTINGS
//        //For example if we only want to detect numbers
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//
//        //blackList Example
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
//                "YTRWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");

        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            System.out.println("Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }
}
