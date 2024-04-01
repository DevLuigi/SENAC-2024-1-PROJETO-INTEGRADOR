package model;

public class Produto {
    private String 
            nome,
            modelo,
            categoria,
            fabricante,
            cor;
    private double preco;
    private int estoque;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Produto() {}

    public Produto(String nome, String modelo, String categoria, String fabricante, String cor, double preco, int estoque) {
        this.nome = nome;
        this.modelo = modelo;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.cor = cor;
        this.preco = preco;
        this.estoque = estoque;
    }
  
    public static void inserirProduto() {
        // ver com o professor como fazer assim
    }
}
