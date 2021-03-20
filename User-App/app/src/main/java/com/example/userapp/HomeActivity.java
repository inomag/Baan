package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userapp.adapter.CustomRecyclerAdapter;
import com.example.userapp.model.DisasterModel;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<DisasterModel> disasters;

    RequestQueue rq;

    String disaster_url = "https://spate-assam.herokuapp.com/api/get/locations/?flooded_locations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rq = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        disasters = new ArrayList<>();

        sendRequest();
    }

    private void sendRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, disaster_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            DisasterModel disasterModel = new DisasterModel();
                            JSONArray arr = response.getJSONArray("flooded_locations");
                            for(int i=0;i<arr.length();i++){
                                JSONObject obj = arr.getJSONObject(i);
                                disasterModel.setCoordinates(obj.getString("coordinates"));
                                disasters.add(disasterModel);
                            }
                            mAdapter = new CustomRecyclerAdapter(HomeActivity.this, disasters);
                            recyclerView.setAdapter(mAdapter);
                            Toast.makeText(HomeActivity.this, String.valueOf(arr.length()), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jsonObjectRequest);
    }
}