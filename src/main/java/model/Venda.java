package model;

import java.util.Date;
import javax.swing.JOptionPane;

public class Venda {
    // produto
    private int[] idProdutos;
    private int[] quantidadeProduto;
    private double[] precoProduto;
    static private int quantidadeItens = -1;
    
    // cliente
    private int idCliente;
    
    // venda
    private Date dataVenda;
    
    public int getIdProduto(int idProduto) {
        return acharPosicao(idProduto);
    }

    public void setIdProduto(int idProduto) {
        int posicao = acharPosicao(idProduto);
        
        if (posicao < 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return;
        }
        
        this.idProdutos[posicao] = idProduto;
    }

    public int getQuantidadeProduto(int idProduto) {
        int posicao = acharPosicao(idProduto);
        
        if (posicao < 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return posicao;
        }
        
        return quantidadeProduto[posicao];
    }

    public void setQuantidadeProduto(int idProduto, int quantidadeProduto) {
        int posicao = acharPosicao(idProduto);
        
        if (posicao < 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return;
        }
        
        this.quantidadeProduto[posicao] = quantidadeProduto;
    }

    public double getPrecoProduto(int idProduto) {
        int posicao = acharPosicao(idProduto);
        
        if (posicao < 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return posicao;
        }
        
        return precoProduto[posicao];
    }

    public void setPrecoProduto(int idProduto, double precoProduto) {
        int posicao = acharPosicao(idProduto);
        
        if (posicao < 0) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado");
            return;
        }
        
        this.precoProduto[posicao] = precoProduto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(int id, Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Venda(int idProduto, int quantidadeProduto, double precoProduto, int idCliente) {
        quantidadeItens++;
        this.idProdutos[quantidadeItens] = idProduto;
        this.quantidadeProduto[quantidadeItens] = quantidadeProduto;
        this.precoProduto[quantidadeItens] = precoProduto;
        this.idCliente = idCliente;
    }
    
    private int acharPosicao(int idProduto) {
        // O(N), achar solução melhor...
        for (int i = 0; i < quantidadeItens; i++) {
            if (idProdutos[i] == idProduto) {
                return i;
            }
        }
        
        return -1;
    }
}
