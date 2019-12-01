package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.Singleton;
import br.senac.lojaandroid.util.Util;

public class CarrinhoActivity extends AppCompatActivity {

    private TextView txtLimpar, txtTotal, txtVazio, txtTitle;
    private Button btnComprar;
    private ViewGroup mainLayout;
    private ProgressBar loader;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        txtTitle = findViewById(R.id.titleTotal);
        txtTotal = findViewById(R.id.txtTotal);
        txtLimpar = findViewById(R.id.txtLimpar);
        txtVazio = findViewById(R.id.txtVazio);
        mainLayout = findViewById(R.id.itensLayout);
        btnComprar = findViewById(R.id.btnComprar);
        loader = findViewById(R.id.loader);

        loader.setVisibility(View.VISIBLE);

        final Singleton singleton = Singleton.getInstance();
        listaProdutos = singleton.getCarrinho();

        if (listaProdutos == null || listaProdutos.isEmpty()) {
            txtVazio.setVisibility(View.VISIBLE);
            loader.setVisibility(View.GONE);
            txtTitle.setVisibility(View.GONE);
            txtTotal.setVisibility(View.GONE);
            txtLimpar.setVisibility(View.GONE);
            btnComprar.setVisibility(View.GONE);
        } else {
            try {
                attLayout(listaProdutos);
            } finally {
                loader.setVisibility(View.GONE);
            }
        }

        txtLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleton.removeAll();
                attLayout(singleton.getCarrinho());
            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarrinhoActivity.this, FinalizarActivity.class);
                startActivity(intent);
            }
        });

    }

    private void attLayout(List<Produto> list) {
        double auxPreco = 0;
        double auxTotal = 0;

        loader.setVisibility(View.VISIBLE);
        mainLayout.removeAllViews();

        if (list.isEmpty() || list == null) {
            btnComprar.setEnabled(false);
            loader.setVisibility(View.GONE);
            btnComprar.setBackgroundColor(getResources().getColor(R.color.material_on_primary_disabled));
            txtTotal.setText(Util.formatPreco(0.00));
        } else {
            for (Produto p : list) {
                auxPreco = p.getPrecProduto();

                addLines(p.getNomeProduto(), auxPreco, p.getIdProduto());
                auxTotal += auxPreco;
            }
            txtTotal.setText(Util.formatPreco(auxTotal));
        }
    }

    private void addLines(String name, double preco, final int id) {
        final LinearLayout linerLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.itens_carrinho_layout, mainLayout, false);

        TextView nomeProduto = linerLayout.findViewById(R.id.txtProduto);
        TextView precoProduto = linerLayout.findViewById(R.id.txtPreco);
        ImageView image = linerLayout.findViewById(R.id.imgProduto);
        Button btnDelete = linerLayout.findViewById(R.id.btnDelete);


        String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + id;
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.animation)
                .showImageForEmptyUri(R.drawable.error24px)
                .showImageOnFail(R.drawable.error24px)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton singleton = Singleton.getInstance();
                List<Produto> listAux = singleton.getCarrinho();
                for (Produto p : listAux) {
                    if (id == p.getIdProduto()) {
                        listAux.remove(p);
                    }
                    break;
                }
                singleton.setCarrinho(listAux);
                attLayout(listAux);
            }
        });

        imageLoader.displayImage(url, image, options);
        nomeProduto.setText(name);
        precoProduto.setText(Util.formatPreco(preco));

        mainLayout.addView(linerLayout);
    }


}
