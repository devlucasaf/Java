package application.exercicios.faculdade.projeto.dao;

import application.exercicios.faculdade.projeto.model.Jogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {

    private final Connection conexao;

    public JogoDAO(DataBase banco) {
        this.conexao = banco.getConexao();
    }

    public void criarTabela() {
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

        try (Statement stmt = conexao.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela Jogos criada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Jogo jogo) {
        String sql = """
            INSERT INTO tb_jogos
            (nome, preco, data_lanc, faixa_etaria, desenvolvedor, plataformas, genero)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, jogo.getNome());
            stmt.setDouble(2, jogo.getPreco());
            stmt.setString(3, jogo.getDataLancamento());
            stmt.setInt(4, jogo.getFaixaEtaria());
            stmt.setString(5, jogo.getDesenvolvedor());
            stmt.setString(6, jogo.getPlataformas());
            stmt.setString(7, jogo.getGenero());

            stmt.executeUpdate();
            System.out.println("Jogo inserido!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Jogo buscarPorId(int id) {
        String sql = "SELECT * FROM tb_jogos WHERE id_jogo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return montarJogo(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Jogo> listarTodos() {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM tb_jogos";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                jogos.add(montarJogo(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jogos;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tb_jogos WHERE id_jogo = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Jogo deletado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Jogo montarJogo(ResultSet rs) throws SQLException {
        Jogo jogo = new Jogo();
        jogo.setId(rs.getInt("id_jogo"));
        jogo.setNome(rs.getString("nome"));
        jogo.setPreco(rs.getDouble("preco"));
        jogo.setFaixaEtaria(rs.getInt("faixa_etaria"));
        jogo.setDesenvolvedor(rs.getString("desenvolvedor"));
        jogo.setDataLancamento(rs.getString("data_lanc"));
        jogo.setPlataformas(rs.getString("plataformas"));
        jogo.setGenero(rs.getString("genero"));
        return jogo;
    }
}

