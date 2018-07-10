package com.example.kankan.findpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyInner> {

    Context context;
    List<MyRoom> list;

    public MyAdapter(Context context, List<MyRoom> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyInner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.my_layout,parent,false);
        return new MyInner(view,list);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInner holder, int position) {

            String oldPincode = holder.txtPincode.getText().toString();
            String oldDistrict = holder.txtDistrict.getText().toString();
            String oldLocation = holder.txtLocation.getText().toString();
            holder.txtPincode.setText("\n" + oldPincode + list.get(position).getPicode() + "\n");
            holder.txtDistrict.setText(oldDistrict + list.get(position).getDistructName() + "\n");
            holder.txtLocation.setText("\n" + oldLocation + list.get(position).localityName + "\n");
            Glide.with(context).load(list.get(position).getUriHome()).into(holder.imgHome);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyInner extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtDistrict,txtPincode,txtLocation;
        ImageView imgHome;
        List<MyRoom> Innerlist;
        public MyInner(View itemView,List<MyRoom> Innerlist) {
            super(itemView);
            this.Innerlist=Innerlist;
            txtDistrict=(TextView)itemView.findViewById(R.id.txtGetDistrictName);
            txtPincode=(TextView)itemView.findViewById(R.id.txtGetPicode);
            imgHome=(ImageView)itemView.findViewById(R.id.imgGetData);
            txtLocation=(TextView)itemView.findViewById(R.id.txtLocation);
            itemView.setOnClickListener(MyInner.this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            String pincode=list.get(position).getUriHome();
            Intent intent=new Intent(v.getContext(),DetailsActivity.class);
            intent.putExtra("URIROOM",list.get(position).getUriRoom());
            intent.putExtra("URIHOME",list.get(position).getUriHome());
            intent.putExtra("TYPE_ROOM", list.get(position).getTypeOFRoom());
            intent.putExtra("NO_OF_BED",list.get(position).getNoOfBed());
            intent.putExtra("STATE_NAME",list.get(position).getStateName());
            intent.putExtra("DISTRICT_NAME",list.get(position).getDistructName());
            intent.putExtra("LOCALITY_NAME",list.get(position).getLocalityName());
            intent.putExtra("PINCODE",list.get(position).getPicode());
            v.getContext().startActivity(intent);

        }
    }
}
