package com.example.android.gridview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.gridview.R;

/**
 * Created by Tanaskovic on 1/13/2018.
 */

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private final String[] gridColor;

    public CustomGrid(Context c, String[] web, int[] imageid, String[] gridColor) {
        mContext = c;
        this.web = web;
        Imageid = imageid;
        this.gridColor = gridColor;
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
            grid.setBackgroundColor(Color.parseColor(gridColor[position]));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
