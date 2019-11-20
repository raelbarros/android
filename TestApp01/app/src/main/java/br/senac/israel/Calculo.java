package br.senac.israel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Calculo extends AppCompatActivity {

    EditText txtVal1;
    EditText txtVal2;
    TextView txtResult;
    Button btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        txtVal1 = findViewById(R.id.txtVal1);
        txtVal2 = findViewById(R.id.txtVal2);
        txtResult = findViewById(R.id.txtResult);
        btnResult = findViewById(R.id.btnSomar);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float fTotal = calcula(Float.parseFloat(txtVal1.getText().toString()), Float.parseFloat(txtVal2.getText().toString()));
                String sTotal = Float.toString(fTotal);
                txtResult.setText(sTotal);
            }
        });
    }


    float calcula (float val1, float val2){
        return val1 + val2;
    }

}
