import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe da terceira tela (final), que mostra o resultado da partida
public class PageThree {
    public PageThree() {

        // Criação da janela principal (frame) e do painel que vai conter os componentes
        JFrame frame = new JFrame("Placar");
        JPanel panel = new JPanel();
        frame.setSize(360, 640);               // Tamanho fixo da janela
        frame.setVisible(true);                // Exibe a janela
        frame.add(panel);                      // Adiciona o painel à janela
        panel.setSize(360, 640);               // Tamanho do painel
        frame.setResizable(false);             // Impede redimensionamento da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o app ao encerrar
        frame.setLocationRelativeTo(null);     // Centraliza a janela na tela
        panel.setLayout(null);                 // Layout manual (absoluto)

        // Label de mensagem de parabéns para o vencedor
        JLabel label = new JLabel();
        label.setBounds(35, 115, 320, 150);
        panel.add(label);

        // Verifica quem venceu e personaliza a mensagem e cor
        if (DataBase.setPointPlayerOne == 3) {
            label.setText("Parabéns! " + DataBase.playerOne + " Você Ganhou!");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.RED);
        } 
        else {
            label.setText("Parabéns! " + DataBase.playerTwo + " Você Ganhou!");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.BLUE);
        }

        // Label título "PLACAR FINAL"
        JLabel labelOneFinish = new JLabel("PLACAR FINAL");
        labelOneFinish.setBounds(105, 250, 150, 50);
        labelOneFinish.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(labelOneFinish);

        // Placar final do jogador 1 (com cor vermelha)
        JLabel setsPlayerOne = new JLabel(DataBase.setPointPlayerOne + " SETS");
        setsPlayerOne.setBounds(35, 300, 100, 50);
        setsPlayerOne.setFont(new Font("Arial", Font.BOLD, 20));
        setsPlayerOne.setForeground(Color.RED);
        panel.add(setsPlayerOne);

        // Label "X" entre os dois placares (visualização tipo "2 X 1")
        JLabel labelTwoFinish = new JLabel("X");
        labelTwoFinish.setBounds(165, 300, 50, 50);
        labelTwoFinish.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(labelTwoFinish);

        // Placar final do jogador 2 (com cor azul)
        JLabel setsPlayerTwo = new JLabel(DataBase.setPointPlayerTwo + " SETS");
        setsPlayerTwo.setBounds(225, 300, 100, 50);
        setsPlayerTwo.setFont(new Font("Arial", Font.BOLD, 20));
        setsPlayerTwo.setForeground(Color.BLUE);
        panel.add(setsPlayerTwo);

        // Botão para encerrar o programa com o texto "FIM"
        JButton closeButton = new JButton("FIM");
        closeButton.setBounds(120, 470, 100, 50); // Posição central inferior
        panel.add(closeButton);

        // Ação do botão: fecha a janela e encerra o programa
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();      // Fecha a janela
                System.exit(0);       // Encerra a aplicação
            }
        });
    }
}
