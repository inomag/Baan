package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import com.example.userapp.UtilsService.UtilsService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserRegister extends AppCompatActivity{


    private Window window;

    private TextInputEditText eName,ePhone,location,code,ephone2;
    private Button sendOtp, registerUser, mapDialog,back, to_login,to_signup,sendOtp2;
    private LinearLayout layout1,layout2, layout3;

    private TextInputLayout nameTIL, phoneTIL, locationTIL, codeTIL,phoneTIL2;

    private int flag =1;

    String NAME,PHONE,LOCATION,OTP;
    Double lat,lon;
    Geocoder geocoder;
    List<Address> addresses;
    UtilsService utilsService;

    HashMap<String, String> params = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        utilsService = new UtilsService();
        initViews();
        LOCATION = getIntent().getStringExtra("location");
        NAME = getIntent().getStringExtra("name");
        PHONE = getIntent().getStringExtra("phone");
        lat = getIntent().getDoubleExtra("lat",-1);
        lon = getIntent().getDoubleExtra("lon",-1);
        location.setText(LOCATION);
        eName.setText(NAME);
        ePhone.setText(PHONE);

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            if(lat>0&&lon>0){
                addresses = geocoder.getFromLocation(lat, lon, 1);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                location.setText(city+", "+state+", "+country);
            }else{
                location.setText("Lat: "+String.valueOf(lat)+", Lon: "+String.valueOf(lon));
            }
        } catch (IOException e) {
            location.setText("Lat: "+String.valueOf(lat)+", Lon: "+String.valueOf(lon));
        }
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilsService.hideKeyboard(v,UserRegister.this);
                sendCode(v);
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilsService.hideKeyboard(v,UserRegister.this);
                verifyCode(v);
                startActivity(new Intent(UserRegister.this,HomeActivity.class));
                finish();
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
                layout3.setVisibility(View.GONE);
                if(flag==1){
                    layout2.setVisibility(View.GONE);
                    layout1.setVisibility(View.VISIBLE);
                }else{
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            }
        });
        to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                flag = 1;
            }
        });
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                flag = 2;
            }
        });

        sendOtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
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
        ephone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneTIL2.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void loginUser(View v){
        PHONE = ephone2.getText().toString();
        if(validatePhone(v)){
            params.put("phone",PHONE);
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
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);


        }
    }

    private boolean validatePhone(View v){
        boolean isValid;
        if(ephone2.getText().toString().length()!=10){
            isValid = false;
            phoneTIL2.setError("Enter a valid number");
            return isValid;
        }
        isValid = true;
        return isValid;
    }
    private void showMap() {
        Intent intent = new Intent(UserRegister.this,MapMarker.class);
        intent.putExtra("name",eName.getText().toString());
        intent.putExtra("phone",ePhone.getText().toString());
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        startActivity(intent);
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
                            if(flag==1){
//                                Toast.makeText(UserRegister.this, "", Toast.LENGTH_SHORT).show();
                            }else if(flag==2){
                                Toast.makeText(UserRegister.this, "Welcome Back", Toast.LENGTH_SHORT).show();
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
        LOCATION = String.valueOf(lat)+","+String.valueOf(lon);
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
//                            Toast.makeText(UserRegister.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            layout3.setVisibility(View.VISIBLE);
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
        if(location.getText().toString().isEmpty()||location.getText().toString()=="Lat: -1, Lon: -1"){
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
        ephone2 = findViewById(R.id.ePhone2);

        sendOtp = findViewById(R.id.sendOtp);
        sendOtp2 = findViewById(R.id.sendOtp2);
        registerUser = findViewById(R.id.registerUser);
        mapDialog = findViewById(R.id.mapDialog);
        back = findViewById(R.id.back);
        to_login = findViewById(R.id.to_login);
        to_signup = findViewById(R.id.to_signup);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);

        nameTIL = findViewById(R.id.nameTIL);
        phoneTIL = findViewById(R.id.phoneTIL);
        locationTIL = findViewById(R.id.locationTIL);
        codeTIL = findViewById(R.id.codeTIL);
        phoneTIL2 = findViewById(R.id.phone2TIL);



    }


}