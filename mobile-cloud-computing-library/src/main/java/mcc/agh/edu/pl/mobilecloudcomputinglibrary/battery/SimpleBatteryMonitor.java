package mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class SimpleBatteryMonitor implements BatteryMonitor {

    private Context context;

    public SimpleBatteryMonitor(Context context){
        this.context = context;
    }

    public long getBatteryLevel() {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return  (long) (((float)level / (float)scale) * 100.0f);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
