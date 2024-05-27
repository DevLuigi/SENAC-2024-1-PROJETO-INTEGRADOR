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
import model.RelatorioSintetico;
import model.Venda;

public class SinteticoDao {
    
    public static ArrayList<RelatorioSintetico> listar() {
        ArrayList<RelatorioSintetico> sintetico = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return sintetico;
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
                             
                RelatorioSintetico objeto = new RelatorioSintetico(id, cpf, nome, dataVenda, valorTotal);
                sintetico.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sintetico;
    }
    
    public static ArrayList<RelatorioSintetico> listar(Date dataInicial, Date dataFinal) {
        ArrayList<RelatorioSintetico> sintetico = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return sintetico;
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
                
                RelatorioSintetico objeto = new RelatorioSintetico(0, cpf, comandoSQL, dataVenda, 0);
                sintetico.add(objeto);
           }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sintetico;
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
