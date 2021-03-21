package com.example.userapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.info.setText(model.getInfo());
        if(model.getLevel()==1)
            holder.img.setImageResource(R.drawable.ic_warn);
        else if(model.getLevel()==2)
            holder.img.setImageResource(R.drawable.ic_warn2);
        else if(model.getLevel()==3)
            holder.img.setImageResource(R.drawable.ic_warn3);
    }

    @Override
    public int getItemCount() {
        return disasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView disasterLoc;
        public TextView info;
        public ImageView img;
        public ViewHolder(View itemview){
            super(itemview);

            disasterLoc = itemview.findViewById(R.id.disasterLoc);
            info = itemview.findViewById(R.id.info);
            img = itemview.findViewById(R.id.ic_flood);

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
