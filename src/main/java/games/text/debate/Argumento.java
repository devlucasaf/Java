package games.text.debate;

import java.time.LocalDateTime;
import java.util.Objects;

public class Argumento {

    private final long                      identificador;
    private final String                    autor;
    private final LadoDebate                lado;
    private final EstrategiaArgumentacao    estrategia;
    private final String                    texto;
    private final LocalDateTime             dataApresentacao;

    public Argumento(long identificador, String autor, LadoDebate lado, EstrategiaArgumentacao estrategia, String texto) {
        validarIdentificador(identificador);
        validarTexto(autor, "O autor");
        validarTexto(texto, "O argumento");

        if (lado == null) {
            throw new IllegalArgumentException("O lado do argumento não pode ser nulo.");
        }

        if (estrategia == null) {
            throw new IllegalArgumentException("A estratégia não pode ser nula.");
        }

        this.identificador = identificador;
        this.autor = autor.trim();
        this.lado = lado;
        this.estrategia = estrategia;
        this.texto = texto.trim();
        this.dataApresentacao = LocalDateTime.now();
    }

    // --- RETORNA A QUANTIDADE APROXIMADA DE PALAVRAS ---
    public int contarPalavras() {
        if (texto.isBlank()) {
            return 0;
        }

        return texto.split("\\s+").length;
    }

    // --- VALIDA O IDENTIFICADOR ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getAutor() {
        return autor;
    }

    public LadoDebate getLado() {
        return lado;
    }

    public EstrategiaArgumentacao getEstrategia() {
        return estrategia;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getDataApresentacao() {
        return dataApresentacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Argumento outroArgumento)) {
            return false;
        }

        return identificador == outroArgumento.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return autor + ": " + texto;
    }
}

