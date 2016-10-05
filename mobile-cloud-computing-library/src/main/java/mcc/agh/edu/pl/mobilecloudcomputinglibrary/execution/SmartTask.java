package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import android.os.AsyncTask;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import proxy.SmartProxy;


public abstract class SmartTask <Q, R> extends AsyncTask<Q, Void, R> {

    private SmartProxy <Q, R> smartProxy;
    private ExecutionEnvironment type = ExecutionEnvironment.LOCAL;
    protected ExecutionModel executionModel;
    private long startTime = 0;

    public SmartTask() {
        this.executionModel = new ExecutionModel();
    }

    public R processLocally(Q arg) {
        return this.smartProxy.processLocally(arg);
    }

    public void executeRemotely(Q arg) {
        this.startTime = System.currentTimeMillis();
        this.type = ExecutionEnvironment.CLOUD;
        this.execute(arg);
    }

    public void executeLocally(Q arg) {
        this.startTime = System.currentTimeMillis();
        this.type = ExecutionEnvironment.LOCAL;
        this.execute(arg);
    }

    public abstract void end(R result);

    @Override
    protected final R doInBackground(Q ... arg) {
        switch (this.type) {
            case CLOUD:
                return this.smartProxy.processRemotely(arg[0]);
            case LOCAL:
                return this.processLocally(arg[0]);
        }
        return this.processLocally(arg[0]);
    }

    @Override
    protected void onPostExecute(R result) {
        this.executionModel.setMilisElapsed(System.currentTimeMillis() - this.startTime);
        this.end(result);
    }

    public void setSmartProxy(SmartProxy<Q, R> smartProxy) {
        this.smartProxy = smartProxy;
    }
}
