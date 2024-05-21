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
import model.Produto;

public class ProdutoDao {
    public static String url = "jdbc:mysql://localhost:3306/lojainfo";
    public static String usu = "root";
    public static String pwd = "";
    
    public static boolean inserir(Produto objeto) {
        boolean inseriu = false;   
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return inseriu;
        }
        
        String comandoSQL = "INSERT INTO produto (nome, modelo, categoria, fabricante, cor, preco, estoque) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, objeto.getNome());
            statement.setString(2, objeto.getModelo());
            statement.setString(3, objeto.getCategoria());
            statement.setString(4, objeto.getFabricante());
            statement.setString(5, objeto.getCor());
            statement.setDouble(6, objeto.getPreco());
            statement.setInt(7, objeto.getEstoque());

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                inseriu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inseriu;
    }
    
    public static boolean alterar(Produto objeto) {
        boolean alterou = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return alterou;
        }
        
        String comandoSQL = "UPDATE computadores set hd = ?, processador = ? WHERE idComputador = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
//            statement.setString(1, objeto.getHd());
//            statement.setString(2, objeto.getProcessador());
//            statement.setInt(3, objeto.getId());
            
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
        
        String comandoSQL = "DELETE FROM computadores where idComputador = ?";
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
    
    public static ArrayList<Produto> listar() {
        ArrayList<Produto> computadorList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return computadorList;
        }
        
        String comandoSQL = "SELECT idComputador, hd, processador, marca FROM computadores";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {            
                int id = resultSet.getInt("idComputador");
                String hd = resultSet.getString("hd");
                String cpu = resultSet.getString("processador");

//                Produto objeto = new Produto(id, hd, cpu);
//                computadorList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return computadorList;
    }
    
    public static ArrayList<Produto> listar(String filtro) {
        ArrayList<Produto> computadorList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return computadorList;
        }
        
        String comandoSQL = "SELECT idComputador, hd, processador, marca FROM computadores WHERE processador LIKE ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, String.format("%s%s%s", "%", filtro, "%"));
            JOptionPane.showMessageDialog(null, statement);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {            
                int id = resultSet.getInt("idComputador");
                String hd = resultSet.getString("hd");
                String cpu = resultSet.getString("processador");

//                Produto objeto = new Produto(id, hd, cpu);
//                computadorList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return computadorList;
    }
    
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
