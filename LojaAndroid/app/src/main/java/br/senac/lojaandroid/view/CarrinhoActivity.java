package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.Singleton;
import br.senac.lojaandroid.util.Util;

public class CarrinhoActivity extends AppCompatActivity {

    private TextView txtLimpar, txtTotal;
    private Button btnFinalizar;
    private ViewGroup mainLayout;
    private ProgressBar loader;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_carrinho);

        txtTotal = findViewById(R.id.txtTotal);
        txtLimpar = findViewById(R.id.txtLimpar);
        mainLayout = findViewById(R.id.itensLayout);
        loader = findViewById(R.id.loader);

        loader.setVisibility(View.VISIBLE);

        final Singleton singleton = Singleton.getInstance();
        listaProdutos = singleton.getCarrinho();

        try {
            attLayout(listaProdutos);
        } finally {
            loader.setVisibility(View.GONE);
        }

        txtLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleton.removeAll();
                attLayout(singleton.getCarrinho());
            }
        });

    }

    private void attLayout(List<Produto> list){
        double auxPreco = 0;
        double auxTotal = 0;

        loader.setVisibility(View.VISIBLE);
        mainLayout.removeAllViews();
        for (Produto p : list) {
            if (p.getDescontoPromocao() == 0) {
                auxPreco = p.getPrecProduto();

            } else {
                auxPreco = p.getPrecProduto() - p.getDescontoPromocao();
            }
            addLines(p.getNomeProduto(), auxPreco, p.getIdProduto());
            auxTotal += auxPreco;
        }
        txtTotal.setText(Util.formatPreco(auxTotal));
        loader.setVisibility(View.GONE);
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
                    if(id == p.getIdProduto()) {
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
