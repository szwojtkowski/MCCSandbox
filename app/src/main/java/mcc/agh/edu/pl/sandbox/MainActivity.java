package mcc.agh.edu.pl.sandbox;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.mccfunction.BarcodeReaderRequest;
import com.mccfunction.QuickSortRequest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.XorResult;
import mcc.agh.edu.pl.tasks.ArraySumTask;
import mcc.agh.edu.pl.tasks.BarcodeReaderTask;
import mcc.agh.edu.pl.tasks.QuickSortTask;

public class MainActivity extends AppCompatActivity {


    private EditText aText;
    private EditText bText;
    private TextView zeroResult;
    private TextView oneResult;
    private WekaService service;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aText = (EditText) findViewById(R.id.aText);
        bText = (EditText) findViewById(R.id.bText);

        zeroResult = (TextView) findViewById(R.id.zeroResult);
        oneResult = (TextView) findViewById(R.id.oneResult);

        service = new WekaService();
        service.init();

    }

    public void onClick(View view) {
        double a = Double.parseDouble(aText.getText().toString());
        double b = Double.parseDouble(bText.getText().toString());
        if (service != null) {
            try {
                if (service.isReady()) {
                    XorResult result = service.getXor(a, b);
                    Toast.makeText(this, String.format("0: %f, 1: %f", result.zero(), result.one()), Toast.LENGTH_SHORT).show();
                    zeroResult.setText(String.format("0: %f", result.zero()));
                    oneResult.setText(String.format("1: %f", result.one()));
                } else {
                    Toast.makeText(this, String.format("Service is not initialized yet"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, String.format("Cannot calculate xor"), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void calculateArraySumHandler(View view) {
        float [] testArray = {1.f, 2.f, 3.f, 4.f};
        new ArraySumTask(this).execute(new ArraySumRequest(testArray));
    }

    public void calculateQuicksortHandler(View view) {
        double [] testArray = {1.2, 6.1, 0.1, 22.3, 1.32, 13.4, 44.2, 0.0001, 0.002};
        new QuickSortTask(this).execute(new QuickSortRequest(testArray));

    }

    public void readBarcodeHandler(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                File file = new File(selectedImagePath);
                try {
                    InputStream inputStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap == null) {
                        System.out.println("uri is not a bitmap,");
                    }

                    int width = bitmap.getWidth(), height = bitmap.getHeight();
                    int[] pixels = new int[width * height];

                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    bitmap.recycle();

                    BarcodeReaderRequest request = new BarcodeReaderRequest(pixels, width, height);
                    new BarcodeReaderTask(this).execute(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // TODO: move to some helper class

    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void onServiceTest(View view) {
        Intent intent = new Intent(MainActivity.this, ServiceTestActivity.class);
        startActivity(intent);
    }
}
