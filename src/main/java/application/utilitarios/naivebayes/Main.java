package application.utilitarios.naivebayes;

public class Main {

    public static void main(String[] args) {
        NaiveBayes nb = new NaiveBayes();

        nb.treinar("compre agora oferta imperdivel promocao ganhe premio dinheiro gratis", "spam");
        nb.treinar("oferta exclusiva clique aqui premio milionario ganhe agora", "spam");
        nb.treinar("promocao imperdivel produto barato oferta relampago gratis", "spam");
        nb.treinar("ganhe dinheiro facil trabalhando em casa sem esforco premio", "spam");
        nb.treinar("clique no link e ganhe premio credito gratis dinheiro", "spam");

        nb.treinar("reuniao amanha as 10 horas na sala de conferencia", "normal");
        nb.treinar("segue em anexo relatorio trimestral do departamento", "normal");
        nb.treinar("por favor confirme presenca no evento da empresa", "normal");
        nb.treinar("relatorio de vendas do mes fechou dentro da meta", "normal");
        nb.treinar("bom dia equipe segue agenda da semana com prioridades", "normal");

        System.out.println("=== CLASSIFICADOR DE SPAM ===\n");

        String[] testes = {
            "reuniao urgente com o time amanha cedo",
            "ganhe premio agora clique aqui e receba dinheiro gratis",
            "segue anexo o relatorio final do projeto",
            "oferta exclusiva promocao imperdivel apenas hoje"
        };

        for (String t : testes) {
            String c = nb.classificar(t);
            var probs = nb.probabilidades(t);
            System.out.println("Texto: " + t);
            System.out.println("  Classe: " + c);
            probs.forEach((k, v) -> System.out.printf("    %s: %.2f%%%n", k, v * 100));
            System.out.println();
        }
    }
}

