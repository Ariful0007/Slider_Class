package com.meass.professionalworks;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class HomeFragment extends Fragment {


View view;
CardView cardView1;
ImageSwitcher imageSwitcher;
Button  next;
    int imageSwitcherImages[] =
            {R.drawable.cpp, R.drawable.ic_360, R.drawable.ic_home, R.drawable.ll, R.drawable.ic_baseline_home_24};

    int switcherImageLength = imageSwitcherImages.length;
    int counter = -1;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        cardView1=view.findViewById(R.id.cardview1);
        imageSwitcher=view.findViewById(R.id.dddd);
        next=view.findViewById(R.id.next);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(view.getContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT
                ));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                switcherImageView.setImageResource(R.drawable.cpp);
                //switcherImageView.setMaxHeight(100);
                return switcherImageView;
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        int test=2;
        imageSwitcher.setImageResource(imageSwitcherImages[test]);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                ProfileFragement profileFragement=new ProfileFragement();
                bundle.putString("key","Android class");
                profileFragement.setArguments(bundle);
                FragmentTransaction pt=getActivity().getSupportFragmentManager().beginTransaction();
                pt.replace(R.id.content,profileFragement,"");
                pt.commit();
            }
        });
        return view;
    }
}