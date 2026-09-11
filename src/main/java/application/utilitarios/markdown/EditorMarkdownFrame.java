package application.utilitarios.markdown;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JEditorPane;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EditorMarkdownFrame extends JFrame {

    private final ServicoMarkdown   servicoMarkdown;
    private final JTextArea         areaMarkdown;
    private final JEditorPane       painelPreview;
    private final JLabel            rotuloStatus;
    private Path                    arquivoAtual;
    private boolean                 alterado;
    private boolean                 atualizacaoInterna;

    public EditorMarkdownFrame() {
        super("Markdown Editor");
        this.servicoMarkdown = new ServicoMarkdown();
        this.areaMarkdown = new JTextArea();
        this.painelPreview = new JEditorPane();
        this.rotuloStatus = new JLabel("Novo arquivo");
        this.alterado = false;
        this.atualizacaoInterna = false;

        configurarJanela();
        configurarComponentes();
        configurarEventos();
        atualizarPreview();
        atualizarTitulo();
    }

    // --- CONFIGURA A JANELA PRINCIPAL ---
    private void configurarJanela() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(960, 600));
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // --- CRIA E POSICIONA OS COMPONENTES DA TELA ---
    private void configurarComponentes() {
        areaMarkdown.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaMarkdown.setTabSize(4);

        painelPreview.setContentType("text/html");
        painelPreview.setEditable(false);

        JScrollPane rolagemMarkdown = new JScrollPane(areaMarkdown);
        JScrollPane rolagemPreview = new JScrollPane(painelPreview);

        JSplitPane painelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rolagemMarkdown, rolagemPreview);
        painelDividido.setResizeWeight(0.5);

        add(criarBarraAcoes(), BorderLayout.NORTH);
        add(painelDividido, BorderLayout.CENTER);
        add(criarBarraStatus(), BorderLayout.SOUTH);
    }

    // --- CONFIGURA EVENTOS DE EDIÇÃO E FECHAMENTO ---
    private void configurarEventos() {
        areaMarkdown.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aoEditarDocumento();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aoEditarDocumento();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aoEditarDocumento();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmarPerdaDeAlteracoes()) {
                    dispose();
                }
            }
        });
    }

    // --- MONTA A BARRA DE AÇÕES ---
    private JToolBar criarBarraAcoes() {
        JToolBar barra = new JToolBar();
        barra.setFloatable(false);

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> novoArquivo());

        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.addActionListener(e -> abrirArquivo());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarArquivo(false));

        JButton btnSalvarComo = new JButton("Salvar como");
        btnSalvarComo.addActionListener(e -> salvarArquivo(true));

        JButton btnExportarHtml = new JButton("Exportar HTML");
        btnExportarHtml.addActionListener(e -> exportarHtml());

        JButton btnExportarPdf = new JButton("Exportar PDF");
        btnExportarPdf.addActionListener(e -> exportarPdf());

        JButton btnAtualizarPreview = new JButton("Atualizar preview");
        btnAtualizarPreview.addActionListener(e -> atualizarPreview());

        barra.add(btnNovo);
        barra.add(btnAbrir);
        barra.add(btnSalvar);
        barra.add(btnSalvarComo);
        barra.addSeparator();
        barra.add(btnExportarHtml);
        barra.add(btnExportarPdf);
        barra.addSeparator();
        barra.add(btnAtualizarPreview);

        return barra;
    }

    // --- CRIA A BARRA DE STATUS ---
    private JPanel criarBarraStatus() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(rotuloStatus, BorderLayout.WEST);
        return painel;
    }

    // --- INICIA UM NOVO DOCUMENTO ---
    private void novoArquivo() {
        if (!confirmarPerdaDeAlteracoes()) {
            return;
        }

        definirConteudoMarkdown("", null, false);
        atualizarStatus("Novo arquivo");
    }

    // --- ABRE UM ARQUIVO MARKDOWN ---
    private void abrirArquivo() {
        if (!confirmarPerdaDeAlteracoes()) {
            return;
        }

        JFileChooser seletor = criarSeletorArquivos("Arquivos Markdown", "md", "markdown", "txt");
        int resultado = seletor.showOpenDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path caminho = seletor.getSelectedFile().toPath();

        try {
            String conteudo = Files.readString(caminho, StandardCharsets.UTF_8);
            definirConteudoMarkdown(conteudo, caminho, false);
            atualizarStatus("Arquivo aberto: " + caminho.toAbsolutePath());
        } catch (IOException excecao) {
            exibirErro("Não foi possível abrir o arquivo.", excecao);
        }
    }

    // --- SALVA O CONTEÚDO MARKDOWN EM ARQUIVO ---
    private void salvarArquivo(boolean forcarEscolhaArquivo) {
        Path destino = arquivoAtual;

        if (forcarEscolhaArquivo || destino == null) {
            JFileChooser seletor = criarSeletorArquivos("Arquivos Markdown", "md", "markdown", "txt");
            int resultado = seletor.showSaveDialog(this);

            if (resultado != JFileChooser.APPROVE_OPTION) {
                return;
            }

            destino = seletor.getSelectedFile().toPath();
            destino = ajustarExtensao(destino, ".md");
        }

        try {
            Path caminhoAbsoluto = destino.toAbsolutePath();
            Path diretorio = caminhoAbsoluto.getParent();

            if (diretorio != null) {
                Files.createDirectories(diretorio);
            }

            Files.writeString(caminhoAbsoluto, areaMarkdown.getText(), StandardCharsets.UTF_8);
            arquivoAtual = caminhoAbsoluto;
            alterado = false;
            atualizarTitulo();
            atualizarStatus("Arquivo salvo: " + caminhoAbsoluto);
        } catch (IOException excecao) {
            exibirErro("Não foi possível salvar o arquivo.", excecao);
        }
    }

    // --- EXPORTA O DOCUMENTO PARA HTML ---
    private void exportarHtml() {
        JFileChooser seletor = criarSeletorArquivos("Arquivos HTML", "html", "htm");
        int resultado = seletor.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path destino = ajustarExtensao(seletor.getSelectedFile().toPath(), ".html");

        try {
            servicoMarkdown.exportarHtml(areaMarkdown.getText(), destino);
            atualizarStatus("HTML exportado: " + destino.toAbsolutePath());
        } catch (IOException excecao) {
            exibirErro("Não foi possível exportar o HTML.", excecao);
        }
    }

    // --- EXPORTA O DOCUMENTO PARA PDF ---
    private void exportarPdf() {
        JFileChooser seletor = criarSeletorArquivos("Arquivos PDF", "pdf");
        int resultado = seletor.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path destino = ajustarExtensao(seletor.getSelectedFile().toPath(), ".pdf");

        try {
            servicoMarkdown.exportarPdf(areaMarkdown.getText(), destino);
            atualizarStatus("PDF exportado: " + destino.toAbsolutePath());
        } catch (IOException excecao) {
            exibirErro("Não foi possível exportar o PDF.", excecao);
        }
    }

    // --- REGENERA O PREVIEW ---
    private void atualizarPreview() {
        String html = servicoMarkdown.criarDocumentoHtml(areaMarkdown.getText());
        painelPreview.setText(html);
        painelPreview.setCaretPosition(0);
    }

    // --- TRATA ALTERAÇÕES NO DOCUMENTO ---
    private void aoEditarDocumento() {
        if (atualizacaoInterna) {
            return;
        }

        alterado = true;
        atualizarTitulo();
        atualizarPreview();
    }

    // --- DEFINE O CONTEÚDO DA ÁREA PRINCIPAL ---
    private void definirConteudoMarkdown(String conteudo, Path caminhoArquivo, boolean alterado) {
        atualizacaoInterna = true;
        areaMarkdown.setText(conteudo == null ? "" : conteudo);
        areaMarkdown.setCaretPosition(0);
        atualizacaoInterna = false;

        this.arquivoAtual = caminhoArquivo == null ? null : caminhoArquivo.toAbsolutePath();
        this.alterado = alterado;

        atualizarPreview();
        atualizarTitulo();
    }

    // --- CONFIRMA PERDA DE ALTERAÇÕES NÃO SALVAS ---
    private boolean confirmarPerdaDeAlteracoes() {
        if (!alterado) {
            return true;
        }

        int escolha = JOptionPane.showConfirmDialog(
                this,
                "Há alterações não salvas. Deseja salvar antes de continuar?",
                "Alterações pendentes",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (escolha == JOptionPane.CANCEL_OPTION || escolha == JOptionPane.CLOSED_OPTION) {
            return false;
        }

        if (escolha == JOptionPane.YES_OPTION) {
            salvarArquivo(false);
            return !alterado;
        }

        return true;
    }

    // --- MONTA UM FILE CHOOSER COM FILTRO ---
    private JFileChooser criarSeletorArquivos(String descricao, String... extensoes) {
        JFileChooser seletor = new JFileChooser();
        seletor.setFileFilter(new FileNameExtensionFilter(descricao, extensoes));

        if (arquivoAtual != null && arquivoAtual.getParent() != null) {
            seletor.setCurrentDirectory(arquivoAtual.getParent().toFile());
        }

        return seletor;
    }

    // --- AJUSTA A EXTENSÃO DO ARQUIVO ---
    private Path ajustarExtensao(Path arquivo, String extensao) {
        String nome = arquivo.getFileName().toString();
        String extensaoNormalizada = extensao.startsWith(".") ? extensao : "." + extensao;

        if (nome.toLowerCase().endsWith(extensaoNormalizada.toLowerCase())) {
            return arquivo;
        }

        Path pai = arquivo.getParent();
        String novoNome = nome + extensaoNormalizada;
        return pai == null ? Path.of(novoNome) : pai.resolve(novoNome);
    }

    // --- ATUALIZA O TÍTULO DA JANELA ---
    private void atualizarTitulo() {
        String nomeArquivo = arquivoAtual == null ? "sem título" : arquivoAtual.getFileName().toString();
        String marcador = alterado ? " *" : "";
        setTitle("Markdown Editor - " + nomeArquivo + marcador);
    }

    // --- ATUALIZA O TEXTO DE STATUS ---
    private void atualizarStatus(String mensagem) {
        rotuloStatus.setText(mensagem);
    }

    // --- EXIBE ERRO PARA O USUÁRIO ---
    private void exibirErro(String mensagem, Exception excecao) {
        atualizarStatus(mensagem + " " + excecao.getMessage());
        JOptionPane.showMessageDialog(this, mensagem + System.lineSeparator() + excecao.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
