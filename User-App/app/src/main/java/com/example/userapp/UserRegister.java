package com.example.userapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserRegister extends AppCompatActivity{


    private Window window;

    private TextInputEditText eName,ePhone,location,code;
    private Button sendOtp, registerUser, mapDialog,back;
    private LinearLayout layout1, layout2;

    private TextInputLayout nameTIL, phoneTIL, locationTIL, codeTIL;

    String NAME,PHONE,LOCATION,OTP;

    HashMap<String, String> params = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        initViews();
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode(v);
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(v);
            }
        });

        mapDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);

            }
        });

        ePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneTIL.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        eName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameTIL.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationTIL.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                codeTIL.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void showMap() {
        startActivity(new Intent(UserRegister.this,MapMarker.class));
    }

    private void verifyCode(View v) {
        OTP = code.getText().toString();
        if(validateCode(v)){
            params.put("code",OTP);
            String apikey = "https://spate-assam.herokuapp.com/api/verifyOTP";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apikey, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String message = response.getString("error");
                                Toast.makeText(UserRegister.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            try{
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Toast.makeText(UserRegister.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                            }catch(JSONException| UnsupportedEncodingException je){
                                je.printStackTrace();
                            }
                            codeTIL.setError("Wrong OTP. Please check once again.");
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> headers = new HashMap<>();
                    headers.put("Content-type","application/json");
                    return params;
                }
            };
            int socketTime = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }

    private boolean validateCode(View v) {
        boolean isValid;
        if(code.getText().toString().isEmpty()){
            isValid = false;
            codeTIL.setError("Please enter OTP.");
            return isValid;
        }
        isValid = true;
        return isValid;
    }

    private void sendCode(View v) {
        NAME = eName.getText().toString();
        PHONE = ePhone.getText().toString();
        LOCATION = "26.245462,94.8793742";
        if(validateInfo(v)){
            params.put("name",NAME);
            params.put("phone",PHONE);
            params.put("default_loc",LOCATION);
            String apikey = "https://spate-assam.herokuapp.com/api/signup";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apikey, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserRegister.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-type","application/json");
                    return params;
                }
            };

            int socketTime = 3000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
            Toast.makeText(this, "OTP Request Sent...", Toast.LENGTH_SHORT).show();
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }
    }
    private boolean validateInfo(View v) {
        boolean isValid;
        if(ePhone.getText().toString().length()!=10){
            isValid = false;
            phoneTIL.setError("Enter a valid number");
            return isValid;
        }
        if(eName.getText().toString().isEmpty()){
            isValid = false;
            nameTIL.setError("Enter your name");
            return isValid;
        }
        if(location.getText().toString().isEmpty()){
            isValid =false;
            locationTIL.setError("Select your location");
            return isValid;
        }
        isValid = true;
        return isValid;
    }
    private void initViews(){
        eName = findViewById(R.id.name);
        ePhone = findViewById(R.id.phone);
        location = findViewById(R.id.location);
        code = findViewById(R.id.eCode);

        sendOtp = findViewById(R.id.sendOtp);
        registerUser = findViewById(R.id.registerUser);
        mapDialog = findViewById(R.id.mapDialog);
        back = findViewById(R.id.back);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        nameTIL = findViewById(R.id.nameTIL);
        phoneTIL = findViewById(R.id.phoneTIL);
        locationTIL = findViewById(R.id.locationTIL);
        codeTIL = findViewById(R.id.codeTIL);

    }


}