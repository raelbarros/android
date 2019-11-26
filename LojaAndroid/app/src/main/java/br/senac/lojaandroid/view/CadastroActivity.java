package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiCliente;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.MaskUtil;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    Button btnSalvar, btnCancelar;
    EditText txtNome, txtSobrenome,txtPhone, txtCPF, txtEmail, txtPasswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobreN);
        txtPhone = findViewById(R.id.txtPhone);
        txtCPF = findViewById(R.id.txtCPF);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancel);

        txtCPF.addTextChangedListener(MaskUtil.mask(txtCPF, MaskUtil.FORMAT_CPF));
        txtPhone.addTextChangedListener(MaskUtil.mask(txtPhone, MaskUtil.FORMAT_FONE));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cliente cliente = new Cliente();
                cliente.setNomeCompletoCliente(txtNome.getText().toString() + " " + txtSobrenome.getText().toString());
                cliente.setCelularCliente(txtPhone.getText().toString());
                cliente.setCPFCliente(txtCPF.getText().toString());
                cliente.setEmailCliente(txtEmail.getText().toString());
                cliente.setSenhaCliente(txtPasswd.getText().toString());

                System.out.println(cliente.getNomeCompletoCliente());

            }
        });


    }



}
