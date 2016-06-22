package mcc.agh.edu.pl.sandbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.XorResult;

public class MainActivity extends AppCompatActivity {

    private XorServiceConnection serviceConn;

    private EditText aText;
    private EditText bText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceConn = new XorServiceConnection();

        aText = (EditText) findViewById(R.id.aText);
        bText = (EditText) findViewById(R.id.bText);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, XorService.class);
        bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConn);
    }

    public void onClick(View view) {
        XorService service = serviceConn.getService();
        double a = Double.parseDouble(aText.getText().toString());
        double b = Double.parseDouble(bText.getText().toString());
        if (service != null) {
            try {
                XorResult result = service.getXor(a, b);
                Toast.makeText(this, String.format("zero: %f, one: %f", result.zero(), result.one()), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, String.format("Cannot calculate xor"), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


}
