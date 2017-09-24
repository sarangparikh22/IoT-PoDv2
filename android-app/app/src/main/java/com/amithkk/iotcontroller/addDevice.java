package com.amithkk.iotcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class addDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);


        Button add = (Button) findViewById(R.id.addbtn);
        Button cancel = (Button) findViewById(R.id.cancelbtn);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText ipaddr = (EditText)  findViewById(R.id.ipaddr);
        final Spinner spin = (Spinner) findViewById(R.id.type);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.putExtra("name", name.getText().toString());
                i.putExtra("ipaddr", ipaddr.getText().toString());
                i.putExtra("type", spin.getSelectedItem().toString());

                setResult(RESULT_OK,i);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
