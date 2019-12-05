package br.senac.lojaandroid.validador;

import br.senac.lojaandroid.exception.PedidoException;
import br.senac.lojaandroid.model.Pedido;
import br.senac.lojaandroid.util.Util;

public class ValidadorPedido {

    public static void validate(Pedido p) throws PedidoException {
        if (p == null)
            throw new PedidoException("Pedido Invalido");

        if (Util.empty(p.getEndereco()))
            throw new PedidoException("Endereço não informado");

        if (Util.empty(p.getCep()))
            throw new PedidoException("CEP não informado");

        if (Util.empty(p.getBairro()))
            throw new PedidoException("Bairro não informado");

        if (Util.empty(p.getCidade()))
            throw new PedidoException("Cidade não informada");

        if (p.getIdCliente() == 0)
            throw new PedidoException("Cliente Invalido");

        if (Util.empty(p.getNunCartao()))
            throw new PedidoException("Cartão não informado");

        if (Util.empty(p.getCardCodigo()))
            throw new PedidoException("Codigo do cartão Invalido");

        if (Util.empty(p.getCardVal()))
            throw new PedidoException("Data de validade do cartão Invalido");



    }

}
