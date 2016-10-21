package mcc.agh.edu.pl.service.smart.local;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery.SimpleBatteryMonitor;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.Decider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.decider.SmartDecider;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.ExecutionRegistry;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.execution.SmartTask;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.ExecutionEnvironment;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.model.PredictionInstance;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.FileKnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.service.smart.SmartOffloadingService;
import task.SmartRequest;

public class SmartOffloadingLocalService extends Service implements SmartOffloadingService{


    private final IBinder mBinder = new LocalBinder();
    private KnowledgeRepository repository;
    private Decider decider;
    private ExecutionRegistry executionRegistry;
    private SimpleBatteryMonitor batteryMonitor;

    @Override
    public void onCreate() {
        super.onCreate();
        this.repository = new FileKnowledgeRepository("/data/data/files/weka/repo.arff");
        this.decider = new SmartDecider(repository);
        this.executionRegistry = new ExecutionRegistry(repository);
        this.batteryMonitor = new SimpleBatteryMonitor(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void execute(SmartTask task, SmartRequest input) {
        PredictionInstance predictionInstance = composePredictionInstance(task);
        task.setExecutionRegistry(executionRegistry);
        task.setBatteryMonitor(batteryMonitor);
        ExecutionEnvironment env = decider.whereExecute(predictionInstance);
        switch (env) {
            case CLOUD: task.executeRemotely(input); break;
            case LOCAL: task.executeLocally(input); break;
        }
    }

    public class LocalBinder extends Binder {
        public SmartOffloadingLocalService getService() {
            return SmartOffloadingLocalService.this;
        }
    }

    private PredictionInstance composePredictionInstance(SmartTask task){
        PredictionInstance instance = new PredictionInstance(task.getClass().getSimpleName(), false);
        return instance;
    }
}
