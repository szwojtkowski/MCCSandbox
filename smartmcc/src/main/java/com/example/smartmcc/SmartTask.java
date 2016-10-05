package com.example.smartmcc;

import android.os.AsyncTask;

import proxy.SmartProxy;
import task.ISharedResource;

public abstract class SmartTask <Q, R> extends AsyncTask<Q, Void, R> {

    private SmartProxy <Q, R> smartProxy;

    public SmartTask(SmartProxy <Q, R> proxy) {
        this.smartProxy = proxy;
    }

    public R begin(Q arg) {
        return this.doInBackground(arg);
    }

    public abstract void end(R result);

    @Override
    protected final R doInBackground(Q ... arg) {
        return this.smartProxy.processRemotely(arg[0]);
    }

    @Override
    protected void onPostExecute(R result) {
        this.end(result);
    }
}
