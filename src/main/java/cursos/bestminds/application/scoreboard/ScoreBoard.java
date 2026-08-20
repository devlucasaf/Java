package cursos.bestminds.application.scoreboard;

import javax.swing.*;

public class ScoreBoard {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScoreBoard::criarTelaInicial);
    }

    private static void criarTelaInicial() {
        JFrame janela = new JFrame("Placar - Configuração");
        JPanel painel = new JPanel();

        janela.setSize(360, 640);
        janela.add(painel);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        painel.setLayout(null);

        // Labels
        criarRotulo(painel, "Insira os dados abaixo", 10, 16);
        criarRotulo(painel, "Jogador 1", 10, 56);
        criarRotulo(painel, "Jogador 2", 10, 156);
        criarRotulo(painel, "Sets por partida", 10, 256);
        criarRotulo(painel, "Pontos por set", 10, 356);

        // Campos de texto
        JTextField campoJogador1    = criarCampoTexto(painel, "Jogador 1", 95);
        JTextField campoJogador2    = criarCampoTexto(painel, "Jogador 2", 195);
        JTextField campoSets        = criarCampoTexto(painel, "3", 295);
        JTextField campoPontos      = criarCampoTexto(painel, "5", 395);

        // Botão começar
        JButton botaoComecar = new JButton("Começar");
        botaoComecar.setBounds(120, 470, 100, 50);
        painel.add(botaoComecar);

        botaoComecar.addActionListener(e -> {
            try {
                String nomeJogador1 = campoJogador1.getText().trim();
                String nomeJogador2 = campoJogador2.getText().trim();
                int sets = Integer.parseInt(campoSets.getText().trim());
                int pontos = Integer.parseInt(campoPontos.getText().trim());

                if (nomeJogador1.isBlank() || nomeJogador2.isBlank()) {
                    exibirErro(janela, "Os nomes dos jogadores não podem ser vazios!");
                    return;
                }

                if (sets <= 0 || pontos <= 0) {
                    exibirErro(janela, "Sets e pontos devem ser maiores que zero!");
                    return;
                }

                DataBase dados = new DataBase(nomeJogador1, nomeJogador2, sets, pontos);
                new TelaPlacar(dados);
                janela.dispose();

            } catch (NumberFormatException ex) {
                exibirErro(janela, "Sets e pontos devem ser números válidos!");
            }
        });

        janela.setVisible(true);
    }

    private static void criarRotulo(JPanel painel, String texto, int x, int y) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setBounds(x, y, 200, 50);
        painel.add(rotulo);
    }

    private static JTextField criarCampoTexto(JPanel painel, String textoInicial, int y) {
        JTextField campo = new JTextField(textoInicial);
        campo.setBounds(10, y, 320, 50);
        painel.add(campo);
        return campo;
    }

    private static void exibirErro(JFrame janela, String mensagem) {
        JOptionPane.showMessageDialog(janela, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
