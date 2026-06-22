package application.system.animal.zoologico;

public abstract class Animal {

    private final String    nome;
    private final int       idade;
    private final Sexo      sexo;
    private boolean         acordado;

    public Animal(String nome, int idade) {
        this(nome, idade, Sexo.MACHO);
    }

    public Animal(String nome, int idade, Sexo sexo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do animal nao pode ser vazio.");
        }

        if (idade < 0) {
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        }
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo == null ? Sexo.MACHO : sexo;
        this.acordado = true;
    }

    public abstract void emitirSom();

    public void dormir() {
        if (!acordado) {
            System.out.println(nome + " ja esta dormindo.");
            return;
        }
        acordado = false;
        System.out.println(nome + " esta dormindo.");
    }

    public void acordar() {
        if (acordado) {
            System.out.println(nome + " ja esta acordado.");
            return;
        }
        acordado = true;
        System.out.println(nome + " acordou.");
    }

    public void alimentar() {
        System.out.println(nome + " esta se alimentando.");
    }

    public void apresentar() {
        System.out.println("Eu sou " + nome + ", tenho " + idade
                + " ano(s), sexo " + sexo.getDescricao() + ".");
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public boolean isAcordado() {
        return acordado;
    }

    public String getTipo() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getTipo() + " [" + nome + ", " + idade + " ano(s), " + sexo.getDescricao() + "]";
    }
}
