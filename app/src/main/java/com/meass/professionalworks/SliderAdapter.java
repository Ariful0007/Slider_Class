package com.meass.professionalworks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    // creating a variable for
    // context and array list.
    private Context context;
    private List<Model_admin> mSliderItems = new ArrayList<>();

    // constructor for our adapter class.
    public SliderAdapter(Context context, List<Model_admin> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }
    public void renewItems(List<Model_admin> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Model_admin sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        // inside the on Create view holder method we are
        // inflating our layout file which we have created.
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.imaglayout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        // inside on bind view holder method we are
        // getting url of image from our modal class
        // and setting that url for image inside our
        // image view using Picasso.
        final Model_admin sliderItem = mSliderItems.get(position);
     //   final Model_admin sliderItem1 = new Model_admin();
        try {
            Picasso.get().load(sliderItem.getImage()).into(viewHolder.imageView);
        }catch (Exception e) {
            e.printStackTrace();
        }
viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        
    }
});


    }

    @Override
    public int getCount() {
        // returning the size of our array list.
        return mSliderItems.size();
    }

    // view holder class for initializing our view holder.
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        // variables for our view and image view.
        View itemView;
        ImageView imageView;
CardView carddd;
TextView viewForgotPAssword,viewForgotPAsswordf;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            // initializing our views.
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.idIVimage);


        }
    }
}
