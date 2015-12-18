package com.example.pruebasandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WidgetActivity extends Activity {

    @Bind(R.id.button)  Button button;
    @Bind(R.id.editText)  EditText textEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_activity);

        ButterKnife.bind(this);

//        button= (Button) findViewById(R.id.button);
//        textEdit= (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                textEdit.setText("Hola");
            }
        });
    }


}
