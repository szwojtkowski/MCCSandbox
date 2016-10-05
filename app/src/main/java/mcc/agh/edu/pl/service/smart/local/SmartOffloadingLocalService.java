package mcc.agh.edu.pl.service.smart.local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.service.smart.SmartOffloadingService;
import task.SmartRequest;

public class SmartOffloadingLocalService extends Service implements SmartOffloadingService{

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void execute(SmartTask task, SmartRequest input) {
        task.executeLocally(input);
    }

    public class LocalBinder extends Binder {
        public SmartOffloadingLocalService getService() {
            return SmartOffloadingLocalService.this;
        }
    }
}
