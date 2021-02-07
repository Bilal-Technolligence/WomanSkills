package com.example.womanskills;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    ArrayList<ProductAttr> productAttrs;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ProductListAdapter(ArrayList<ProductAttr> productAttrs, Context context) {
        this.context = context;
        this.productAttrs = productAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int A[] = new int[productAttrs.size()];
        A[position] = 0;
        String user = productAttrs.get(position).getUserId();
        if (!uid.equals(user)){
            holder.delete.setVisibility(View.GONE);
        }
        String id = productAttrs.get(position).getId();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("Products").child(id).setValue(null);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        Picasso.get().load(productAttrs.get(position).getImage1()).into(holder.img1);
//        Picasso.get().load(productAttrs.get(position).getImage2()).into(holder.img2);
//        Picasso.get().load(productAttrs.get(position).getImage3()).into(holder.img3);
        holder.back.setVisibility(View.GONE);
        holder.title.setText(productAttrs.get(position).getTitle());
        holder.price.setText(productAttrs.get(position).getPrice());
        String uid = productAttrs.get(position).getUserId();
        databaseReference.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    try {
                        String Name = snapshot.child("fullname").getValue().toString();
                        holder.name.setText(Name);
                        String img = snapshot.child("img").getValue().toString();
                        Picasso.get().load(img).into(holder.profile);
                    }
                    catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.frwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A[position]++;
                if(A[position]==0){
                    Picasso.get().load(productAttrs.get(position).getImage1()).into(holder.img1);
                    holder.back.setVisibility(View.GONE);
                    holder.frwd.setVisibility(View.VISIBLE);
                }
                if(A[position]==2){
                    Picasso.get().load(productAttrs.get(position).getImage2()).into(holder.img1);
                    holder.back.setVisibility(View.VISIBLE);
                    holder.frwd.setVisibility(View.GONE);
                }
                if (A[position]==1){
                    Picasso.get().load(productAttrs.get(position).getImage3()).into(holder.img1);
                    holder.back.setVisibility(View.VISIBLE);
                    holder.frwd.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A[position]--;
                if(A[position]==0){
                    Picasso.get().load(productAttrs.get(position).getImage1()).into(holder.img1);
                    holder.back.setVisibility(View.GONE);
                    holder.frwd.setVisibility(View.VISIBLE);
                }
                if(A[position]==2){
                    Picasso.get().load(productAttrs.get(position).getImage2()).into(holder.img1);
                    holder.back.setVisibility(View.VISIBLE);
                    holder.frwd.setVisibility(View.GONE);
                }
                if (A[position]==1){
                    Picasso.get().load(productAttrs.get(position).getImage3()).into(holder.img1);
                    holder.back.setVisibility(View.VISIBLE);
                    holder.frwd.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1 ,profile;
        TextView frwd, back, name ,title , price;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            profile = (ImageView) itemView.findViewById(R.id.imgProfile);
            name = (TextView) itemView.findViewById(R.id.txtName);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            frwd = (TextView) itemView.findViewById(R.id.btnFrwd);
            back = (TextView) itemView.findViewById(R.id.btnBack);
            delete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}
