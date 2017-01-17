package pl.edu.agh.mcc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;

import edu.umich.PowerTutor.service.ICounterService;
import edu.umich.PowerTutor.service.UMLoggerService;
import edu.umich.PowerTutor.util.Counter;

public class PowerTutorFacade {

    private Intent serviceIntent;
    private Context context;
    private CounterServiceConnection conn;

    private static Map<String,PowerTutorFacade> instances = new HashMap<>();

    public static PowerTutorFacade getInstance(Context context,String id) {
        if (instances.get(id) != null) {
            return instances.get(id);
        } else {
            PowerTutorFacade facade = new PowerTutorFacade(context);
            instances.put(id, facade);
            return facade;
        }
    }

    private PowerTutorFacade(Context context) {
        this.context = context;
        conn = new CounterServiceConnection();
    }

    public void startPowerTutor() {
        serviceIntent = new Intent(context, UMLoggerService.class);
        context.startService(serviceIntent);
    }

    public long getTotalPowerForUid() {
        long result = 0;
        try {
            long [] totals = conn.counterService.getTotals(context.getApplicationInfo().uid, Counter.WINDOW_TOTAL);
            // get only CPU, WIFI, 3G
            result = totals[1] + totals[2] + totals[3];
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }


    private class CounterServiceConnection implements ServiceConnection {
        private ICounterService counterService;

        public void onServiceConnected(ComponentName className, IBinder boundService) {
            counterService = ICounterService.Stub.asInterface(boundService);
        }

        public void onServiceDisconnected(ComponentName className) {
            counterService = null;
        }
    }

    public void unbindService() {
        context.unbindService(conn);
    }

    public void bindService() {
        context.bindService(serviceIntent, conn, 0);
    }
}
