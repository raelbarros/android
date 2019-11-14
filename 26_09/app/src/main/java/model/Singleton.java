package model;

public class Singleton {
    private Usuario user;
    private Cliente cliente;
    private Produto produto;

    private static final Singleton INSTANCE = new Singleton();
    private Singleton(){
    }

    public static Singleton getInstance(){
        return INSTANCE;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
