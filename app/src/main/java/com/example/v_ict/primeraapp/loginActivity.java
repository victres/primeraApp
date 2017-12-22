package com.example.v_ict.primeraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {
    private EditText usuario,contra;
    private TextView reg;
    private Button login;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Declaramos todas las variables
        usuario = (EditText) findViewById(R.id.user);
        contra=(EditText) findViewById(R.id.pass);
        reg=(TextView) findViewById(R.id.reg);
        login=(Button) findViewById(R.id.login);
        img=(ImageView) findViewById(R.id.logo);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(loginActivity.this, general.class);
                startActivity(intent);
            }
        });
    }
}
