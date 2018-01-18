package com.example.v_ict.primeraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.v_ict.primeraapp.SQLite.DatabaseOperation;
import com.example.v_ict.primeraapp.Volley.Constants;
import com.example.v_ict.primeraapp.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    // TODO: Estudiar buenas practicas de programación
    // TODO: Repaso POO, Polimorfismo, Herencia

    private EditText user, password;
    private TextView registry;
    private Button login;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializamos variables
        init();

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegistryActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usuario = user.getText().toString();
                final String contrasenia = password.getText().toString();

                String service = "login.php";
                final String mRequestBody = loginRequestJSON(usuario, contrasenia).toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.server.concat(service),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("Response", response.toString());

                                JSONArray jsonArray;
                                JSONObject jsonObject;

                                String code = "";
                                String message = "";

                                String name = "";
                                String email = "";

                                try {
                                    jsonArray = new JSONArray(response);
                                    jsonObject = new JSONObject(jsonArray.get(0).toString());
                                    code = jsonObject.getString("code");
                                    if(jsonObject.has("name")){
                                        name = jsonObject.getString("name");
                                    }
                                    if(jsonObject.has("email")){
                                        email = jsonObject.getString("email");
                                    }
                                    if(jsonObject.has("message")){
                                        message = jsonObject.getString("message");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (code.equals("login_success")) {

                                    DatabaseOperation dop = new DatabaseOperation(getApplicationContext());
                                    dop.guardarUsuario(dop, name, "", email);
                                    //TODO: Agregar validacion donde veamos si exiten registros en la tabla Usuario, si existe, limpiamos la tabla. Si no, continuar
                                    //TODO: Evitar que te deje ingresar si los campos son nulos
                                    //TODO: Eliminar la base de datos al darle log out
                                    //TODO: Agregar "sesion" por tiempo, y que te mande a login despues de preguntar si quiere seguir conectado con AlertDialog
                                    Intent intent = new Intent(loginActivity.this, general.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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


            }
        });
    }

    public static JSONObject loginRequestJSON(String usuario, String contrasenia) {

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("user_name", usuario);
            jsonParams.put("password", contrasenia);
        } catch (Exception e) {
            Log.e("JSON Error: ", e.getMessage());
        }
        return jsonParams;
    }

    public void init() {
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        registry = (TextView) findViewById(R.id.reg);
        login = (Button) findViewById(R.id.login);
        logo = (ImageView) findViewById(R.id.logo);
    }
}
