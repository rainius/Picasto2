package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }
}