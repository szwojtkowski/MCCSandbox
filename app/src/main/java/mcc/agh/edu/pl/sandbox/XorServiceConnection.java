package mcc.agh.edu.pl.sandbox;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class XorServiceConnection implements ServiceConnection {

    private XorService service;

    public XorService getService(){
        return service;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        XorService.ServiceBinder binder = (XorService.ServiceBinder) service;
        this.service = binder.getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

        this.service = null;

    }
}
