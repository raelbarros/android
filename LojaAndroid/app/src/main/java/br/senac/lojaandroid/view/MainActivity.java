package br.senac.lojaandroid.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.Util;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton carrinho;
    private TextView hBtnEntrar, hTxtNome, hTxtEmail;
    private ImageView hImageUser;

    Home home;

    @Override
    protected void onResume() {
        super.onResume();

        setTitle(R.string.app_name);

        //Verifica se o Usuario esta logado para mostrar informacoes
        if (Util.checkLogin(MainActivity.this)) {
            setItensCabecalho("entrou");

            try {
                LojaDatabase appDB = LojaDatabase.getInstance(MainActivity.this);
                int id = Util.getCurrentUser(MainActivity.this);

                Cliente clienteDB = appDB.clienteDao().clienteById(id);

                //Salva imagem no imageView
                if (clienteDB.getImage() == null) {
                    hImageUser.setImageResource(R.drawable.user_512);
                } else {
                    hImageUser.setImageURI(Uri.fromFile(new File("/storage/emulated/0/" + clienteDB.getImage())));
                }

                hTxtNome.setText(clienteDB.getNomeCompletoCliente());
                hTxtEmail.setText(clienteDB.getEmailCliente());
            } catch (Throwable t) {
                hImageUser.setVisibility(View.VISIBLE);
                hImageUser.setImageResource(R.drawable.user_512);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navegation_view);
        carrinho = findViewById(R.id.carrinho);

        // ItensCategoria Menu Cabecalho
        hBtnEntrar = navigationView.getHeaderView(0).findViewById(R.id.txtEntrar);
        hTxtNome = navigationView.getHeaderView(0).findViewById(R.id.txtNome);
        hTxtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtCPF);
        hImageUser = navigationView.getHeaderView(0).findViewById(R.id.imageUser);


        home = new Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, home).commit();


        carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });

        hBtnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // marca tela atual no menu
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                // fecha menu ao clicar
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.action_compras) {
                    Compras compras = new Compras();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, compras).commit();
                    return true;
                } else if (menuItem.getItemId() == R.id.action_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, home).commit();
                    return true;
                } else if (menuItem.getItemId() == R.id.action_logout) {
                    SharedPreferences.Editor editor = Util.getPreference(getApplication()).edit();

                    // Registra que usuario saiu na preferencia compartilhada
                    editor.putBoolean("isLogado", false);
                    editor.putInt("currentUser", 0);
                    editor.apply();

                    setItensCabecalho("saiu");

                    Util.showToast(getApplication(), "saiu");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.action_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_compras) {
            Intent intent = new Intent(MainActivity.this, Compras.class);
            startActivity(intent);
            return true;

        }
        return false;
    }

    public void setItensCabecalho(String status) {
        if (status.equals("saiu")) {
            hBtnEntrar.setVisibility(View.VISIBLE);

            hImageUser.setVisibility(View.GONE);
            hTxtNome.setVisibility(View.GONE);
            hTxtEmail.setVisibility(View.GONE);
        } else if (status.equals("entrou")) {
            hBtnEntrar.setVisibility(View.GONE);

            hImageUser.setVisibility(View.VISIBLE);
            hTxtNome.setVisibility(View.VISIBLE);
            hTxtEmail.setVisibility(View.VISIBLE);
        }

    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}