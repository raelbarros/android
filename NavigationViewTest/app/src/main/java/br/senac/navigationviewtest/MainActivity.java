package br.senac.navigationviewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle btnSanduiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navegation_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.isChecked()){
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.action_settings) {
                    Fragmento01 fragmento01 = new Fragmento01();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragmento01).commit();
                    return true;
                }
                return false;
            }
        });

        btnSanduiche = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(btnSanduiche);
        btnSanduiche.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (btnSanduiche.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
}
