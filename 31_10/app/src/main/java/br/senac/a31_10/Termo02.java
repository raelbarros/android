package br.senac.a31_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Termo02 extends AppCompatActivity {

    TextView txtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terno02);

        txtTexto = findViewById(R.id.txtTexto);
        verificaSettings();

    }

    protected void onResume() {
        super.onResume();
        verificaSettings();

    }

    public void verificaSettings(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean config = pref.getBoolean("shTxt", false);

        if (!config) {
            txtTexto.setVisibility(View.INVISIBLE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
