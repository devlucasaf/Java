package games.text.debate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteligenciaArtificialDebate {

    private final Random        random;
    private final List<String>  argumentosUtilizados;

    public InteligenciaArtificialDebate() {
        this.random = new Random();
        this.argumentosUtilizados = new ArrayList<>();
    }

    // --- CRIA UM ARGUMENTO PARA A INTELIGÊNCIA ARTIFICIAL ---
    public String criarArgumento(TemaDebate tema, LadoDebate lado, EstrategiaArgumentacao estrategia, String argumentoAnterior) {
        if (tema == null || lado == null || estrategia == null) {
            throw new IllegalArgumentException("O tema, o lado e a estratégia devem ser informados.");
        }

        String argumentoBase = escolherArgumentoBase(tema.getArgumentosPorLado(lado));
        return aplicarEstrategia(argumentoBase, estrategia, argumentoAnterior);
    }

    // --- ESCOLHE UM ARGUMENTO AINDA NÃO UTILIZADO ---
    private String escolherArgumentoBase(List<String> argumentos) {
        if (argumentos.isEmpty()) {
            return "A questão precisa ser observada por diferentes perspectivas antes que uma conclusão definitiva seja estabelecida.";
        }

        List<String> disponiveis = argumentos.stream().filter(argumento -> !argumentosUtilizados.contains(argumento)).toList();

        if (disponiveis.isEmpty()) {
            argumentosUtilizados.clear();
            disponiveis = argumentos;
        }

        String escolhido = disponiveis.get(random.nextInt(disponiveis.size()));
        argumentosUtilizados.add(escolhido);
        return escolhido;
    }

    // --- ADAPTA O ARGUMENTO À ESTRATÉGIA SELECIONADA ---
    private String aplicarEstrategia(String argumentoBase, EstrategiaArgumentacao estrategia, String argumentoAnterior) {
        return switch (estrategia) {
            case CAUSA_E_EFEITO -> argumentoBase + " Isso ocorre porque decisões bem planejadas produzem consequências mais previsíveis e sustentáveis.";
            case EXEMPLO_PRATICO -> "Como exemplo prático, podemos observar situações em que medidas semelhantes produziram resultados mensuráveis. " + argumentoBase;
            case COMPARACAO -> "Ao comparar soluções tradicionais com abordagens alternativas, percebemos diferenças importantes. " + argumentoBase;
            case REFUTACAO -> criarRefutacao(argumentoBase, argumentoAnterior);
            case PROPOSTA_SOLUCAO -> argumentoBase + " Uma solução possível seria implementar a proposta gradualmente, avaliando seus resultados e corrigindo possíveis falhas.";
            case PRINCIPIO_GERAL -> "Com base nos princípios de responsabilidade, equilíbrio e benefício coletivo, " + iniciarComMinuscula(argumentoBase);
        };
    }

    // --- CRIA UMA REFUTAÇÃO AO ARGUMENTO ANTERIOR ---
    private String criarRefutacao(String argumentoBase, String argumentoAnterior) {
        if (argumentoAnterior == null || argumentoAnterior.isBlank()) {
            return "Embora existam opiniões contrárias, elas não eliminam os benefícios apresentados. " + argumentoBase;
        }

        return "O argumento anterior apresenta uma perspectiva relevante, contudo sua conclusão é insuficiente porque não considera todos os efeitos envolvidos. " + argumentoBase;
    }

    // --- CONVERTE O PRIMEIRO CARACTERE PARA MINÚSCULO ---
    private String iniciarComMinuscula(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        return Character.toLowerCase(texto.charAt(0)) + texto.substring(1);
    }

    // --- LIMPA O HISTÓRICO DE ARGUMENTOS UTILIZADOS ---
    public void reiniciar() {
        argumentosUtilizados.clear();
    }
}

