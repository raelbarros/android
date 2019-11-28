package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.MaskUtil;
import br.senac.lojaandroid.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private Button btnSalvar, btnCancelar;
    private EditText txtNome, txtSobrenome, txtPhone, txtCPF, txtEmail, txtPasswd;


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

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://oficinacordova.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiCliente apiCliente = retrofit.create(ApiCliente.class);
                Call<Cliente> clienteCall = apiCliente.setCliente(cliente);

                clienteCall.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        try {
                            Util.showToast(getApplication(), response.body() + "");
                            if (response.code() == 200) {
                                Cliente clienteResp = response.body();

                                // Salva o status de logado na preferencia compartilhada
                                SharedPreferences.Editor editor = Util.getPreference(CadastroActivity.this).edit();
                                editor.putBoolean("logado", true);
                                editor.apply();

                                // Salva o Cliente no SQLLite
                                LojaDatabase appDB = LojaDatabase.getInstance(CadastroActivity.this);
                                appDB.clienteDao().insertCliente(cliente);

                                // Retorna para a pagina Inicial
                                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (Exception t) {

                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {

                    }
                });
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
