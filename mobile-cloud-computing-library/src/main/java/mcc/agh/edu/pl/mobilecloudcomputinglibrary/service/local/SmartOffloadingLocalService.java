package mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.local;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.BatteryMonitor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.powertutor.PowerTutorBatteryMonitor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.SmartDecider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ExecutionRegistry;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.FileKnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.SmartOffloadingService;
import task.SmartInput;

public class SmartOffloadingLocalService extends Service implements SmartOffloadingService{


    private final IBinder mBinder = new LocalBinder();
    private KnowledgeRepository repository;
    private Decider decider;
    private ExecutionRegistry executionRegistry;
    private BatteryMonitor batteryMonitor;
    private WifiManager wifiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.repository = new FileKnowledgeRepository("/data/data/files/weka/repo.arff");
        this.decider = new SmartDecider(repository);
        this.executionRegistry = new ExecutionRegistry(repository);
        this.batteryMonitor = new PowerTutorBatteryMonitor(this);
        this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        this.batteryMonitor.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        batteryMonitor.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void execute(SmartTask task, SmartInput input) {
        PredictionInstance predictionInstance = composePredictionInstance(task, input);
        task.setExecutionRegistry(executionRegistry);
        task.setBatteryMonitor(batteryMonitor);
        task.setWifiManager(wifiManager);
        repository.registerTask(task.getName());
        if(isNetworkAvailable()) {
            ExecutionEnvironment env = decider.getExecutionEnvironment(predictionInstance);
            switch (env) {
                case CLOUD: task.executeRemotely(input); break;
                case LOCAL: task.executeLocally(input);break;
            }
        } else {
            task.executeLocally(input);
        }
    }

    @Override
    public void setKnowledgeRepository(KnowledgeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setOffloadingDecider(Decider decider) {
        this.decider = decider;
    }

    public class LocalBinder extends Binder {
        public SmartOffloadingLocalService getService() {
            return SmartOffloadingLocalService.this;
        }
    }

    private PredictionInstance composePredictionInstance(SmartTask task, SmartInput input){
        PredictionInstance instance = new PredictionInstance(
                task.getClass().getSimpleName(), false, task.getParamsFromInput(input));
        return instance;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
