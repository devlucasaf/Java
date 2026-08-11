package games.plataforma.minigames.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import games.plataforma.minigames.jogos.velha.util.JogoDaVelhaUI;
import games.plataforma.minigames.jogos.forca.util.ForcaUI;
import games.plataforma.minigames.jogos.campominado.util.CampoMinadoUI;
import games.plataforma.minigames.jogos.memoria.util.JogoDaMemoriaUI;
import games.plataforma.minigames.jogos.termo.util.TermoUI;
import games.plataforma.minigames.jogos.adivinhanumero.util.AdivinhaNumeroUI;
import games.plataforma.minigames.jogos.bingo.util.BingoUI;
import games.plataforma.minigames.jogos.pedrapapeltesoura.util.PedraPapelTesouraUI;
import games.plataforma.minigames.jogos.capitais.util.CapitaisUI;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        TemaEscuro.aplicar();
        setTitle("Central de Jogos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridLayout(3, 3, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        TemaEscuro.configurarComponente(painel);

        String[] nomes = {
                "Jogo da Velha", "Forca", "Campo Minado",
                "Jogo da Memória", "Termo", "Adivinhe o Número",
                "Bingo", "Pedra, Papel e Tesoura", "Capitais"
        };

        Runnable[] acoes = {
                () -> new JogoDaVelhaUI().setVisible(true),
                () -> new ForcaUI().setVisible(true),
                () -> new CampoMinadoUI().setVisible(true),
                () -> new JogoDaMemoriaUI().setVisible(true),
                () -> new TermoUI().setVisible(true),
                () -> new AdivinhaNumeroUI().setVisible(true),
                () -> new BingoUI().setVisible(true),
                () -> new PedraPapelTesouraUI().setVisible(true),
                () -> new CapitaisUI().setVisible(true)
        };

        for (int i = 0; i < nomes.length; i++) {
            JButton botao = criarBotao(nomes[i]);
            final Runnable acao = acoes[i];
            botao.addActionListener((ActionEvent e) -> acao.run());
            painel.add(botao);
        }

        add(painel);

        // Título
        JLabel titulo = new JLabel("Plataforma de Minigames", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        TemaEscuro.configurarComponente(titulo);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(64, 64, 64));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(85, 85, 85));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(64, 64, 64));
            }
        });
        return botao;
    }
}
