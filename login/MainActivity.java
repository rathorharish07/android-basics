package com.harishrathor.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button b1;
private AdView adone;
EditText email,pass;
ProgressBar p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        b1=(Button)findViewById(R.id.login);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        p1 =(ProgressBar)findViewById(R.id.pb);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1.setVisibility(View.VISIBLE);
                b1.setVisibility(View.INVISIBLE);
                final String em = email.getText().toString();
                final String ps = pass.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, Function.FIRST_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        p1.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.names().get(0).equals("success")) {
                                Intent i = new Intent(getApplicationContext(), profile.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), object.getString("success"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), object.getString("error"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                ){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError{

                        HashMap<String,String> putParams= new HashMap<String, String>();
                        putParams.put("email",em);
                        putParams.put("password",ps);
                        return putParams;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });

    }
}
