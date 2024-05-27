package dao;

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
import model.Venda;

public class VendaDao {
    public static String url = "jdbc:mysql://localhost:3306/lojainfo";
    public static String usu = "root";
    public static String pwd = "senharoot";
    
    public static long inserir(Venda objeto) {
        long inseriu = 0;   
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return inseriu;
        }
        
        String comandoSQL = """
            INSERT INTO PEDIDO (
            	IDCLIENTE,
                DATAHORA,
                VALORTOTAL
            ) VALUES (?,?,?)""";

        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, objeto.getCpf());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (objeto.getDataVenda() != null) {
                String dataFormatada = sdf.format(objeto.getDataVenda());
                statement.setString(2, dataFormatada);
            } else {
                statement.setString(2, null);
            }
            statement.setDouble(3, objeto.getValorTotal());
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    inseriu = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inseriu;
    }
    
    public static boolean alterar(Venda objeto) {
        boolean alterou = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return alterou;
        }
        
        String comandoSQL = """ 
            UPDATE PEDIDO SET 
                IDCLIENTE = ?,
                DATAHORA = ?,
                VALORTOTAL = ?                
            WHERE 
               ID = ?
        """;
        
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, objeto.getCpf());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (objeto.getDataVenda() != null) {
                String dataFormatada = sdf.format(objeto.getDataVenda());
                statement.setString(2, dataFormatada);
            } else {
                statement.setString(2, null);
            }
            statement.setDouble(3, objeto.getValorTotal());
            statement.setInt(4, objeto.getId());
            
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
        
        VendaItemDao vendaItemDao = new VendaItemDao();
        excluiu = vendaItemDao.excluirTodos(id);
        if (!excluiu) {
            JOptionPane.showMessageDialog(null, "Falha ao excluir itens do pedido");
            return excluiu;
        }
        
        String comandoSQL = "DELETE FROM PEDIDO WHERE ID = ?";
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
    
    public static ArrayList<Venda> listar() {
        ArrayList<Venda> vendaList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return vendaList;
        }
        
        String comandoSQL = """
            SELECT
                PED.ID,
                CLI.CPF,
                CLI.NOME,
                PED.DATAHORA,
                PED.VALORTOTAL
            FROM
                PEDIDO PED
            INNER JOIN CLIENTE CLI ON PED.IDCLIENTE = CLI.CPF
                            """;
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) { 
                int id = resultSet.getInt("ID");
                String cpf = resultSet.getString("CPF");
                String nome = resultSet.getString("NOME");
                Date dataVenda = resultSet.getDate("DATAHORA");
                Double valorTotal = resultSet.getDouble("VALORTOTAL");
                             
                Venda objeto = new Venda(id, cpf, nome, dataVenda, valorTotal);
                vendaList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vendaList;
    }
    
    public static ArrayList<Venda> listar(Date dataInicial, Date dataFinal) {
        ArrayList<Venda> vendaList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return vendaList;
        }
        
        String comandoSQL = "SELECT * FROM PEDIDO WHERE DATAHORA >= ? AND DATAHORA <= ?";
       
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
                
                Venda objeto = new Venda(cpf, dataVenda, valorTotal);
                vendaList.add(objeto);
           }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vendaList;
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
