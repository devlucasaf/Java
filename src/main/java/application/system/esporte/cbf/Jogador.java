package application.system.esporte.cbf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jogador extends Pessoa {
    private String          apelido;
    private Posicao         posicaoPrincipal;
    private int             numeroCamisa;
    private PeDominante     peDominante;
    private double          altura;
    private double          peso;
    private List<String>    lesoes;
    private Clube           clubeAtual;
    private int             golsMarcados;
    private int             cartoesAmarelos;
    private int             cartoesVermelhos;

    public Jogador(String nome, String cpf, LocalDate dataNascimento, String nacionalidade,
                    String apelido, Posicao posicaoPrincipal, int numeroCamisa,
                    PeDominante peDominante, double altura, double peso) {
        super(nome, cpf, dataNascimento, nacionalidade);
        this.apelido = apelido;
        this.posicaoPrincipal = posicaoPrincipal;
        this.numeroCamisa = numeroCamisa;
        this.peDominante = peDominante;
        this.altura = altura;
        this.peso = peso;
        this.lesoes = new ArrayList<>();
        this.golsMarcados = 0;
        this.cartoesAmarelos = 0;
        this.cartoesVermelhos = 0;
    }

    public void registrarGol() {
        golsMarcados++;
        System.out.println(nome + " fez gol! Total: " + golsMarcados);
    }

    public void receberCartaoAmarelo() {
        cartoesAmarelos++;
        System.out.println(nome + " recebeu cartão amarelo. Total: " + cartoesAmarelos);
        if (cartoesAmarelos == 3) {
            System.out.println("ATENÇÃO: " + nome + " está suspenso por acúmulo de amarelos!");
        }
    }

    public void receberCartaoVermelho() {
        cartoesVermelhos++;
        System.out.println(nome + " recebeu cartão VERMELHO! Total: " + cartoesVermelhos);
    }

    public void adicionarLesao(String lesao) {
        lesoes.add(lesao);
        System.out.println(nome + " lesionado: " + lesao);
    }

    public void transferirPara(Clube novoClube) {
        if (clubeAtual != null) {
            clubeAtual.removerJogador(this);
        }
        this.clubeAtual = novoClube;
        novoClube.adicionarJogador(this);
        System.out.println(nome + " transferido para " + novoClube.getNome());
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- JOGADOR ---");
        System.out.println("Nome: " + nome);
        System.out.println("Apelido: " + apelido);
        System.out.println("Posição: " + posicaoPrincipal);
        System.out.println("Número: " + numeroCamisa);
        System.out.println("Pé preferido: " + peDominante);
        System.out.println("Altura: " + altura + "m | Peso: " + peso + "kg");
        System.out.println("Idade: " + calcularIdade() + " anos");
        System.out.println("Gols: " + golsMarcados);
        System.out.println("Cartões: Amarelos=" + cartoesAmarelos + ", Vermelhos=" + cartoesVermelhos);

        if (clubeAtual != null) {
            System.out.println("Clube: " + clubeAtual.getNome());
        }

        if (!lesoes.isEmpty()) {
            System.out.println("Lesões: " + String.join(", ", lesoes));
        }
    }

    public Posicao getPosicaoPrincipal() {
        return posicaoPrincipal;
    }

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    public Clube getClubeAtual() {
        return clubeAtual;
    }

    public void setClubeAtual(Clube clubeAtual) {
        this.clubeAtual = clubeAtual;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }
}
