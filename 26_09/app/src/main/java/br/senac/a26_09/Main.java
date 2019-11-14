package br.senac.a26_09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);

        // mostra o icone menu visivel
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // marca tela atual no menu
                if (menuItem.isChecked()){
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                // fecha menu ao clicar
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.action_user) {
                    Usuario user = new Usuario();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, user).commit();
                }
                if (menuItem.getItemId() == R.id.action_produtos) {
                    Produto produto = new Produto();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, produto).commit();
                }
                if (menuItem.getItemId() == R.id.action_client) {
                    Cliente client = new Cliente();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, client).commit();
                }

                return false;
            }
        });

        // cria navi menu
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawe);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    // acao de abrir o menu
    public boolean onOptionsItemSelected(MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.action_sobre) {
            Intent sobre = new Intent(Main.this, Sobre.class);
            startActivity(sobre);
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            finish();
        }

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}
