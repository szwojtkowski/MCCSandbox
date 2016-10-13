package mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution;

import android.os.AsyncTask;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import proxy.SmartProxy;
import task.SmartRequest;
import task.SmartResponse;


public abstract class SmartTask <Q extends SmartRequest, R extends SmartResponse> extends AsyncTask<Q, Void, R> {

    private SmartProxy <Q, R> smartProxy;
    private ExecutionEnvironment type = ExecutionEnvironment.LOCAL;
    protected ExecutionModel executionModel;
    private ExecutionRegistry executionRegistry;
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
        this.executionModel.setName(this.getClass().getSimpleName());
        this.executionModel.setExecutionEnvironment(this.type);
        if(executionRegistry != null)
            this.executionRegistry.registerExecution(executionModel);
        this.end(result);
    }

    public void setSmartProxy(SmartProxy<Q, R> smartProxy) {
        this.smartProxy = smartProxy;
    }

    public void setExecutionRegistry(ExecutionRegistry registry){
        this.executionRegistry = registry;
    }
}
