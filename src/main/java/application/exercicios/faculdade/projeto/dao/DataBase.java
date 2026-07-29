package application.exercicios.faculdade.projeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL      = "jdbc:mysql://localhost:3306/db_wixus";
    private static final String USUARIO  = "root";
    private static final String SENHA    = "ceub123456";

    private Connection conexao;

    public void conectar() {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conectado ao banco!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexao encerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criarBanco() {
        try (Statement stmt = conexao.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS db_wixus");
            System.out.println("Banco criado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}

