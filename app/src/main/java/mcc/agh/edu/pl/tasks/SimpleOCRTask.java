package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.mccfunction.ISimpleOCR;
import com.mccfunction.SimpleOCR;
import com.mccfunction.SimpleOCRRequest;
import com.mccfunction.SimpleOCRResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;

public class SimpleOCRTask extends SmartTask<SimpleOCRRequest, SimpleOCRResponse> {

    private final Activity caller;
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/MCCSandbox/";
    private static final String TESSDATA = "tessdata";
    Uri outputFileUri;
    private TessBaseAPI tessBaseApi;
    private static final String lang = "pol";


    public SimpleOCRTask(Activity caller) {
        this.caller = caller;
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<SimpleOCRRequest, SimpleOCRResponse>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(ISimpleOCR.class, new SimpleOCR(), "SimpleOCR", SimpleOCRResponse.class));
    }

    @Override
    public void end(SimpleOCRResponse result) {
        if (result != null)
            Toast.makeText(this.caller, String.format("Response result %s", result.getText()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void executeLocally(SimpleOCRRequest arg) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(arg.getPayload(), 0, arg.getPayload().length);
        String result = startOCR(bitmap);
        Toast.makeText(this.caller, String.format("Response result %s", result), Toast.LENGTH_SHORT).show();
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

        System.out.println(("Training file loaded"));
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
