package dao;

import static dao.VendaDao.pwd;
import static dao.VendaDao.url;
import static dao.VendaDao.usu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.RelatorioAnalitico;
import model.RelatorioSintetico;

public class AnaliticoDao {
    
/**
* 
* @param p - objeto do tipo Relatório Analítico DAO
* @return ArrayList analitico
*  @throws
*/
    public static ArrayList<RelatorioAnalitico> listar() {
        ArrayList<RelatorioAnalitico> analitico = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return analitico;
        }
        
        String comandoSQL = """
            select
            	cliente.cpf,
                produto.id,
                produto.nome,
                pedidoitem.quantidade,
                produto.preco
            from cliente
            	inner join pedido on cliente.cpf = pedido.idcliente
                inner join pedidoitem on pedido.id = pedidoitem.idpedido
                inner join produto on pedidoitem.idproduto = produto.id;
                            """;
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                int idProduto = resultSet.getInt("id");
                String nomeProduto = resultSet.getString("nome");
                int Quantidade = resultSet.getInt("quantidade");
                Double valor = resultSet.getDouble("preco");
                             
                RelatorioAnalitico objeto = new RelatorioAnalitico(cpf, idProduto, nomeProduto, Quantidade, valor);
                analitico.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return analitico;
    }
    
/**
* 
* @param p - objeto do tipo Relatório Analítico DAO
* @return ArrayList analitico
* @deprecated
*  @throws
*/    
    public static ArrayList<RelatorioSintetico> listar(Date dataInicial, Date dataFinal) {
        ArrayList<RelatorioSintetico> sintetico = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return sintetico;
        }
        
        String comandoSQL = """
            select
            	cliente.cpf,
                produto.id,
                produto.nome,
                pedidoitem.quantidade,
                produto.preco
            from cliente
            	inner join pedido on cliente.cpf = pedido.idcliente
                inner join pedidoitem on pedido.id = pedidoitem.idpedido
                inner join produto on pedidoitem.idproduto = produto.id;
            WHERE DATAHORA >= ? AND DATAHORA <= ?
                            """;
       
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (dataInicial != null) {
                String dataFormatada = sdf.format(dataInicial);
                statement.setString(2, dataFormatada);
            } else {
                statement.setString(2, null);
            }
         
            if (dataFinal != null) {
                String dataFormatada = sdf.format(dataFinal);
                statement.setString(2, dataFormatada);
            } else {
                statement.setString(2, null);
            }
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {            
                String cpf = resultSet.getString("IDCLIENTE");
                Date dataVenda = resultSet.getDate("DATAHORA");
                Double valorTotal = resultSet.getDouble("VALORTOTAL");
                
                RelatorioSintetico objeto = new RelatorioSintetico(0, cpf, comandoSQL, dataVenda, 0);
                sintetico.add(objeto);
           }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sintetico;
    }
    
/**
* 
* @param p - objeto do tipo conexão com BD
* @return Connection null
* @throws
*/         
    public static Connection conectarBD() {
        try {
            Connection conexao = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(url, usu, pwd);
            return conexao;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
