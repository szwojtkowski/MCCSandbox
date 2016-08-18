package mcc.agh.edu.pl.sandbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import mcc.agh.edu.pl.service.local.OffloadingLocalService;
import mcc.agh.edu.pl.service.messenger.OffloadingMessengerService;
import mcc.agh.edu.pl.tasks.CurrentTimeResult;
import mcc.agh.edu.pl.tasks.CurrentTimeTask;

public class ServiceTestActivity extends AppCompatActivity {

    private OffloadingLocalService localService;
    private boolean localBound = false;

    private Messenger messengerService;
    private boolean messengerBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OffloadingMessengerService.class);
        bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE);

        Intent localIntent = new Intent(this, OffloadingLocalService.class);
        bindService(localIntent, localConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (localBound) {
            unbindService(localConnection);
            localBound = false;
        }
        if (messengerBound) {
            unbindService(messengerConnection);
            messengerBound = false;
        }
    }

    public void onLocalNow(View view) {
        if (localBound) {
            CurrentTimeResult res = (CurrentTimeResult) localService.execute(new CurrentTimeTask());
            Toast.makeText(this, String.format("%s (from local service)", res.getResult() ), Toast.LENGTH_LONG).show();
        }
    }

    public void onMessengerNow(View view) {
        if (!messengerBound) return;
        Message msg = Message.obtain(null, OffloadingMessengerService.TASK_MSG);
        msg.replyTo = new Messenger(new ResponseHandler());

        Bundle bundle = new Bundle();
        bundle.putSerializable("task", new CurrentTimeTask());

        msg.setData(bundle);

        try {
            messengerService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
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


    private ServiceConnection messengerConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            messengerService = new Messenger(service);
            messengerBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            messengerBound = false;
        }
    };

    class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OffloadingMessengerService.TASK_MSG_RESPONSE: {
                    CurrentTimeResult res = (CurrentTimeResult) msg.getData().getSerializable("response");
                    Toast.makeText(getApplicationContext(), String.format("%s (from messenger service)", res.getResult()), Toast.LENGTH_LONG).show();
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
