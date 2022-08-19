package com.meass.professionalworks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SliderClasss extends AppCompatActivity {
ImageSlider slider;
FirebaseFirestore  firebaseFirestore;
ArrayList<Model_admin>modelAdminArrayList;
SliderAdapter sliderAdapter;
SliderView slidetr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_classs);
        slider=findViewById(R.id.slider);
        ArrayList<SlideModel>list=new ArrayList<>();
        list.add(new SlideModel(R.drawable.ic_360));
        list.add(new SlideModel(R.drawable.ic_home));
        list.add(new SlideModel(R.drawable.error));
        list.add(new SlideModel(R.drawable.loading));
        list.add(new SlideModel(R.drawable.ll));
        list.add(new SlideModel("https://th.bing.com/th/id/R.3f453f0b0a7b4e4d1bcffbf76d3c0c07?rik=rcXX7QdXxsQEUw&pid=ImgRaw&r=0"));
slider.setImageList(list,true);




//database auto slider
        slidetr=findViewById(R.id.slidetr);
        modelAdminArrayList=new ArrayList<>();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Sliders_Image")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Model_admin model_admin=documentSnapshot.toObject(Model_admin.class);
                            Model_admin model=new Model_admin();
                            model.setImage(model_admin.getImage());
modelAdminArrayList.add(model);
sliderAdapter=new SliderAdapter(SliderClasss.this,modelAdminArrayList);
                            slidetr.setSliderAdapter(sliderAdapter);
// below line is for setting animation to our slider.
                            slidetr.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                            // below line is for setting auto cycle duration.
                            slidetr.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                            // below line is for setting
                            // scroll time animation
                            slidetr.setScrollTimeInSec(3);

                            // below line is for setting auto
                            // cycle animation to our slider
                            slidetr.setAutoCycle(true);

                            // below line is use to start
                            // the animation of our slider view.
                            slidetr.startAutoCycle();


                        }

                    }
                });



    }
}