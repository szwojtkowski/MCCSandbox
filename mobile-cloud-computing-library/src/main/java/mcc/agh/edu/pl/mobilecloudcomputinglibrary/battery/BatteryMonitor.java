package mcc.agh.edu.pl.mobilecloudcomputinglibrary.battery;

public interface BatteryMonitor {
    long getBatteryLevel();
    void start();
    void stop();
}
