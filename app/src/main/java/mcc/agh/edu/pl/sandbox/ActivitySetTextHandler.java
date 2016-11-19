package mcc.agh.edu.pl.sandbox;

import android.widget.EditText;
import android.widget.TextView;

public class ActivitySetTextHandler {
    private EditText editText;
    public ActivitySetTextHandler(EditText editText) {
        this.editText = editText;

    }
    public void setText(String text) {
        editText.setText(text, TextView.BufferType.EDITABLE);
    }
}
