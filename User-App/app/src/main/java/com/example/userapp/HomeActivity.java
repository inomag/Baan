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
import com.android.volley.toolbox.Volley;
import com.example.userapp.adapter.CustomRecyclerAdapter;
import com.example.userapp.model.DisasterModel;

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

    String disaster_url = "https://spate-assam.herokuapp.com/api/get/locations";

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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, disaster_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            DisasterModel disasterModel = new DisasterModel();

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                disasterModel.setCoordinates(jsonObject.getString("coordinates"));
                                Toast.makeText(HomeActivity.this, jsonObject.getString("coordinates"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException E) {
                                E.printStackTrace();
                            }

                            disasters.add(disasterModel);
                        }

                        mAdapter = new CustomRecyclerAdapter(HomeActivity.this, disasters);
                        recyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Volley Error: ",error.getMessage());
            }
        });

        rq.add(jsonArrayRequest);
    }
}