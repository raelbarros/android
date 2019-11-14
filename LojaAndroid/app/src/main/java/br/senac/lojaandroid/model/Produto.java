package br.senac.lojaandroid.model;

public class Produto {
    private double descontoPromocao, precProduto;
    private String nomeProduto;
    private int idCategoria, idProduto, qtdMinEstoque;
    private boolean ativoProduto, descProduto;

    public Produto () {

    }

    public double getDescontoPromocao() {
        return descontoPromocao;
    }

    public void setDescontoPromocao(double descontoPromocao) {
        this.descontoPromocao = descontoPromocao;
    }

    public double getPrecProduto() {
        return precProduto;
    }

    public void setPrecProduto(double precProduto) {
        this.precProduto = precProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQtdMinEstoque() {
        return qtdMinEstoque;
    }

    public void setQtdMinEstoque(int qtdMinEstoque) {
        this.qtdMinEstoque = qtdMinEstoque;
    }

    public boolean isAtivoProduto() {
        return ativoProduto;
    }

    public void setAtivoProduto(boolean ativoProduto) {
        this.ativoProduto = ativoProduto;
    }

    public boolean isDescProduto() {
        return descProduto;
    }

    public void setDescProduto(boolean descProduto) {
        this.descProduto = descProduto;
    }
}
