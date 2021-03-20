package com.example.userapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userapp.R;
import com.example.userapp.model.DisasterModel;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<DisasterModel> disasters;

    public CustomRecyclerAdapter(Context context, List disasters){
        this.context = context;
        this.disasters = disasters;
    }

    @NonNull
    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(disasters.get(position));

        DisasterModel model = disasters.get(position);

        holder.disasterLoc.setText(model.getCoordinates());
    }

    @Override
    public int getItemCount() {
        return disasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView disasterLoc;

        public ViewHolder(View itemview){
            super(itemview);

            disasterLoc = itemview.findViewById(R.id.disasterLoc);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisasterModel db = (DisasterModel) v.getTag();
                    Toast.makeText(context, db.getCoordinates(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
