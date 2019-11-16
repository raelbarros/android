package br.senac.lojaandroid.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.senac.lojaandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Itens extends Fragment {


    public Itens() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_itens, container, false);

        int id = getArguments().getInt("id");
        System.out.println(id);

        return view;
    }

}
