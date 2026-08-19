package application.system.colegio.biblioteca.model;

import java.time.LocalDate;

public abstract class Publicacao {
    protected String    titulo;
    protected String    editora;
    protected LocalDate anoPublicacao;
    protected int       numeroExemplares;
    protected int       exemplaresDisponiveis;
    protected boolean   ativo;

    public Publicacao(String titulo, String editora, LocalDate anoPublicacao, int numeroExemplares) {
        this.titulo = titulo;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.numeroExemplares = numeroExemplares;
        this.exemplaresDisponiveis = numeroExemplares;
        this.ativo = true;
    }

    public abstract void exibirInformacoes();

    public boolean emprestarExemplar() {
        if (exemplaresDisponiveis > 0 && ativo) {
            exemplaresDisponiveis--;
            return true;
        }
        return false;
    }

    public void devolverExemplar() {
        if (exemplaresDisponiveis < numeroExemplares) {
            exemplaresDisponiveis++;
        }
    }

    public boolean estaDisponivel() {
        return exemplaresDisponiveis > 0 && ativo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    public int getNumeroExemplares() {
        return numeroExemplares;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
