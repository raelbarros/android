package br.senac.navigationviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabslayout);

        TabPageAdapter adapter = new TabPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
