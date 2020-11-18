package com.kenon.wikihero.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kenon.wikihero.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubFragmentListAdapter extends BaseAdapter {
    private Context context;
    private HashMap <String, String> data;
    private List <String> keys;

    public SubFragmentListAdapter(Context context, HashMap <String, String> data, List<String> keys) {
        this.context = context;
        this.data = data;
        this.keys = keys;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(keys.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.list_item_2, null);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);

        title.setText(keys.get(position));
        subtitle.setText(data.get(keys.get(position)));

        return convertView;
    }
}
