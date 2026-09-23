package application.utilitarios.musica;

import java.nio.file.Path;
import java.util.Objects;

import javafx.scene.image.Image;

public final class Musica {

    private final Path  arquivo;
    private String      titulo;
    private String      artista;
    private String      album;
    private Image       capa;

    // --- INICIALIZA A MÚSICA A PARTIR DE UM ARQUIVO DE ÁUDIO ---
    public Musica(Path arquivo) {
        this.arquivo = Objects.requireNonNull(arquivo, "O arquivo da música não pode ser nulo.").toAbsolutePath();

        titulo = obterNomeSemExtensao(arquivo);
        artista = "Artista desconhecido";
        album = "Álbum desconhecido";
    }

    // --- OBTÉM O NOME DO ARQUIVO SEM A EXTENSÃO ---
    private String obterNomeSemExtensao(Path arquivo) {
        String nomeArquivo = arquivo.getFileName().toString();
        int posicaoUltimoPonto = nomeArquivo.lastIndexOf('.');

        if (posicaoUltimoPonto > 0) {
            return nomeArquivo.substring(0, posicaoUltimoPonto);
        }

        return nomeArquivo;
    }

    // --- RETORNA O CAMINHO DO ARQUIVO DE ÁUDIO ---
    public Path obterArquivo() {
        return arquivo;
    }

    // --- RETORNA O TÍTULO DA MÚSICA ---
    public String obterTitulo() {
        return titulo;
    }

    // --- ALTERA O TÍTULO DA MÚSICA ---
    public void definirTitulo(String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            this.titulo = titulo.strip();
        }
    }

    // --- RETORNA O NOME DO ARTISTA ---
    public String obterArtista() {
        return artista;
    }

    // --- ALTERA O NOME DO ARTISTA ---
    public void definirArtista(String artista) {
        if (artista != null && !artista.isBlank()) {
            this.artista = artista.strip();
        }
    }

    // --- RETORNA O NOME DO ÁLBUM ---
    public String obterAlbum() {
        return album;
    }

    // --- ALTERA O NOME DO ÁLBUM ---
    public void definirAlbum(String album) {
        if (album != null && !album.isBlank()) {
            this.album = album.strip();
        }
    }

    // --- RETORNA A CAPA DA MÚSICA ---
    public Image obterCapa() {
        return capa;
    }

    // --- ALTERA A CAPA DA MÚSICA ---
    public void definirCapa(Image capa) {
        this.capa = capa;
    }

    // --- VERIFICA SE A MÚSICA POSSUI UMA CAPA DISPONÍVEL ---
    public boolean possuiCapa() {
        return capa != null;
    }

    // --- RETORNA O NOME DO ARQUIVO DE ÁUDIO ---
    public String obterNomeArquivo() {
        return arquivo.getFileName().toString();
    }

    // --- RETORNA O ENDEREÇO DO ARQUIVO NO FORMATO DE URI ---
    public String obterEnderecoArquivo() {
        return arquivo.toUri().toString();
    }

    // --- RETORNA A DESCRIÇÃO TEXTUAL DA MÚSICA ---
    @Override
    public String toString() {
        return titulo + " • " + artista;
    }

    // --- COMPARA DUAS MÚSICAS PELO CAMINHO DO ARQUIVO ---
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Musica outraMusica)) {
            return false;
        }

        return arquivo.equals(outraMusica.arquivo);
    }

    // --- GERA O CÓDIGO DE IDENTIFICAÇÃO DA MÚSICA ---
    @Override
    public int hashCode() {
        return arquivo.hashCode();
    }
}

