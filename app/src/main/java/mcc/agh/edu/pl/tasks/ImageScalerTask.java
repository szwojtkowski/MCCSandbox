package mcc.agh.edu.pl.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.regions.Regions;
import com.mccfunction.ImageScaler;
import com.mccfunction.ImageScalerInput;
import com.mccfunction.ImageScalerOutput;

import java.io.ByteArrayOutputStream;

import mcc.agh.edu.pl.lambdaproxy.IImageScalerLambdaProxy;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactory;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ProxyFactoryConfiguration;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;

public class ImageScalerTask extends SmartTask<ImageScalerInput, ImageScalerOutput> {

    private final Activity caller;
    private ImageView imageView;

    public ImageScalerTask(Activity caller, ImageView imageView) {
        ProxyFactoryConfiguration pfc = new ProxyFactoryConfiguration("eu-west-1:b9444146-c3d3-4a3f-93a7-d9f8fd72dfc5", Regions.EU_WEST_1);
        ProxyFactory factory = new ProxyFactory<ImageScalerInput, ImageScalerOutput>(caller.getApplicationContext(), pfc);
        this.setSmartProxy(factory.create(IImageScalerLambdaProxy.class, new ImageScaler(), "ImageScaler", ImageScalerOutput.class));
        this.caller = caller;
        this.imageView = imageView;
    }

    private static Bitmap byteArrayToBitmap(byte [] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);

    }

    @Override
    public void end(ImageScalerOutput result) {
        Bitmap bitmap = ImageScalerTask.byteArrayToBitmap(result.getPayload());
        imageView.setImageBitmap(bitmap);
        Toast.makeText(caller, String.format("Execution time: %d", executionModel.getMillisElapsed()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ImageScalerOutput processLocally(ImageScalerInput request) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(request.getPayload(), 0, request.getPayload().length);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, request.getWidth(), request.getHeight(), false);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);
        byte[] bitmapdata = blob.toByteArray();
        return new ImageScalerOutput(bitmapdata, request.getWidth(), request.getHeight());
    }

}
