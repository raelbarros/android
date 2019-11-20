package br.senac.aula29_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;

public class exe04Detalhe extends AppCompatActivity {

    Button btnSoma, btnSub, btnMult, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe04_detalhe);

        btnSoma = findViewById(R.id.btnSoma);
        btnSub = findViewById(R.id.btnSub);
        btnMult = findViewById(R.id.btnMult);
        btnDiv = findViewById(R.id.btnDiv);

        Intent intent = getIntent();

        String val1 = intent.getStringExtra("val1");
        final String val2 = intent.getStringExtra("val2");

        final float fVal1 = Float.parseFloat(val1);
        final float fVal2 = Float.parseFloat(val2);

        btnSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valT = fVal1 + fVal2;

                String Sval = String.valueOf(valT);

                Intent intent = new Intent();
                intent.putExtra("retorno", Sval);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valT = fVal1 - fVal2;

                String Sval = String.valueOf(valT);

                Intent intent = new Intent();
                intent.putExtra("retorno", Sval);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valT = fVal1 * fVal2;

                String Sval = String.valueOf(valT);

                Intent intent = new Intent();
                intent.putExtra("retorno", Sval);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fVal2 > 0) {
                    float valT = fVal1 / fVal2;

                    String Sval = String.valueOf(valT);

                    Intent intent = new Intent();
                    intent.putExtra("retorno", Sval);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED, null);
                    finish();
                }
            }
        });
    }
}
