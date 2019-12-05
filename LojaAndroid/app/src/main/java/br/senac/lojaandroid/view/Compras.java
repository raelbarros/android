package br.senac.lojaandroid.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.model.Pedido;
import br.senac.lojaandroid.util.LojaDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Compras extends Fragment {

    private ViewGroup mainLayout;
    private ProgressBar loader;
    private TextView txtVazio;

    public Compras() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compras, container, false);


        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.setTitle("Suas Compras");
        }

        mainLayout = view.findViewById(R.id.itens_compras);
        loader = view.findViewById(R.id.loader);
        txtVazio = view.findViewById(R.id.txtVazio);

        LojaDatabase appDB = LojaDatabase.getInstance(getContext());
        List<Pedido> listaPedido = appDB.pedidoDao().getAllPedido();

        if (listaPedido == null || listaPedido.isEmpty()) {
            txtVazio.setVisibility(View.VISIBLE);
        }

        for (Pedido p : listaPedido) {
            addCard(p.getDate(), p.getTotal(), p.getNunPedido());

        }
        loader.setVisibility(View.GONE);

        return view;


    }


    private void addCard(final String data, final String preco, final long id) {
        final CardView cardview = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.cards_compras_layout, mainLayout, false);
        final TextView txtData = cardview.findViewById(R.id.txtData);
        final TextView txtPreco = cardview.findViewById(R.id.txtValor);

        txtData.setText(data);
        txtPreco.setText(preco);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListaItensComprados.class);
                intent.putExtra("id", id);
                intent.putExtra("title", data);
                startActivity(intent);

            }
        });
        mainLayout.addView(cardview);
    }
}
