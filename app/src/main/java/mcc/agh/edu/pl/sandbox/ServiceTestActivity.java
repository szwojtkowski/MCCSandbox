package mcc.agh.edu.pl.sandbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import mcc.agh.edu.pl.service.local.OffloadingLocalService;
import mcc.agh.edu.pl.tasks.CurrentTimeResult;
import mcc.agh.edu.pl.tasks.CurrentTimeTask;

public class ServiceTestActivity extends AppCompatActivity {

    private OffloadingLocalService localService;
    private boolean localBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OffloadingLocalService.class);
        bindService(intent, localConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (localBound) {
            unbindService(localConnection);
            localBound = false;
        }
    }

    public void onNow(View view) {
        if (localBound) {
            CurrentTimeResult res = (CurrentTimeResult) localService.execute(new CurrentTimeTask());
            Toast.makeText(this, String.format("Now is %s", res.getResult()), Toast.LENGTH_LONG).show();
        }
    }


    private ServiceConnection localConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            OffloadingLocalService.LocalBinder binder = (OffloadingLocalService.LocalBinder) service;
            localService = binder.getService();
            localBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            localBound = false;
        }
    };



}
