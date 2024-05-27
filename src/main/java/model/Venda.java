package model;

import java.util.Date;

/**
* 
* @param p - objeto do tipo Venda
*/ 
public class Venda { 
    private int id;
    private String cpf;
    private Date dataVenda;
    private double valorTotal;
    private String nomeCliente;

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Venda(String cpf, Date dataVenda, double valorTotal) {
        this.cpf = cpf;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
    }
    
    public Venda(int id, String cpf, Date dataVenda, double valorTotal) {
        this.id = id;
        this.cpf = cpf;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
    }
    
    public Venda(int id, String cpf, String nomeCliente, Date dataVenda, double valorTotal) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
    }
}
