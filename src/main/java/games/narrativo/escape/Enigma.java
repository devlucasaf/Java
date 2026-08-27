package games.narrativo.escape;

import java.io.Serializable;

public class Enigma implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String    identificador;
    private final String    titulo;
    private final String    descricao;
    private final String    resposta;
    private final String    identificadorItemNecessario;
    private final String    identificadorItemRecompensa;
    private final String    mensagemSucesso;
    private final String    mensagemErro;
    private boolean         resolvido;

    public Enigma(String identificador, String titulo, String descricao, String resposta, String identificadorItemNecessario, String identificadorItemRecompensa, String mensagemSucesso, String mensagemErro) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("O identificador do enigma não pode estar vazio.");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do enigma não pode estar vazio.");
        }

        this.identificador = identificador;
        this.titulo = titulo;
        this.descricao = descricao == null ? "" : descricao;
        this.resposta = resposta == null ? "" : resposta.trim();
        this.identificadorItemNecessario = identificadorItemNecessario;
        this.identificadorItemRecompensa = identificadorItemRecompensa;
        this.mensagemSucesso = mensagemSucesso == null ? "O enigma foi resolvido." : mensagemSucesso;
        this.mensagemErro = mensagemErro == null ? "A resposta está incorreta." : mensagemErro;
        this.resolvido = false;
    }

    // --- VERIFICA SE O JOGADOR POSSUI O ITEM NECESSÁRIO PARA O ENIGMA ---
    public boolean possuiRequisito(Inventario inventario) {
        return identificadorItemNecessario == null || inventario.possuiItem(identificadorItemNecessario);
    }

    // --- TENTA RESOLVER O ENIGMA COM A RESPOSTA INFORMADA ---
    public boolean tentarResolver(String tentativa, Inventario inventario) {
        if (resolvido || !possuiRequisito(inventario) || tentativa == null) {
            return false;
        }

        if (resposta.equalsIgnoreCase(tentativa.trim())) {
            resolvido = true;
            return true;
        }

        return false;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIdentificadorItemNecessario() {
        return identificadorItemNecessario;
    }

    public String getIdentificadorItemRecompensa() {
        return identificadorItemRecompensa;
    }

    public String getMensagemSucesso() {
        return mensagemSucesso;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public boolean isResolvido() {
        return resolvido;
    }
}

