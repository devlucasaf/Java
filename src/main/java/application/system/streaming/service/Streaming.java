package application.system.streaming.service;

import application.system.streaming.model.Titulo;
import application.system.streaming.model.Usuario;

import java.util.*;

public class Streaming {

    private final Map<String, Titulo>   catalogo = new LinkedHashMap<>();
    private final Map<String, Usuario>  usuarios = new LinkedHashMap<>();

    public void adicionarTitulo(Titulo titulo) {
        catalogo.put(titulo.getId(), titulo);
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    public Titulo buscarTitulo(String id) {
        Titulo titulo = catalogo.get(id);
        if (titulo == null) {
            throw new NoSuchElementException("Titulo nao encontrado: " + id);
        }
        return titulo;
    }

    public void assistir(Usuario usuario, String idTitulo) {
        Titulo titulo = buscarTitulo(idTitulo);
        if (usuario.getIdade() < titulo.getClassificacaoIndicativa()) {
            throw new IllegalStateException(
                    usuario.getNome() + " nao tem idade suficiente para assistir " + titulo.getNome());
        }
        usuario.registrarAssistido(titulo);
        System.out.println(usuario.getNome() + " assistiu: " + titulo.getNome());
    }

    public void avaliarTitulo(String idTitulo, double nota) {
        buscarTitulo(idTitulo).avaliar(nota);
    }

    public List<Titulo> recomendarPara(Usuario usuario, int quantidade) {
        List<String> generosPreferidos = usuario.getGenerosPreferidos();

        if (generosPreferidos.isEmpty()) {
            return catalogo.values().stream()
                    .sorted((a, b) -> Double.compare(b.getNotaMedia(), a.getNotaMedia()))
                    .filter(t -> usuario.getIdade() >= t.getClassificacaoIndicativa())
                    .limit(quantidade)
                    .collect(java.util.stream.Collectors.toList());
        }

        String generoFavorito = generosPreferidos.get(0);

        List<Titulo> candidatos = new ArrayList<>();
        for (Titulo titulo : catalogo.values()) {
            boolean jaAssistido = usuario.getHistoricoAssistidos().contains(titulo.getId());
            boolean idadePermitida = usuario.getIdade() >= titulo.getClassificacaoIndicativa();
            if (!jaAssistido && idadePermitida && titulo.getGeneros().contains(generoFavorito)) {
                candidatos.add(titulo);
            }
        }

        candidatos.sort((a, b) -> Double.compare(b.getNotaMedia(), a.getNotaMedia()));

        return candidatos.size() > quantidade ? candidatos.subList(0, quantidade) : candidatos;
    }

    public Collection<Titulo> getCatalogo() {
        return catalogo.values();
    }
}
