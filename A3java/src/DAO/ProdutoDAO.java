package DAO;

import Model.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    public Connection getConexao() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            
            String url = "jdbc:mysql://localhost:3306/estoqueprodutos?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = ""; 

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Produto> getMinhaLista() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            Connection conn = this.getConexao();
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet res = stmt.executeQuery("SELECT * FROM estoque");
                while (res.next()) {
                    Produto p = new Produto(
                        res.getInt("id_produto"),
                        res.getString("nome_produto"),
                        res.getString("descricao_produto"),
                        res.getInt("quantidade_estoque"),
                        res.getDouble("preco")
                    );
                    lista.add(p);
                }
                stmt.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex.getMessage());
        }
        return lista;
    }

    public boolean InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO estoque(nome_produto, descricao_produto, quantidade_estoque, preco, data_cadastro) VALUES(?,?,?,?, CURDATE())";

        try {
            Connection conn = this.getConexao();
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, objeto.getNome());
                stmt.setString(2, objeto.getDescricao());
                stmt.setInt(3, objeto.getQuantidade());
                stmt.setDouble(4, objeto.getPreco());
                
                stmt.execute();
                stmt.close();
                return true;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir: " + erro.getMessage());
        }
        return false;
    }
}
