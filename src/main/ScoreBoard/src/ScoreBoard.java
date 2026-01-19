import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe principal
public class ScoreBoard {

    // Método principal, ponto de entrada do programa
    public static void main(String[] args) {

        // Cria a janela principal (JFrame)
        JFrame frame = new JFrame("Placar");

        // Cria um painel (JPanel) para colocar os componentes
        JPanel panel = new JPanel();

        // Define tamanho da janela e a torna visível
        frame.setSize(360,640);
        frame.setVisible(true);

        // Adiciona o painel à janela
        frame.add(panel);

        // Define o tamanho do painel
        panel.setSize(360,640);

        // Impede redimensionamento da janela
        frame.setResizable(false);

        // Encerra o programa ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centraliza a janela na tela
        frame.setLocationRelativeTo(null);

        // Define layout nulo (para posicionamento manual dos componentes)
        frame.setLayout(null);

        // Adiciona rótulos com instruções e textos
        labelChato(panel, "Insira os dados abaixo", 10);
        label(panel,"Jogador 1",56);
        label(panel,"Jogador 2",156);
        label(panel,"Set por partidas",256);
        label(panel,"Ponto por set",356);

        // Campos de entrada de texto (text fields)
        JTextField textFieldOne = new JTextField("Jogador 1");
        textFieldOne.setBounds(10, 95, 320, 50);
        panel.add(textFieldOne);

        JTextField textFieldTwo = new JTextField("Jogador 2");
        textFieldTwo.setBounds(10, 195, 320, 50);
        panel.add(textFieldTwo);

        JTextField textFieldThree = new JTextField("3"); // Sets por partida
        textFieldThree.setBounds(10, 295, 320, 50);
        panel.add(textFieldThree);

        JTextField textFieldFour = new JTextField("5"); // Pontos por set
        textFieldFour.setBounds(10, 395, 320, 50);
        panel.add(textFieldFour);

        // Botão "Começar"
        JButton button = new JButton("Começar");
        button.setBounds(120, 470,100,50);

        // Ação ao clicar no botão
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Salva os dados digitados em uma classe chamada DataBase (não fornecida aqui)
                DataBase.playerOne = textFieldOne.getText().toUpperCase();
                DataBase.playerTwo = textFieldTwo.getText().toUpperCase();
                DataBase.set = Integer.parseInt(textFieldThree.getText());
                DataBase.point = Integer.parseInt(textFieldFour.getText());

                // Abre a próxima página (nova janela)
                new PageTwo();

                // Fecha a janela atual
                frame.dispose();
            }
        });

        // Adiciona o botão ao painel
        panel.add(button);
    }

    // Método para criar um JLabel com texto explicativo mais genérico
    public static void labelChato(JPanel panel, String o, int y) {
        JLabel label = new JLabel(o);
        label.setBounds(10,y,150,50);
        panel.add(label);
    }

    // Método para criar um JLabel com texto e posição vertical (y) específica
    public static void label(JPanel panel, String o, int y) {
        JLabel label = new JLabel(o);
        label.setBounds(10,y,100,50);
        panel.add(label);
    }
}
