package com.example.v_ict.primeraapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v_ict.primeraapp.SQLite.DatabaseOperation;

public class general extends AppCompatActivity {

    private TextView name;
    private TextView email;

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        init();

        DatabaseOperation dop = new DatabaseOperation(getApplicationContext());
        Cursor cursor = dop.consultarUsuarios(dop);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            name.setText(cursor.getString(cursor.getColumnIndex("name")));
            email.setText(cursor.getString(cursor.getColumnIndex("email")));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(general.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        logout = (Button) findViewById(R.id.logout);
    }
}
