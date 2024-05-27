package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ItemVenda;

public class VendaItemDao {
    public static String url = "jdbc:mysql://localhost:3306/lojainfo";
    public static String usu = "root";
    public static String pwd = "senharoot";
    
    public static boolean inserir(ItemVenda objeto) {
        boolean inseriu = false;   
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return inseriu;
        }
        
        String comandoSQL = """
            INSERT INTO PEDIDOITEM (
            	IDPEDIDO,
                IDPRODUTO,
                QUANTIDADE,
                VALORUNITARIO
            ) VALUES (?,?,?,?)""";

        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setLong(1, objeto.getIdPedido());
            statement.setInt(2, objeto.getIdProduto());
            statement.setInt(3, objeto.getQuantidadeProduto());
            statement.setDouble(4, objeto.getPrecoProduto());
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                inseriu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inseriu;
    }
    
    public static boolean alterar(ItemVenda objeto) {
        boolean alterou = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return alterou;
        }
        
        String comandoSQL = """ 
            UPDATE PEDIDOITEM SET 
               QUANTIDADE = ?,             
               VALORUNITARIO = ?
            WHERE 
               ID = ?
        """;
        
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setInt(1, objeto.getQuantidadeProduto());
            statement.setDouble(2, objeto.getPrecoProduto());
            statement.setInt(3, objeto.getId());
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                alterou = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alterou;
    }
    
    public static boolean excluir(int id) {
        boolean excluiu = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return excluiu;
        }
        
        String comandoSQL = "DELETE FROM PEDIDOITEM WHERE ID = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setInt(1, id);
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                excluiu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return excluiu;
    }
    
    public static boolean excluirTodos(int id) {
        boolean excluiu = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return excluiu;
        }
        
        String comandoSQL = "DELETE FROM PEDIDOITEM WHERE IDPEDIDO = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setInt(1, id);
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                excluiu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return excluiu;
    }
    
    public static ArrayList<ItemVenda> listar() {
        ArrayList<ItemVenda> vendaList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return vendaList;
        }
        
        String comandoSQL = "SELECT * FROM PEDIDOITEM";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {   
                int idPedido = resultSet.getInt("IDPEDIDO");
                int idProduto = resultSet.getInt("IDPRODUTO");
                int quantidade = resultSet.getInt("QUANTIDADE");
                Double precoProduto = resultSet.getDouble("VALORUNITARIO");
                
                // validar se isso está correto
                ItemVenda objeto = new ItemVenda(idPedido, idProduto, quantidade, precoProduto);
                vendaList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vendaList;
    }
    
    public static ArrayList<ItemVenda> listarItensPedido(int id) {
        ArrayList<ItemVenda> vendaList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return vendaList;
        }
        
        String comandoSQL = """
            SELECT 
                PRO.ID,
                PRO.NOME,
                ITE.QUANTIDADE,
                ITE.VALORUNITARIO                    
            FROM 
                PEDIDOITEM ITE 
            INNER JOIN PRODUTO PRO ON PRO.ID = ITE.IDPRODUTO
            WHERE 
                IDPEDIDO = ?
        """;
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {   
                int idProduto = resultSet.getInt("ID");
                String nome = resultSet.getString("NOME");
                int quantidade = resultSet.getInt("QUANTIDADE");
                Double precoProduto = resultSet.getDouble("VALORUNITARIO");
                
                ItemVenda objeto = new ItemVenda(idProduto, nome, precoProduto, quantidade);
                vendaList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vendaList;
    }
    
//    public static ArrayList<ItemVenda> listar(Date dataInicial, Date dataFinal) {
//        ArrayList<ItemVenda> vendaList = new ArrayList<>();
//        Connection conexao = conectarBD();
//        if (conexao == null) {
//            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
//            return vendaList;
//        }
//        
//        String comandoSQL = "SELECT * FROM PEDIDOITEM WHERE DATAHORA >= ? AND DATAHORA <= ?";
//       
//        try {
//            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            if (dataInicial != null) {
//                String dataFormatada = sdf.format(dataInicial);
//                statement.setString(2, dataFormatada);
//            } else {
//                statement.setString(2, null);
//            }
//         
//            if (dataFinal != null) {
//                String dataFormatada = sdf.format(dataFinal);
//                statement.setString(2, dataFormatada);
//            } else {
//                statement.setString(2, null);
//            }
//            
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {            
//                String cpf = resultSet.getString("IDCLIENTE");
//                Date dataVenda = resultSet.getDate("DATAHORA");
//                Double valorTotal = resultSet.getDouble("VALORTOTAL");
//                
//                ItemVenda objeto = new ItemVenda(cpf, dataVenda, valorTotal);
//                vendaList.add(objeto);
//           }
//        } catch (SQLException ex) {
//            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return vendaList;
//    }
    
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
