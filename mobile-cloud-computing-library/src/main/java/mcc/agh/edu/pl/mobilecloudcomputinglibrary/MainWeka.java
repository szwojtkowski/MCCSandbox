package mcc.agh.edu.pl.mobilecloudcomputinglibrary;

public class MainWeka {

    public static void main(String[] args) throws Exception {
        Weka weka = new Weka();
        weka.init();
        weka.getXorResult(0.2, 0.3);
    }

}
