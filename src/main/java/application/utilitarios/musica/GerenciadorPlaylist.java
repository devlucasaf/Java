package application.utilitarios.musica;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class GerenciadorPlaylist {

    private final ObservableList<Musica>    musicas;
    private final ObservableList<Musica>    musicasSomenteLeitura;
    private int                             indiceMusicaAtual;

    // --- INICIALIZA O GERENCIADOR DA LISTA DE REPRODUÇÃO ---
    public GerenciadorPlaylist() {
        musicas = FXCollections.observableArrayList();
        musicasSomenteLeitura = FXCollections.unmodifiableObservableList(musicas);
        indiceMusicaAtual = -1;
    }

    // --- ADICIONA UMA MÚSICA A PARTIR DO CAMINHO DO ARQUIVO ---
    public boolean adicionarMusica(Path arquivo) {
        Objects.requireNonNull(arquivo, "O caminho do arquivo não pode ser nulo.");
        return adicionarMusica(new Musica(arquivo));
    }

    // --- ADICIONA UMA MÚSICA À LISTA DE REPRODUÇÃO ---
    public boolean adicionarMusica(Musica musica) {
        Objects.requireNonNull(musica, "A música não pode ser nula.");

        if (musicas.contains(musica)) {
            return false;
        }

        boolean musicaAdicionada = musicas.add(musica);

        if (musicaAdicionada && indiceMusicaAtual < 0) {
            indiceMusicaAtual = 0;
        }

        return musicaAdicionada;
    }

    // --- ADICIONA VÁRIOS ARQUIVOS À LISTA DE REPRODUÇÃO ---
    public int adicionarMusicas(Collection<Path> arquivos) {
        Objects.requireNonNull(arquivos, "A coleção de arquivos não pode ser nula.");

        int quantidadeAdicionada = 0;

        for (Path arquivo : arquivos) {
            if (arquivo != null && adicionarMusica(arquivo)) {
                quantidadeAdicionada++;
            }
        }

        return quantidadeAdicionada;
    }

    // --- REMOVE UMA MÚSICA DA LISTA DE REPRODUÇÃO ---
    public boolean removerMusica(Musica musica) {
        if (musica == null) {
            return false;
        }

        int indiceRemovido = musicas.indexOf(musica);

        if (indiceRemovido < 0) {
            return false;
        }

        musicas.remove(indiceRemovido);
        ajustarIndiceAposRemocao(indiceRemovido);
        return true;
    }

    // --- REMOVE UMA MÚSICA PELO ÍNDICE ---
    public Musica removerMusica(int indice) {
        if (!indiceValido(indice)) {
            return null;
        }

        Musica musicaRemovida = musicas.remove(indice);
        ajustarIndiceAposRemocao(indice);
        return musicaRemovida;
    }

    // --- AJUSTA A SELEÇÃO APÓS A REMOÇÃO DE UMA MÚSICA ---
    private void ajustarIndiceAposRemocao(int indiceRemovido) {
        if (musicas.isEmpty()) {
            indiceMusicaAtual = -1;
            return;
        }

        if (indiceRemovido < indiceMusicaAtual) {
            indiceMusicaAtual--;
            return;
        }

        if (indiceMusicaAtual >= musicas.size()) {
            indiceMusicaAtual = musicas.size() - 1;
        }
    }

    // --- REMOVE TODAS AS MÚSICAS DA LISTA DE REPRODUÇÃO ---
    public void limparPlaylist() {
        musicas.clear();
        indiceMusicaAtual = -1;
    }

    // --- SELECIONA UMA MÚSICA PELO ÍNDICE ---
    public Musica selecionarMusica(int indice) {
        if (!indiceValido(indice)) {
            return null;
        }

        indiceMusicaAtual = indice;
        return obterMusicaAtual();
    }

    // --- SELECIONA UMA MÚSICA PELO OBJETO ---
    public Musica selecionarMusica(Musica musica) {
        if (musica == null) {
            return null;
        }

        return selecionarMusica(musicas.indexOf(musica));
    }

    // --- RETORNA A MÚSICA ATUAL ---
    public Musica obterMusicaAtual() {
        if (!indiceValido(indiceMusicaAtual)) {
            return null;
        }

        return musicas.get(indiceMusicaAtual);
    }

    // --- RETORNA A PRÓXIMA MÚSICA DA LISTA ---
    public Musica obterProximaMusica() {
        if (musicas.isEmpty()) {
            indiceMusicaAtual = -1;
            return null;
        }

        if (indiceMusicaAtual < 0) {
            indiceMusicaAtual = 0;
        } else {
            indiceMusicaAtual = (indiceMusicaAtual + 1) % musicas.size();
        }

        return obterMusicaAtual();
    }

    // --- RETORNA A MÚSICA ANTERIOR DA LISTA ---
    public Musica obterMusicaAnterior() {
        if (musicas.isEmpty()) {
            indiceMusicaAtual = -1;
            return null;
        }

        if (indiceMusicaAtual < 0) {
            indiceMusicaAtual = 0;
        } else {
            indiceMusicaAtual =
                    (indiceMusicaAtual - 1 + musicas.size()) % musicas.size();
        }

        return obterMusicaAtual();
    }

    // --- RETORNA UMA MÚSICA PELO ÍNDICE ---
    public Musica obterMusica(int indice) {
        if (!indiceValido(indice)) {
            return null;
        }

        return musicas.get(indice);
    }

    // --- LOCALIZA O ÍNDICE DE UMA MÚSICA ---
    public int obterIndice(Musica musica) {
        if (musica == null) {
            return -1;
        }

        return musicas.indexOf(musica);
    }

    // --- RETORNA O ÍNDICE DA MÚSICA ATUAL ---
    public int obterIndiceMusicaAtual() {
        return indiceMusicaAtual;
    }

    // --- RETORNA A LISTA OBSERVÁVEL SOMENTE PARA LEITURA ---
    public ObservableList<Musica> obterMusicas() {
        return musicasSomenteLeitura;
    }

    // --- RETORNA A QUANTIDADE DE MÚSICAS DA LISTA ---
    public int obterQuantidadeMusicas() {
        return musicas.size();
    }

    // --- VERIFICA SE A LISTA DE REPRODUÇÃO ESTÁ VAZIA ---
    public boolean estaVazia() {
        return musicas.isEmpty();
    }

    // --- VERIFICA SE UMA MÚSICA ESTÁ PRESENTE NA LISTA ---
    public boolean contemMusica(Musica musica) {
        return musica != null && musicas.contains(musica);
    }

    // --- VERIFICA SE UM ÍNDICE É VÁLIDO ---
    private boolean indiceValido(int indice) {
        return indice >= 0 && indice < musicas.size();
    }
}
