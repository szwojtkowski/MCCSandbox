package mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.powertutor;

import android.content.Context;

import mcc.agh.edu.pl.mobilecloudcomputing.PowerTutorFacade;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.BatteryMonitor;

public class PowerTutorBatteryMonitor implements BatteryMonitor {

    private Context context;
    private PowerTutorFacade powerTutorFacade;

    public PowerTutorBatteryMonitor(Context context){
        this.context = context;
        this.powerTutorFacade = PowerTutorFacade.getInstance(context, "battery");
        this.powerTutorFacade.startPowerTutor();
    }

    @Override
    public long getBatteryLevel() {
        return powerTutorFacade.getTotalPowerForUid();
    }

    public void start(){
        powerTutorFacade.bindService();
    }

    public void stop(){
        powerTutorFacade.unbindService();
    }

}
