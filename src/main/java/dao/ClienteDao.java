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
import model.Cliente;

public class ClienteDao {
    public static String url = "jdbc:mysql://localhost:3306/lojainfo";
    public static String usu = "root";
    public static String pwd = "senharoot";
    
    
/**
* 
* @param p - objeto do tipo Cliente DAO
* @return boolean - true: inseriu, false: falha
*  @throws
*/
    public static boolean inserir(Cliente objeto) {
        boolean inseriu = false;   
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return inseriu;
        }
        
        String comandoSQL = """
            INSERT INTO CLIENTE (
            	CPF,
                NOME,
                TELEFONE,
                EMAIL,
                SEXO,
                ESTADOCIVIL,
                DATANASCIMENTO,
                CEP,
                LOGRADOURO,
                NUMERO,
                COMPLEMENTO,
                BAIRRO,
                CIDADE,
                ESTADO,
                PAIS
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)""";

        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, objeto.getCpf());
            statement.setString(2, objeto.getNome());
            statement.setString(3, objeto.getTelefone());
            statement.setString(4, objeto.getEmail());
            statement.setString(5, objeto.getSexo());
            statement.setString(6, objeto.getEstadoCivil());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (objeto.getDataNascimento() != null) {
                String dataFormatada = sdf.format(objeto.getDataNascimento());
                statement.setString(7, dataFormatada);
            } else {
                statement.setString(7, null);
            }
            
            statement.setString(8, objeto.getCep());
            statement.setString(9, objeto.getLogradouro());
            statement.setInt(10, objeto.getNumero());
            statement.setString(11, objeto.getComplemento());
            statement.setString(12, objeto.getBairro());
            statement.setString(13, objeto.getCidade());
            statement.setString(14, objeto.getEstado());
            statement.setString(15, objeto.getPais());

            if(verificarClienteCadastrado(objeto.getCpf())) {
                JOptionPane.showMessageDialog(null, "Este cliente já está cadastrado no sistema");
                return false;
            }
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                inseriu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inseriu;
    }
    
/**
* 
* @param p - objeto do tipo Verificar Cliente
* @return boolean - true: existe, false: falha
* @throws
*/
    private static boolean verificarClienteCadastrado(String cpf) {
        boolean existe = false;   
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return existe;
        }
        
        String comandoSQL = """
            SELECT 
                CPF 
            FROM 
                CLIENTE
            WHERE
                CPF = ?
        """;

        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                return true;
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existe;
    }
    
/**
* 
* @param p - objeto do tipo alterar cadastro Cliente
* @return boolean - true: alterou, false: falha
* @throws
*/    
    public static boolean alterar(Cliente objeto) {
        boolean alterou = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return alterou;
        }
        
        String comandoSQL = """ 
            UPDATE CLIENTE set 
                NOME = ?,
                TELEFONE = ?,
                EMAIL = ?,
                SEXO = ?,
                ESTADOCIVIL = ?,
                DATANASCIMENTO = ?,
                CEP = ?,
                LOGRADOURO = ?,
                NUMERO = ?,
                COMPLEMENTO = ?,
                BAIRRO = ?,
                CIDADE = ?,
                ESTADO = ?,
                PAIS = ?
            WHERE 
                CPF = ?
        """;
        
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, objeto.getNome());
            statement.setString(2, objeto.getTelefone());
            statement.setString(3, objeto.getEmail());
            statement.setString(4, objeto.getSexo());
            statement.setString(5, objeto.getEstadoCivil());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (objeto.getDataNascimento() != null) {
                String dataFormatada = sdf.format(objeto.getDataNascimento());
                statement.setString(6, dataFormatada);
            } else {
                statement.setString(6, null);
            }
            
            statement.setString(7, objeto.getCep());
            statement.setString(8, objeto.getLogradouro());
            statement.setInt(9, objeto.getNumero());
            statement.setString(10, objeto.getComplemento());
            statement.setString(11, objeto.getBairro());
            statement.setString(12, objeto.getCidade());
            statement.setString(13, objeto.getEstado());
            statement.setString(14, objeto.getPais());
            statement.setString(15, objeto.getCpf());
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                alterou = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alterou;
    }
    
/**
* 
* @param p - objeto do tipo excluir Cliente
* @return boolean - true: excluiu, false: falha
* @throws
*/    
    public static boolean excluir(String cpf) {
        boolean excluiu = false;
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return excluiu;
        }
        
        String comandoSQL = "DELETE FROM CLIENTE WHERE CPF = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            statement.setString(1, cpf);
            
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                excluiu = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return excluiu;
    }
    
/**
* 
* @param p - objeto do tipo listar Cliente
* @return ArrayList clienteList
* @throws
*/    
    public static ArrayList<Cliente> listar() {
        ArrayList<Cliente> clienteList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return clienteList;
        }
        
        String comandoSQL = "SELECT * FROM CLIENTE";
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {   
                String cpf = resultSet.getString("CPF");
                String nome = resultSet.getString("NOME");
                String telefone = resultSet.getString("TELEFONE");
                String email = resultSet.getString("EMAIL");
                String sexo = resultSet.getString("SEXO");
                String estadoCivil = resultSet.getString("ESTADOCIVIL");
                Date dataNascimento = resultSet.getDate("DATANASCIMENTO");
                String cep = resultSet.getString("CEP");
                String logradouro = resultSet.getString("LOGRADOURO");
                int numero = resultSet.getInt("NUMERO");
                String complemento = resultSet.getString("COMPLEMENTO");
                String bairro = resultSet.getString("BAIRRO");
                String cidade = resultSet.getString("CIDADE");
                String estado = resultSet.getString("ESTADO");
                String pais = resultSet.getString("PAIS");
                
                Cliente objeto = new Cliente(
                        cpf, 
                        nome, 
                        telefone,
                        email, 
                        sexo,
                        estadoCivil, 
                        dataNascimento,
                        cep, 
                        logradouro, 
                        numero, 
                        complemento, 
                        bairro, 
                        cidade, 
                        estado,
                        pais
                );
                clienteList.add(objeto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clienteList;
    }
    
/**
* 
* @param p - objeto do tipo listar Cliente com filtros
* @return ArrayList clienteList
* @throws
*/     
    public static ArrayList<Cliente> listar(String nomeFiltro, String cpfFiltro) {
        ArrayList<Cliente> clienteList = new ArrayList<>();
        Connection conexao = conectarBD();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar ao Banco de Dados");
            return clienteList;
        }
        
        String comandoSQL = "SELECT * FROM CLIENTE WHERE NOME LIKE ? OR CPF LIKE ?";
       
        try {
            PreparedStatement statement = conexao.prepareStatement(comandoSQL);
            if (nomeFiltro.equals("")) {
                statement.setString(1, "");
            } else {
                statement.setString(1, String.format("%s%s%s", "%", nomeFiltro, "%"));
            }
            
            if (cpfFiltro.equals("")) {
                statement.setString(2, "");
            } else {
                statement.setString(2, String.format("%s%s%s", "%", cpfFiltro, "%"));
            }
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {            
                String cpf = resultSet.getString("CPF");
                String nome = resultSet.getString("NOME");
                String telefone = resultSet.getString("TELEFONE");
                String email = resultSet.getString("EMAIL");
                String sexo = resultSet.getString("SEXO");
                String estadoCivil = resultSet.getString("ESTADOCIVIL");
                Date dataNascimento = resultSet.getDate("DATANASCIMENTO");
                String cep = resultSet.getString("CEP");
                String logradouro = resultSet.getString("LOGRADOURO");
                int numero = resultSet.getInt("NUMERO");
                String complemento = resultSet.getString("COMPLEMENTO");
                String bairro = resultSet.getString("BAIRRO");
                String cidade = resultSet.getString("CIDADE");
                String estado = resultSet.getString("ESTADO");
                String pais = resultSet.getString("PAIS");
                
                Cliente objeto = new Cliente(
                        cpf, 
                        nome, 
                        telefone,
                        email, 
                        sexo,
                        estadoCivil, 
                        dataNascimento,
                        cep, 
                        logradouro, 
                        numero, 
                        complemento, 
                        bairro, 
                        cidade, 
                        estado,
                        pais
                );
                clienteList.add(objeto);
           }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clienteList;
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
