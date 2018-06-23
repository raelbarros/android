package com.example.ifsp.validator;

import com.example.ifsp.exception.ContatoException;
import com.example.ifsp.model.Contato;

    // Classe validadora do obj contato
public class ContatoValidador  {
    public static void validador(Contato c) throws ContatoException{
        // Se o obj for null, returna erro
        if (c == null) {
            throw new ContatoException("Contato vazio");
        }
        // Se o obj nao tive Nome, returna erro
        if (c.getNome() == null || c.getNome().trim().equals("")){
            throw new ContatoException("Informe o nome");
        }
        // Se o obj nao tiver CPF, retorna erro
        if (c.getCpf() == null || c.getCpf().trim().equals("")){
            throw new ContatoException("Informe o CPF");
        }
        // Se o obj nao tiver Idade, retorna erro
        if (c.getIdade() == null || c.getCpf().trim().equals("")){
            throw new ContatoException("Informe um contato");
        }
        // se o obj nao tiver Telefone, retorna erro
        if (c.getTelefone() == null || c.getCpf().trim().equals("")) {
            throw new ContatoException("Informe um telefone");
        }
        // Se o obj nao tiver E-mail, retorna erro
        if (c.getEmail() == null || c.getEmail().trim().equals("")){
            throw new ContatoException("Informe o e-mail");
        }

    }
}
