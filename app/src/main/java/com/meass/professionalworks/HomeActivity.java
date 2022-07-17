package com.meass.professionalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FirebaseAuth firebaseAuth;
    private BottomNavigationView mainBottomNav;
    HomeFragment homeFragment;
    ProfileFragement profileFragement;
    BlankFragment blankFragment;
    //
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawar);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        firebaseAuth=FirebaseAuth.getInstance();
        mainBottomNav=findViewById(R.id.bottomnavigationview);
        homeFragment=new HomeFragment();
        profileFragement=new ProfileFragement();
        blankFragment=new BlankFragment();
       // mainBottomNav.setOnNavigationItemReselectedListener();
        initilizeFragment();
      mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              int id=item.getItemId();
              if (id==R.id.home) {
                  replaceFragment(homeFragment);

              }
              else if (id==R.id.profile) {
                  replaceFragment(profileFragement);
              }
              if (id==R.id.bank) {
                  replaceFragment(blankFragment);
              }
              return true;
          }
      });
//tablayout
        tabLayout=findViewById(R.id.tablelayour);
        viewPager=findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

///
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initilizeFragment() {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
fragmentTransaction.add(R.id.content,homeFragment);
fragmentTransaction.add(R.id.content,profileFragement);
        fragmentTransaction.add(R.id.content,blankFragment);
fragmentTransaction.hide(profileFragement);
fragmentTransaction.hide(blankFragment);
fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (fragment==homeFragment)
        {
fragmentTransaction.hide(profileFragement);
fragmentTransaction.hide(blankFragment);
        }
        else if(fragment==profileFragement) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(blankFragment);
        }
        else if(fragment==blankFragment) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragement);
        }
fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ggg,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.home1) {
            HomeFragment homeFragment=new HomeFragment();
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.content, homeFragment, "");

            ft2.commit();
        }
        else if (id==R.id.home2){
        }
        else if (id==R.id.home3){
        }
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.home:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                HomeFragment homeFragment=new HomeFragment();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.content, homeFragment, "");

                ft2.commit();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case R.id.profile:
                ProfileFragement profileFragement=new ProfileFragement();
                FragmentTransaction pt=getSupportFragmentManager().beginTransaction();
                pt.replace(R.id.content,profileFragement,"");
                pt.commit();

                break;
            case R.id.logout:
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you want logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
firebaseAuth.signOut();
startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).create().show();

                break;

        }
      //  drawerLayout.setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Confirmation")
                .setMessage("Are you want exit from our application?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finishAffinity();
            }
        }).create().show();
    }
}