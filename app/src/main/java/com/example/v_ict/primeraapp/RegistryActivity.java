package com.example.v_ict.primeraapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.v_ict.primeraapp.Volley.Constants;
import com.example.v_ict.primeraapp.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegistryActivity extends AppCompatActivity {

    private EditText user, email, userLogin, passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        init();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailText = email.getText().toString();
                final String userText = user.getText().toString();
                final String userLoginText = userLogin.getText().toString();
                final String contrasenia = passwordLogin.getText().toString();

                String service = "register.php";
                final String mRequestBody = registroRequestJSON(userText, emailText, userLoginText, contrasenia).toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.server.concat(service),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("Response", response.toString());

                                JSONArray jsonArray;
                                JSONObject jsonObject;

                                String code = "";
                                String message = "";

                                try {
                                    jsonArray = new JSONArray(response);
                                    jsonObject = new JSONObject(jsonArray.get(0).toString());

                                    code = jsonObject.getString("code");
                                    message = jsonObject.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (code.equals("reg_success")) {
                                    Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistryActivity.this, loginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Service Error: ", error.toString());
                    }
                })

                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> header = new HashMap<String, String>();
                        //header.put("Content-Type", "application/json");
                        return header;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                            return null;
                        }
                    }
                };

                //stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                VolleySingleton.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private String registroRequestJSON(String userText, String emailText, String userLoginText, String contrasenia) {
        JSONObject request = new JSONObject();
        try {
            request.put("name", userText);
            request.put("email", emailText);
            request.put("user_name", userLoginText);
            request.put("password", contrasenia);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request.toString();
    }


    private void init() {

        user = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        userLogin = (EditText) findViewById(R.id.userLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);

    }

}
