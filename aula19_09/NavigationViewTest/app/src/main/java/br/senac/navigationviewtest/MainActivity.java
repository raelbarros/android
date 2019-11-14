package br.senac.navigationviewtest;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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
            public boolean onNavigationItemSelected(MenuItem menuItem) {
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

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    // Verifica qual menu foi selecionado
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.action_messages) {
            Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
            startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.action_home) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    //cria menu toolbar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}
