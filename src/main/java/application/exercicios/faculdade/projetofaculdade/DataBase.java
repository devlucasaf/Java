package application.exercicios.faculdade.projetofaculdade;

import java.sql.*;

public class DataBase {

    private Connection conexao;

    public void conectar() {
        try {
            conexao = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_wixus",
                    "root",
                    "ceub123456"
            );
            System.out.println("Conectado ao banco!");
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

    public void criarTabelaJogos() {
        String sql = """
            CREATE TABLE IF NOT EXISTS tb_jogos(
                id_jogo INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(50),
                preco DECIMAL(9,2),
                faixa_etaria INT,
                desenvolvedor VARCHAR(30),
                data_lanc DATE,
                plataformas VARCHAR(30),
                genero VARCHAR(50)
            )
        """;

        executarSQL(sql, "Tabela Jogos criada");
    }

    public void inserirJogo(String nome, double preco, String data,
                            int faixaEtaria, String desenvolvedor,
                            String plataformas, String genero) {

        String sql = """
            INSERT INTO tb_jogos
            (nome, preco, data_lanc, faixa_etaria, desenvolvedor, plataformas, genero)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setString(3, data);
            stmt.setInt(4, faixaEtaria);
            stmt.setString(5, desenvolvedor);
            stmt.setString(6, plataformas);
            stmt.setString(7, genero);

            stmt.executeUpdate();
            System.out.println("Jogo inserido!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarJogoPorId(int id) {
        String sql = "SELECT * FROM tb_jogos WHERE id_jogo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Nome: " + rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarJogo(int id) {
        String sql = "DELETE FROM tb_jogos WHERE id_jogo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Jogo deletado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executarSQL(String sql, String mensagem) {
        try (Statement stmt = conexao.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println(mensagem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}