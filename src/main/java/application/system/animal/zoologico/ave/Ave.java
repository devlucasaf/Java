package application.system.animal.zoologico.ave;

import application.system.animal.zoologico.Animal;
import application.system.animal.zoologico.Sexo;

public abstract class Ave extends Animal {

    private final boolean podeVoar;

    public Ave(String nome, int idade) {
        this(nome, idade, Sexo.MACHO, true);
    }

    public Ave(String nome, int idade, Sexo sexo, boolean podeVoar) {
        super(nome, idade, sexo);
        this.podeVoar = podeVoar;
    }

    public void voar() {
        if (podeVoar) {
            System.out.println(getNome() + " está voando.");
        } else {
            System.out.println(getNome() + " não pode voar.");
        }
    }

    public boolean isPodeVoar() {
        return podeVoar;
    }
}
