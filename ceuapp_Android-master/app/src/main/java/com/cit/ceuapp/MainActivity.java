package com.cit.ceuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.news_feed);

        mAuth  = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            // Esta com uma conta
            AppCompatTextView tv_main_account = (AppCompatTextView) findViewById(R.id.tv_main_account);
            String s = "Entrou como " + currentUser.getEmail();
            tv_main_account.setText(s);
        }
        else {
            // Ainda nao esta com uma conta, entrando na tela de signin
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean(getString(R.string.bundle_code_show_signed_in_successful), false)) {
            Snackbar.make(findViewById(R.id.cl_main_body), R.string.signed_in, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void bt_main_profile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void bt_main_sign_out(View view){
        mAuth.signOut();
        getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
