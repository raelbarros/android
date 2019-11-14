package br.senac.cep.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.cep.R;
import br.senac.cep.model.Cep;

/**
 * A simple {@link Fragment} subclass.
 */
public class CepFragment extends Fragment {

    TextView txtCep;

    Cep cep = null;

    public CepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        txtCep.setText(cep.getCep());

        return inflater.inflate(R.layout.fragment_cep, container, false);
    }

    public void AtualizarValue(Cep cep){
        if (cep == null) {
            cep = new Cep();
        }
        cep = cep;
    }

}
