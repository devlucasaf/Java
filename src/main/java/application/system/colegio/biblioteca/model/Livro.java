package application.system.colegio.biblioteca.model;

import application.system.colegio.biblioteca.enums.CategoriaLivro;

import java.time.LocalDate;

public class Livro extends Publicacao {
    private String          autor;
    private String          isbn;
    private int             numeroPaginas;
    private CategoriaLivro  categoria;

    public Livro(String titulo, String editora, LocalDate anoPublicacao, int numeroExemplares,
                 String autor, String isbn, int numeroPaginas, CategoriaLivro categoria) {
        super(titulo, editora, anoPublicacao, numeroExemplares);
        this.autor = autor;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.categoria = categoria;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- LIVRO ---");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("ISBN: " + isbn);
        System.out.println("Editora: " + editora);
        System.out.println("Categoria: " + categoria);
        System.out.println("Ano: " + anoPublicacao);
        System.out.println("Páginas: " + numeroPaginas);
        System.out.println("Exemplares: " + exemplaresDisponiveis + "/" + numeroExemplares);
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public CategoriaLivro getCategoria() {
        return categoria;
    }
}
