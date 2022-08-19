package com.meass.professionalworks;

import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class HasAdapter extends SliderViewAdapter<HasAdapter.myview>{
    @Override
    public myview onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(myview viewHolder, int position) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    class myview extends HasAdapter.ViewHolder {
        public myview(View itemView) {
            super(itemView);
        }
    }
}
