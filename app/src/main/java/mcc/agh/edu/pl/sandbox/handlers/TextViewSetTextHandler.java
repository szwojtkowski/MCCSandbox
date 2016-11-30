package mcc.agh.edu.pl.sandbox.handlers;

import android.widget.TextView;

public class TextViewSetTextHandler implements TextHandler {

    private TextView textView;

    public TextViewSetTextHandler(TextView textView) {
        this.textView = textView;
    }
    public void setText(String text) {
        textView.setText(text);
    }
}
