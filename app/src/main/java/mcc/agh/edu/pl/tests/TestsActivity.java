package mcc.agh.edu.pl.tests;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.FileKnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.repository.knowledge.KnowledgeRepository;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.service.local.SmartOffloadingLocalService;
import mcc.agh.edu.pl.sandbox.R;

public class TestsActivity extends Activity {

    private SmartOffloadingLocalService service;
    private boolean bound = false;
    private ServiceConnection connection = new SmartOffloadingServiceConnection();
    private TestLauncher testLauncher;
    private KnowledgeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        this.repository = new FileKnowledgeRepository("/data/data/files/weka/tests.arff");
    }


    public void onStartTest(View view) {
        setupTestLauncher();
        if(testLauncher != null)
            testLauncher.launch();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SmartOffloadingLocalService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    public void setupTestLauncher(){
        if(service != null)
            this.testLauncher = new TestLauncher(this, service, repository);
    }

    class SmartOffloadingServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SmartOffloadingLocalService.LocalBinder binder = (SmartOffloadingLocalService.LocalBinder) service;
            TestsActivity.this.service = binder.getService();
            bound = true;
            setupTestLauncher();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
}
