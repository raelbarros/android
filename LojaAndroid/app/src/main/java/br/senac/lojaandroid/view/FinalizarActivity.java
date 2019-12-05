package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.model.ItensPedido;
import br.senac.lojaandroid.model.Pedido;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.MaskUtil;
import br.senac.lojaandroid.util.Singleton;
import br.senac.lojaandroid.util.Util;

public class FinalizarActivity extends AppCompatActivity {

    private ImageView imageUser;
    private TextView txtNome, txtCPF, txtTotal;
    private EditText txtEndereco, txtCEP, txtBairro, txtCartao, txtCod, txtVal;
    private Button btnCancelar, btnConfirmar;

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
        txtTotal = findViewById(R.id.txtTotal);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        //Mascara dos inputs
        txtCEP.addTextChangedListener(MaskUtil.mask(txtCEP, MaskUtil.FORMAT_CEP));
        txtCartao.addTextChangedListener(MaskUtil.mask(txtCartao, MaskUtil.FORMAT_CARD));
        txtCod.addTextChangedListener(MaskUtil.mask(txtCod, MaskUtil.FORMAT_CODSEG));
        txtVal.addTextChangedListener(MaskUtil.mask(txtVal, MaskUtil.FORMAT_VAL));

        //Carrega image do Usuario
        loadImageUser();

        //Pega instancia no singleton
        Singleton singleton = Singleton.getInstance();
        final List<Produto> listaProduto = singleton.getCarrinho();

        // Atualiza valor do preco total
        double auxPreco = 0;
        for (Produto p : listaProduto) {
            auxPreco += p.getPrecProduto();
        }
        txtTotal.setText(Util.formatPreco(auxPreco));

        //Btn cancelar compra, retorna para o carrinho de compras
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalizarActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });

        //Btn Confirmar compra
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido p = new Pedido();

                int idCliente = Util.getCurrentUser(FinalizarActivity.this);

                p.setIdCliente(idCliente);
                p.setEndereco(txtEndereco.getText().toString());
                p.setCep(txtCEP.getText().toString());
                p.setBairro(txtBairro.getText().toString());
                p.setNunCartao(txtCartao.getText().toString());
                p.setCardCodigo(Integer.parseInt(txtCod.getText().toString()));
                p.setCardVal(txtVal.getText().toString());
                p.setDate(Util.getDateNow());

                //Pega id do item inserido
                LojaDatabase appDB = LojaDatabase.getInstance(FinalizarActivity.this);
                long id = appDB.pedidoDao().insertPedido(p);

                //salva itens da compra
                for (Produto prod : listaProduto) {
                    ItensPedido ip = new ItensPedido();
                    ip.setIdPedido(id);
                    ip.setIdProduto(prod.getIdProduto());

                    appDB.itensPedido().insertItem(ip);
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarActivity.this);
                builder.setTitle("Show!");
                builder.setMessage("Sua Compra foi realizada com sucesso");
                builder.setPositiveButton("Verificar Historico", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    public void loadImageUser() {
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

    public void attPrecoFinal() {

    }
}
