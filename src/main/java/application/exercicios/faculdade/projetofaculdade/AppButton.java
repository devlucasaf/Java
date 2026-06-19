package application.exercicios.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class AppButton {

    private JFrame      janela;
    private Color       roxoClaro;
    private Color       lilasMedio;
    private JPanel      frameSelecao;
    private JPanel      frameInsercao;
    private JPanel      frameCarrinho;
    private ImageIcon   imagemSelect;
    private ImageIcon   imagemInsert;
    private ImageIcon   imagemUpdate;
    private ImageIcon   imagemDelete;
    private Runnable    acaoSelect;
    private Runnable    acaoInsert;
    private Runnable    acaoUpdate;
    private Runnable    acaoDelete;

    public AppButton(JFrame janela,
                    Color roxoClaro, Color lilasMedio,
                    JPanel frameSelecao, JPanel frameInsercao, JPanel frameCarrinho,
                    ImageIcon imagemSelecao, ImageIcon imagemInsert,
                    ImageIcon imagemUpdate, ImageIcon imagemDelete,
                    Runnable acaoSelect, Runnable acaoInsert,
                    Runnable acaoUpdate, Runnable acaoDelete) {

        this.janela         = janela;
        this.roxoClaro      = roxoClaro;
        this.lilasMedio     = lilasMedio;
        this.frameSelecao   = frameSelecao;
        this.frameInsercao  = frameInsercao;
        this.frameCarrinho  = frameCarrinho;
        this.imagemSelect   = imagemSelecao;
        this.imagemInsert   = imagemInsert;
        this.imagemUpdate   = imagemUpdate;
        this.imagemDelete   = imagemDelete;
        this.acaoSelect     = acaoSelect;
        this.acaoInsert     = acaoInsert;
        this.acaoUpdate     = acaoUpdate;
        this.acaoDelete     = acaoDelete;

        criarBotoesPrincipais();
        criarBotoesFrameSelecao();
        criarBotoesCarrinho();
        criarBotoesInsercao();
    }

    private void mostrarJogo() {
        System.out.println("Mostrar campos de jogo");
    }

    private void mostrarUsuario() {
        System.out.println("Mostrar campos de usuário");
    }

    private JButton criarBotao(Container container, String texto, int x, int y,
                                ImageIcon icone, Runnable acao) {

        JButton botao = new JButton(texto, icone);
        botao.setBounds(x, y, 150, 90);
        botao.setBackground(roxoClaro);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);

        botao.addActionListener(e -> acao.run());

        container.add(botao);
        return botao;
    }

    private void criarBotoesPrincipais() {

        janela.setLayout(null);

        criarBotao(janela.getContentPane(), "Select", 20, 120, imagemSelect, acaoSelect);
        criarBotao(janela.getContentPane(), "Insert", 20, 240, imagemInsert, acaoInsert);
        criarBotao(janela.getContentPane(), "Update", 20, 360, imagemUpdate, acaoUpdate);
        criarBotao(janela.getContentPane(), "Delete", 20, 480, imagemDelete, acaoDelete);
    }

    private void criarBotoesFrameSelecao() {

        frameSelecao.setLayout(null);

        frameSelecao.add(criarBotaoSimples("ID do Jogo", 20, 160, acaoSelect));
        frameSelecao.add(criarBotaoSimples("Nome", 150, 160, acaoSelect));
        frameSelecao.add(criarBotaoSimples("Gênero", 280, 160, acaoSelect));
        frameSelecao.add(criarBotaoSimples("Preço", 410, 160, acaoSelect));
        frameSelecao.add(criarBotaoSimples("Produtor", 540, 160, acaoSelect));
        frameSelecao.add(criarBotaoSimples("Idade", 670, 160, acaoSelect));

        frameSelecao.add(criarBotaoSimples("Nome Usuário", 20, 500, acaoSelect));
        frameSelecao.add(criarBotaoSimples("ID Usuário", 150, 500, acaoSelect));
    }

    private void criarBotoesCarrinho() {

        frameCarrinho.setLayout(null);

        frameCarrinho.add(criarBotaoSimples("Produtos", 150, 100, acaoSelect));
        frameCarrinho.add(criarBotaoSimples("Ver Carrinho", 20, 460, acaoSelect));
        frameCarrinho.add(criarBotaoSimples("Remover Produto", 240, 460, acaoSelect));
    }

    private void criarBotoesInsercao() {

        frameInsercao.setLayout(null);

        frameInsercao.add(criarBotaoSimples("Jogo", 520, 40, this::mostrarJogo));
        frameInsercao.add(criarBotaoSimples("Usuário", 640, 40, this::mostrarUsuario));

        frameInsercao.add(criarBotaoSimples("Inserir", 580, 500, this::mostrarJogo));
    }

    private JButton criarBotaoSimples(String texto, int x, int y, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, 120, 85);
        botao.setBackground(new Color(0x3d2e4c));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);

        botao.addActionListener(e -> acao.run());

        return botao;
    }
}