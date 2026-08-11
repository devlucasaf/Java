package games.plataforma.minigames.jogos.capitais.model;

import games.plataforma.minigames.util.GeradorAleatorio;
import java.util.*;

public class Capitais {
    private Map<String, String> paisesCapitais;
    private List<String>        listaPaises;
    private String              paisAtual;
    private String              capitalCorreta;
    private int                 acertos;
    private int                 erros;
    private boolean             finalizado;
    private List<String>        opcoes;

    public Capitais() {
        carregarDados();
        reiniciar();
    }

    private void carregarDados() {
        paisesCapitais = new HashMap<>();
        paisesCapitais.put("Brasil", "Brasília");
        paisesCapitais.put("Argentina", "Buenos Aires");
        paisesCapitais.put("Chile", "Santiago");
        paisesCapitais.put("Colômbia", "Bogotá");
        paisesCapitais.put("Peru", "Lima");
        paisesCapitais.put("Venezuela", "Caracas");
        paisesCapitais.put("Equador", "Quito");
        paisesCapitais.put("Bolívia", "Sucre");
        paisesCapitais.put("Paraguai", "Assunção");
        paisesCapitais.put("Uruguai", "Montevidéu");
        paisesCapitais.put("Guiana", "Georgetown");
        paisesCapitais.put("Suriname", "Paramaribo");
        paisesCapitais.put("França", "Paris");
        paisesCapitais.put("Espanha", "Madri");
        paisesCapitais.put("Portugal", "Lisboa");
        paisesCapitais.put("Itália", "Roma");
        paisesCapitais.put("Alemanha", "Berlim");
        paisesCapitais.put("Inglaterra", "Londres");
        paisesCapitais.put("Rússia", "Moscou");
        paisesCapitais.put("China", "Pequim");
        paisesCapitais.put("Japão", "Tóquio");
        paisesCapitais.put("Índia", "Nova Deli");
        paisesCapitais.put("Estados Unidos", "Washington");
        paisesCapitais.put("Canadá", "Ottawa");
        paisesCapitais.put("México", "Cidade do México");
        paisesCapitais.put("África do Sul", "Pretória");
        paisesCapitais.put("Egito", "Cairo");
        paisesCapitais.put("Nigéria", "Abuja");
        paisesCapitais.put("Austrália", "Camberra");
        paisesCapitais.put("Nova Zelândia", "Wellington");
        paisesCapitais.put("Coreia do Sul", "Seul");
        paisesCapitais.put("Indonésia", "Jacarta");
        paisesCapitais.put("Paquistão", "Islamabad");
        paisesCapitais.put("Bangladesh", "Daca");
        paisesCapitais.put("Turquia", "Ancara");
        paisesCapitais.put("Arábia Saudita", "Riade");
        paisesCapitais.put("Irã", "Teerã");
        paisesCapitais.put("Iraque", "Bagdá");
        paisesCapitais.put("Israel", "Jerusalém");
        paisesCapitais.put("Suécia", "Estocolmo");
        paisesCapitais.put("Noruega", "Oslo");
        paisesCapitais.put("Dinamarca", "Copenhague");
        paisesCapitais.put("Holanda", "Amsterdã");
        paisesCapitais.put("Bélgica", "Bruxelas");
        paisesCapitais.put("Suíça", "Berna");
        paisesCapitais.put("Áustria", "Viena");
        paisesCapitais.put("Grécia", "Atenas");
        paisesCapitais.put("Portugal", "Lisboa");
        paisesCapitais.put("Irlanda", "Dublin");
        paisesCapitais.put("Polônia", "Varsóvia");
        paisesCapitais.put("Ucrânia", "Kiev");

        listaPaises = new ArrayList<>(paisesCapitais.keySet());
    }

    public void reiniciar() {
        acertos = 0;
        erros = 0;
        finalizado = false;
        novaPergunta();
    }

    public void novaPergunta() {
        if (listaPaises.isEmpty()) {
            return;
        }
        paisAtual = GeradorAleatorio.escolher(listaPaises);
        capitalCorreta = paisesCapitais.get(paisAtual);
        opcoes = new ArrayList<>();
        opcoes.add(capitalCorreta);
        List<String> todasCapitais = new ArrayList<>(paisesCapitais.values());
        while (opcoes.size() < 4) {
            String cap = GeradorAleatorio.escolher(todasCapitais);
            if (!opcoes.contains(cap)) {
                opcoes.add(cap);
            }
        }
        Collections.shuffle(opcoes);
    }

    public boolean responder(String resposta) {
        if (finalizado) {
            return false;
        }

        if (resposta.equalsIgnoreCase(capitalCorreta)) {
            acertos++;
            return true;
        } else {
            erros++;
            return false;
        }
    }

    public String getPaisAtual() {
        return paisAtual;
    }

    public String getCapitalCorreta() {
        return capitalCorreta;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public int getAcertos() {
        return acertos;
    }

    public int getErros() {
        return erros;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
}
