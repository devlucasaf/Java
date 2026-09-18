package games.text.debate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TemaDebate {
    private final long          identificador;
    private final String        titulo;
    private final String        descricao;
    private final List<String>  palavrasChave;
    private final List<String>  argumentosFavoraveis;
    private final List<String>  argumentosContrarios;

    public TemaDebate(long identificador, String titulo, String descricao, List<String> palavrasChave, List<String> argumentosFavoraveis, List<String> argumentosContrarios) {
        validarIdentificador(identificador);
        validarTexto(titulo, "O título");
        validarTexto(descricao, "A descrição");

        this.identificador = identificador;
        this.titulo = titulo.trim();
        this.descricao = descricao.trim();
        this.palavrasChave = normalizarLista(palavrasChave);
        this.argumentosFavoraveis = normalizarLista(argumentosFavoraveis);
        this.argumentosContrarios = normalizarLista(argumentosContrarios);
    }

    // --- RETORNA OS ARGUMENTOS COMPATÍVEIS COM O LADO ---
    public List<String> getArgumentosPorLado(LadoDebate lado) {
        if (lado == null) {
            throw new IllegalArgumentException("O lado do debate não pode ser nulo.");
        }

        return lado == LadoDebate.FAVORAVEL ? getArgumentosFavoraveis() : getArgumentosContrarios();
    }

    // --- NORMALIZA UMA LISTA DE TEXTOS ---
    private List<String> normalizarLista(List<String> valores) {
        List<String> resultado = new ArrayList<>();

        if (valores == null) {
            return resultado;
        }

        for (String valor : valores) {
            if (valor != null && !valor.trim().isEmpty()) {
                resultado.add(valor.trim());
            }
        }

        return resultado;
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

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getPalavrasChave() {
        return Collections.unmodifiableList(palavrasChave);
    }

    public List<String> getArgumentosFavoraveis() {
        return Collections.unmodifiableList(argumentosFavoraveis);
    }

    public List<String> getArgumentosContrarios() {
        return Collections.unmodifiableList(argumentosContrarios);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof TemaDebate outroTema)) {
            return false;
        }

        return identificador == outroTema.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + titulo;
    }
}

