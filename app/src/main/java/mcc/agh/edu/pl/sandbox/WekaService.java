package mcc.agh.edu.pl.sandbox;

import mcc.agh.edu.pl.mobilecloudcomputinglibrary.Weka;
import mcc.agh.edu.pl.mobilecloudcomputinglibrary.XorResult;

public class WekaService {

    private Weka weka;
    private boolean ready;

    public WekaService(){
        this.weka = new Weka();
        this.ready = false;
    }

    public void init(){
        Thread t = new Thread(new Initialization(this));
        t.start();
    }

    public XorResult getXor(double a, double b) throws Exception {
        return weka.getXorResult(a, b);
    }

    public void setReady(boolean isReady){
        this.ready = isReady;
    }

    public Weka getWeka(){
        return weka;
    }

    public boolean isReady(){
        return ready;
    }

    class Initialization implements Runnable {

        private WekaService service;

        public Initialization(WekaService service){
            this.service = service;
        }

        @Override
        public void run() {
            try {
                service.getWeka().init();
                service.setReady(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
