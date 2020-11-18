package com.kenon.wikihero.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenon.wikihero.R;
import com.kenon.wikihero.models.Superhero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private static ArrayList<Superhero> arrayList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int index);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public CardViewAdapter(ArrayList <Superhero> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView desc;

        ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            desc = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int index = getAdapterPosition();
                        if(index != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClicked(index);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder evh = new ViewHolder(v, onItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder holder, int position) {
        Superhero currentItem = arrayList.get(position);

        Picasso.get()
                .load(currentItem.getImageUrl())
                .error(R.drawable.dummy1)
                .placeholder(R.drawable.dummy1)
                .into(holder.imageView);

        holder.title.setText(currentItem.getName());
        holder.desc.setText(currentItem.getBiography().getPublisher());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
