package model;

import java.util.Date;

public class RelatorioSintetico {
    private int idCliente;
    private String nomeCliente;
    private Date dataVenda;
    private double valorTotal;

    public RelatorioSintetico(int idCliente, String nomeCliente, Date dataVenda, double valorTotal) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
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
}
