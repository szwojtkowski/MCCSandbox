package mcc.agh.edu.pl.sandbox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;

import java.util.Locale;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.XorResult;
import mcc.agh.edu.pl.tasks.ArraySumTask;

public class MainActivity extends AppCompatActivity {

    //private XorServiceConnection serviceConn;

    private EditText aText;
    private EditText bText;
    private TextView zeroResult;
    private TextView oneResult;
    private WekaService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //serviceConn = new XorServiceConnection();

        aText = (EditText) findViewById(R.id.aText);
        bText = (EditText) findViewById(R.id.bText);


        zeroResult = (TextView) findViewById(R.id.zeroResult);
        oneResult = (TextView) findViewById(R.id.oneResult);

        service = new WekaService();
        service.init();

    }

    public void onClick(View view) {
        double a = Double.parseDouble(aText.getText().toString());
        double b = Double.parseDouble(bText.getText().toString());
        if (service != null) {
            try {
                if (service.isReady()) {
                    XorResult result = service.getXor(a, b);
                    Toast.makeText(this, String.format("0: %f, 1: %f", result.zero(), result.one()), Toast.LENGTH_SHORT).show();
                    zeroResult.setText(String.format("0: %f", result.zero()));
                    oneResult.setText(String.format("1: %f", result.one()));
                } else {
                    Toast.makeText(this, String.format("Service is not initialized yet"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, String.format("Cannot calculate xor"), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void calculateArraySumHandler(View view) {
        float [] testArray = {1.f, 2.f, 3.f, 4.f};
        new ArraySumTask(this).execute(new ArraySumRequest(testArray));
    }

    public void onServiceTest(View view) {
        Intent intent = new Intent(MainActivity.this, ServiceTestActivity.class);
        startActivity(intent);
    }
}
