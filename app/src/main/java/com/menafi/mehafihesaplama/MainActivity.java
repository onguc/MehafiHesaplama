package com.menafi.mehafihesaplama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAddPerson;

    Button btnLast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddPerson=(Button)findViewById(R.id.btnAddPerson);

        btnLast=(Button) findViewById(R.id.btnLast);

        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent("android.intent.action.addPerson");
                startActivity(i);
            }
        });

        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent("android.intent.action.lastMenafi");
                startActivity(i);
            }
        });
    }

}
