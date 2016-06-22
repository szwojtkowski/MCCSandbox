package mcc.agh.edu.pl.sandbox;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.Weka;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.XorResult;

public class XorService extends Service {

    private Weka weka;
    private IBinder binder = new ServiceBinder();
    private boolean ready;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            ready = false;
            Toast.makeText(this, String.format("Training started"), Toast.LENGTH_SHORT).show();
            this.weka = new Weka();
            this.weka.init();
            Toast.makeText(this, String.format("Training finished"), Toast.LENGTH_SHORT).show();
            ready = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public XorResult getXor(double a, double b) throws Exception {
        return weka.getXorResult(a, b);
    }

    public boolean isReady(){
        return ready;
    }

    class ServiceBinder extends Binder {

        public XorService getService(){
            return XorService.this;
        }

    }

}
