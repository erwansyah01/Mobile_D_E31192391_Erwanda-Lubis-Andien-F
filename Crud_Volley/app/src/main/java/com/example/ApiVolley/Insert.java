package com.example.ApiVolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ApiVolley.Util.AppController;
import com.example.ApiVolley.Util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class Insert extends AppCompatActivity {
    EditText username,grup,nama,password;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intent_username = data.getStringExtra("username");
        String intent_grup = data.getStringExtra("grup");
        String intent_nama = data.getStringExtra("nama");
        String intent_password = data.getStringExtra("password");

        username = (EditText) findViewById(R.id.inp_username);
        grup = (EditText) findViewById(R.id.inp_grup);
        nama = (EditText) findViewById(R.id.inp_nama);
        username = (EditText) findViewById(R.id.inp_username);
        password = (EditText) findViewById(R.id.inp_password);
        btnbatal = (Button) findViewById(R.id.btn_cancel);
        btnsimpan = (Button) findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(Insert.this);

        if(update == 1)
        {
            btnsimpan.setText("Update Data");
            username.setText(intent_username);
            username.setVisibility(View.GONE);
            grup.setText(intent_grup);
            nama.setText(intent_nama);
            password.setText(intent_password);
        }
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update == 1)
                {
                    Update_data();
                }else{
                    simpanData();
                }
            }
        });
        btnbatal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent main = new Intent(Insert.this,MainActivity.class);
                startActivity(main);
            }
        });
    }
    private void Update_data()
    {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST,
                ServerAPI.URL_UPDATE,
                new Response.Listener<String>(){
                    public void onResponse(String response){
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Insert.this, "pesan: " + res.getString("Message"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(Insert.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(Insert.this, "Pesan: gagal insert data",
                                Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("grup", grup.getText().toString());
                map.put("nama", nama.getText().toString());
                map.put("password", password.getText().toString());
                return map;
            }

        };
        AppController.getInstance().addToRequestQueue(updateReq);

    }
    private void simpanData()
    {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST,ServerAPI.URL_INSERT,
                new Response.Listener<String>(){
                    public void onResponse(String response){
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(Insert.this, "pesan: " + res.getString("Message"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(Insert.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(Insert.this, "Pesan: gagal insert data",
                                Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString());
                map.put("grup", grup.getText().toString());
                map.put("nama", nama.getText().toString());
                map.put("password", password.getText().toString());
                return map;
            }

        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

}
