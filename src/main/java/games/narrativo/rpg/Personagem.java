package games.narrativo.rpg;

import java.util.Random;

public class Personagem {
    private String  nome;
    private String  classe;
    private int     vida;
    private int     vidaMaxima;
    private int     ataque;
    private int     defesa;
    private int     nivel;
    private int     experiencia;
    private int     ouro;

    private final Inventario inventario;
    private final Random random = new Random();

    public Personagem(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
        this.nivel = 1;
        this.experiencia = 0;
        this.ouro = 50;
        this.inventario = new Inventario();

        switch (classe.toUpperCase()) {
            case "GUERREIRO":
                this.vidaMaxima = 120;
                this.ataque = 15;
                this.defesa = 10;
                break;
            case "MAGO":
                this.vidaMaxima = 80;
                this.ataque = 25;
                this.defesa = 5;
                break;
            case "ARQUEIRO":
                this.vidaMaxima = 100;
                this.ataque = 20;
                this.defesa = 7;
                break;
            default:
                this.vidaMaxima = 100;
                this.ataque = 15;
                this.defesa = 7;
        }
        this.vida = this.vidaMaxima;
    }

    public int atacar() {
        int dano = ataque + random.nextInt(10) - 3;
        boolean critico = random.nextInt(100) < 15;
        if (critico) {
            dano *= 2;
            System.out.println("⚡ GOLPE CRÍTICO!");
        }
        return Math.max(dano, 1);
    }

    public int receberDano(int dano) {
        int danoReal = Math.max(dano - defesa - random.nextInt(5), 1);
        this.vida -= danoReal;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return danoReal;
    }

    public void curar(int quantidade) {
        this.vida = Math.min(this.vida + quantidade, this.vidaMaxima);
    }

    public void ganharExperiencia(int xp) {
        this.experiencia += xp;
        System.out.println("+" + xp + " XP ganho!");
        while (this.experiencia >= nivel * 100) {
            this.experiencia -= nivel * 100;
            subirNivel();
        }
    }

    private void subirNivel() {
        this.nivel++;
        this.vidaMaxima += 15;
        this.ataque += 3;
        this.defesa += 2;
        this.vida = this.vidaMaxima;
        System.out.println("  LEVEL UP! Agora você é nível " + nivel + "!");
        System.out.println("   Vida: " + vidaMaxima + " | Ataque: " + ataque + " | Defesa: " + defesa);
    }

    public void ganharOuro(int quantidade) {
        this.ouro += quantidade;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void mostrarStatus() {
        System.out.println("\n===== STATUS =====");
        System.out.println("Nome: " + nome + " (" + classe + ")");
        System.out.println("Nível: " + nivel + " | XP: " + experiencia + "/" + (nivel * 100));
        System.out.println("Vida: " + vida + "/" + vidaMaxima);
        System.out.println("Ataque: " + ataque + " | Defesa: " + defesa);
        System.out.println("Ouro: " + ouro);
        System.out.println("==================");
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getClasse() {
        return classe;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getNivel() {
        return nivel;
    }

    public int getOuro() {
        return ouro;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }
}

