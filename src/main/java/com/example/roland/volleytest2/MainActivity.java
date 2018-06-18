package com.example.roland.volleytest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    Button btnRequest;
    Button btnReceive;

    RequestQueue mRequestQueue;
    StringRequest mStringRequest;
    String url_POST = "http://146.169.147.119/chaos/append-message.php";
    String url_GET = "http://146.169.147.119/chaos/fetch-message.php?username=Adam";

    EditText edtTen, edtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTen = (EditText) findViewById(R.id.editText1);

        btnRequest = (Button) findViewById(R.id.buttonSend);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              InsertSV();

                                          }
                                      }

        );

        btnReceive = (Button) findViewById(R.id.buttonReceive);

        btnReceive.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              sendAndRequestResponse();

                                          }
                                      }

        );


    }

    private void sendAndRequestResponse() {

        final TextView displayMessage = (TextView)findViewById(R.id.textView1);

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                double[] Received = Translation.StringToDoubles(response.toString());

                Encryption dec = new Encryption();

                String finalMessage = dec.Decrypt(Received);

                displayMessage.setText(finalMessage);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void InsertSV(){
        final TextView displayMessage = (TextView)findViewById(R.id.textView1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                displayMessage.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{

                Map<String, String> params = new HashMap<String, String>();
                String MAIL = edtTen.getText().toString();
                Encryption enc = new Encryption();
                double[] Encrypted = enc.Encrypt(MAIL);
                String EncryptedString = Translation.DoublesToString(Encrypted);
                String From = "Roland";
                String To = "Adam";
                params.put("uorigin", From);
                params.put("udest", To);
                params.put("msg", EncryptedString);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}