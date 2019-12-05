package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.MaskUtil;
import br.senac.lojaandroid.util.Util;

public class FinalizarActivity extends AppCompatActivity {

    private ImageView imageUser;
    private TextView txtNome, txtCPF;
    private EditText txtEndereco, txtCEP, txtBairro, txtCartao, txtCod, txtVal;
    private Button btnCancel, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);

        imageUser = findViewById(R.id.imageUser);
        txtCPF = findViewById(R.id.txtCPF);
        txtNome = findViewById(R.id.txtNome);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtCEP = findViewById(R.id.txtCEP);
        txtBairro = findViewById(R.id.txtBairro);
        txtCartao = findViewById(R.id.txtCartao);
        txtCod = findViewById(R.id.txtCod);
        txtVal = findViewById(R.id.txtValidade);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        //Mascara dos inputs
        txtCEP.addTextChangedListener(MaskUtil.mask(txtCEP, MaskUtil.FORMAT_CEP));
        txtCartao.addTextChangedListener(MaskUtil.mask(txtCartao, MaskUtil.FORMAT_CARD));
        txtCod.addTextChangedListener(MaskUtil.mask(txtCod, MaskUtil.FORMAT_CODSEG));
        txtVal.addTextChangedListener(MaskUtil.mask(txtVal, MaskUtil.FORMAT_VAL));


        //Carrega image do Usuario
        loadImageUser();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalizarActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadImageUser(){
        //Verifica se o Usuario esta logado para mostrar informacoes
        if (Util.checkLogin(FinalizarActivity.this)) {

            try {
                LojaDatabase appDB = LojaDatabase.getInstance(FinalizarActivity.this);
                int id = Util.getCurrentUser(FinalizarActivity.this);

                Cliente clienteDB = appDB.clienteDao().clienteById(id);

                //Salva imagem no imageView
                if (clienteDB.getImage() == null) {
                    imageUser.setImageResource(R.drawable.user_512);
                } else {
                    imageUser.setImageURI(Uri.fromFile(new File("/storage/emulated/0/" + clienteDB.getImage())));
                }

                txtNome.setText(clienteDB.getNomeCompletoCliente());
                txtCPF.setText(clienteDB.getCPFCliente());
            } catch (Throwable t) {
                imageUser.setVisibility(View.VISIBLE);
                imageUser.setImageResource(R.drawable.user_512);
            }

        }
    }
}
