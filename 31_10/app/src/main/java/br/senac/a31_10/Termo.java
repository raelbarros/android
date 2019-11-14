package br.senac.a31_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Termo extends AppCompatActivity {

    Button btnAccept, btnReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termo);

        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);

        final SharedPreferences prefs = getSharedPreferences("termo", MODE_PRIVATE);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("accept", true);
                editor.apply();
                Intent intent = new Intent(Termo.this, Termo02.class);
                startActivity(intent);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("accept", false);
                finish();
            }
        });

        Boolean termo = prefs.getBoolean("accept", false);

        if(termo) {
            Intent intent = new Intent(Termo.this, Termo02.class);
            startActivity(intent);
        }
    }
}
