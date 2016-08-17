package mcc.agh.edu.pl.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

import mcc.agh.edu.pl.tasks.Task;
import mcc.agh.edu.pl.tasks.TaskResult;

public class OffloadingService extends Service {

    static final int TASK_MSG = 1;
    private final Messenger messenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public TaskResult execute(Task task){
        return task.execute();
    }

    class ServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TASK_MSG:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
