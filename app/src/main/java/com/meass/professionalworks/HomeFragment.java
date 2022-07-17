package com.meass.professionalworks;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {


View view;
CardView cardView1;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        cardView1=view.findViewById(R.id.cardview1);
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