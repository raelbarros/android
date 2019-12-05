package br.senac.lojaandroid.validador;

import br.senac.lojaandroid.exception.ClienteException;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.Util;

public class ValidadorCliente {

    public static void validate(Cliente c) throws ClienteException  {
        if (c == null)
            throw  new ClienteException("Cliente Invalido");

        if (Util.empty(c.getNomeCompletoCliente()))
            throw new ClienteException("Nome ou Sobrenome não informado");

        if (Util.empty(c.getCelularCliente()))
            throw new ClienteException("Celular não informado");

        if (Util.empty(c.getCPFCliente()))
            throw new ClienteException("CPF não informado");


        if (Util.empty(c.getEmailCliente()))
            throw new ClienteException("E-mail não informado");

        if (Util.empty(c.getSenhaCliente()))
            throw new ClienteException("Senha não informado");

    }
}
