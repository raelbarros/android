package br.senac.lojaandroid.util;

import java.util.ArrayList;
import java.util.List;

import br.senac.lojaandroid.model.Produto;

public class Singleton {
    private List<Produto> carrinho;
    private List<Produto> itensSearch;

    private static final Singleton INSTANCE = new Singleton();
    private Singleton() {

    }

    public static  Singleton getInstance() {
        return INSTANCE;
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Produto> carrinho) {
        this.carrinho = carrinho;
    }

    public void addCarrinho(Produto p) {
        if (carrinho == null ){
            carrinho = new ArrayList<Produto>();
        }
        this.carrinho.add(p);
    }

    public void removeAll() {
        this.carrinho.clear();
    }

}
