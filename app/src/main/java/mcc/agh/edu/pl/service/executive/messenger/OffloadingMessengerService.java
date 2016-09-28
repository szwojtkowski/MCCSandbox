package mcc.agh.edu.pl.service.executive.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import mcc.agh.edu.pl.tasks.Task;
import mcc.agh.edu.pl.tasks.TaskResult;

public class OffloadingMessengerService extends Service {

    public static final int TASK_MSG = 1;
    public static final int TASK_MSG_RESPONSE = 2;

    private Messenger mMessenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TASK_MSG: {
                    Task task = (Task) msg.getData().getSerializable("task");
                    TaskResult result = task.execute();

                    Message response = Message.obtain(null, TASK_MSG_RESPONSE);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("response", result);

                    response.setData(bundle);

                    try {
                        msg.replyTo.send(response);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
