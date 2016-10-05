package mcc.agh.edu.pl.service.executive.local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import mcc.agh.edu.pl.service.executive.OffloadingService;
import mcc.agh.edu.pl.tasks.Task;
import mcc.agh.edu.pl.tasks.TaskResult;

public class OffloadingLocalService extends Service implements OffloadingService {

    private final IBinder mBinder = new LocalBinder();

    public TaskResult execute(Task task){
        return task.execute();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {

        public OffloadingLocalService getService() {
            return OffloadingLocalService.this;
        }

    }
}
