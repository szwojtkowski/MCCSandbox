package mcc.agh.edu.pl.sandbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.ArraySumRequest;
import com.mccfunction.BarcodeReaderRequest;
import com.mccfunction.ImageScalerRequest;
import com.mccfunction.OCRLang;
import com.mccfunction.PolymonialHaltRequest;
import com.mccfunction.QuickSortRequest;
import com.mccfunction.SimpleOCRRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.local.SmartOffloadingLocalService;
import mcc.agh.edu.pl.tasks.ArraySumTask;
import mcc.agh.edu.pl.tasks.BarcodeReaderTask;
import mcc.agh.edu.pl.tasks.ImageScalerTask;
import mcc.agh.edu.pl.tasks.PolymonialHaltTask;
import mcc.agh.edu.pl.tasks.QuickSortTask;
import mcc.agh.edu.pl.tasks.SimpleOCRTask;

public class MainActivity extends AppCompatActivity {

    private SmartOffloadingLocalService service;
    private boolean bound = false;
    private ServiceConnection connection = new SmartOffloadingServiceConnection();

    private static final int BARCODE_PROCESSING = 1;
    private static final int IMAGE_SCALING = 2;
    private static final int OCR_PROCESSING = 3;
    private ExecutionEnvironment executionEnvironment = ExecutionEnvironment.LOCAL;
    //  TESSERACT
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/MCCSandbox/";
    private static final String TESSDATA = "tessdata";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareTesseract();
        setContentView(R.layout.activity_main);

        Switch executionTypeSwitch = (Switch) findViewById(R.id.executionTypeSwitch);

        if (executionTypeSwitch != null) {
            executionTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        executionEnvironment = ExecutionEnvironment.CLOUD;
                    } else {
                        executionEnvironment = ExecutionEnvironment.LOCAL;
                    }
                }
            });
        }
    }

    public void calculateArraySumHandler(View view) {
        float [] testArray = {1.f, 2.f, 3.f, 4.f};
        ArraySumRequest input = new ArraySumRequest(testArray);
        ArraySumTask task = new ArraySumTask(this);
        service.execute(task, input);
    }

    public void calculateQuicksortHandler(View view) {
        double [] testArray = {1.2, 6.1, 0.1, 22.3, 1.32, 13.4, 44.2, 0.0001, 0.002};
        QuickSortRequest input = new QuickSortRequest(testArray);
        QuickSortTask task = new QuickSortTask(this);
        service.execute(task, input);
    }

    public void readBarcodeHandler(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), BARCODE_PROCESSING);
    }

    public void simpleOCRHandler(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), OCR_PROCESSING);
    }

    public void scaleImageHandler(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), IMAGE_SCALING);
    }

    public void sleepingProcessHandler(View view) {
        new PolymonialHaltTask(this).executeRemotely(new PolymonialHaltRequest(20, 20, 20, 20));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int[] pixels;
        int width;
        int height;

        if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                File file = new File(selectedImagePath);
                try {
                    InputStream inputStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap == null) {
                        System.out.println("uri is not a bitmap,");
                    }

                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    pixels = new int[width * height];

                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);

                    switch (requestCode) {
                        case BARCODE_PROCESSING:
                            BarcodeReaderRequest request = new BarcodeReaderRequest(pixels, width, height);
                            new BarcodeReaderTask(this).execute(request);
                            break;
                        case IMAGE_SCALING:
                            ImageScalerRequest imageScalerRequest = new ImageScalerRequest(blob.toByteArray(), 80, 80);
                            ImageScalerTask scalerTask = new ImageScalerTask(this, (ImageView)findViewById(R.id.imageView));
                            service.execute(scalerTask, imageScalerRequest);
                            break;
                        case OCR_PROCESSING:
                            ActivitySetTextHandler handler = new ActivitySetTextHandler((EditText)findViewById(R.id.editText2));
                            SimpleOCRRequest ocrRequest = new SimpleOCRRequest(blob.toByteArray(), OCRLang.POL);
                            SimpleOCRTask ocrTask = new SimpleOCRTask(this, handler);
                            service.execute(ocrTask, ocrRequest);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SmartOffloadingLocalService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

// TESERACT PREP


    private void prepareTesseract() {
        try {
            prepareDirectory(DATA_PATH + TESSDATA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyTessDataFiles(TESSDATA);
    }
    private void prepareDirectory(String path) {

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.out.println("ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            System.out.println("Created directory " + path);
        }
    }
    private void copyTessDataFiles(String path) {
        try {
            String fileList[] = getAssets().list(path);

            for (String fileName : fileList) {

                String pathToDataFile = DATA_PATH + path + "/" + fileName;
                if (!(new File(pathToDataFile)).exists()) {

                    InputStream in = getAssets().open(path + "/" + fileName);

                    OutputStream out = new FileOutputStream(pathToDataFile);

                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    System.out.println("Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            System.out.println(("Unable to copy files to tessdata " + e.toString()));
        }
    }



    class SmartOffloadingServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SmartOffloadingLocalService.LocalBinder binder = (SmartOffloadingLocalService.LocalBinder) service;
            MainActivity.this.service = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
}
