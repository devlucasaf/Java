package application.system.streaming.model;

import java.util.*;

public class Usuario {

    private final String    id;
    private final String    nome;
    private final int       idade;
    private Plano           plano;
    private final List<String>          historicoAssistidos = new ArrayList<>();
    private final Map<String, Integer>  contagemGeneroAssistido = new HashMap<>();

    public Usuario(String id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public void assinarPlano(Plano plano) {
        this.plano = plano;
    }

    public Plano getPlano() {
        return plano;
    }

    public void registrarAssistido(Titulo titulo) {
        historicoAssistidos.add(titulo.getId());
        for (String genero : titulo.getGeneros()) {
            contagemGeneroAssistido.merge(genero, 1, Integer::sum);
        }
    }

    public List<String> getHistoricoAssistidos() {
        return historicoAssistidos;
    }

    public List<String> getGenerosPreferidos() {
        List<String> generos = new ArrayList<>(contagemGeneroAssistido.keySet());
        generos.sort((a, b) -> contagemGeneroAssistido.get(b) - contagemGeneroAssistido.get(a));
        return generos;
    }
}
