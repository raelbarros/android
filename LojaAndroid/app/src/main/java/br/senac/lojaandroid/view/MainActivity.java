package br.senac.lojaandroid.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import br.senac.lojaandroid.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton carrinho;

    Home home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navegation_view);
        carrinho = findViewById(R.id.carrinho);

        home = new Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, home).commit();

        carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()){
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.action_compras) {
                    Compras compras = new Compras();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, compras).commit();
                    return true;
                } else if (menuItem.getItemId() == R.id.action_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, home).commit();
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
        } else if (item.getItemId() == R.id.action_compras) {
            Intent intent = new Intent(MainActivity.this, Compras.class);
            startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.action_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
