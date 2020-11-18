package com.kenon.wikihero.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenon.wikihero.R;
import com.kenon.wikihero.models.Superhero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends BaseAdapter {
    Context context;
    List <Superhero> superheroList = new ArrayList<>();

    public SearchResultsAdapter(Context context, List <Superhero> superheroList) {
        this.context = context;
        this.superheroList = superheroList;
    }

    @Override
    public int getCount() {
        return superheroList.size();
    }

    @Override
    public Object getItem(int position) {
        return superheroList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.list_item_1, null);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);
        ImageView imageView = convertView.findViewById(R.id.icon);

        title.setText(superheroList.get(position).getName());
        subtitle.setText(superheroList.get(position).getBiography().getPublisher());
        Picasso.get()
                .load(superheroList.get(position).getImageUrl())
                .error(R.drawable.dummy1)
                .placeholder(R.drawable.dummy1)
                .into(imageView);

        return convertView;
    }
}
