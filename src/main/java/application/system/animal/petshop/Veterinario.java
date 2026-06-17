package application.system.animal.petshop;

import java.util.ArrayList;
import java.util.List;

public class Veterinario extends PessoaPetshop {
    private String          registroCRMV;
    private String          especialidade;
    private List<Servico>   consultasRealizadas;
    private double          salario;

    public Veterinario(String nome, String cpf, String telefone, String email, String endereco,
                       String registroCRMV, String especialidade, double salario) {
        super(nome, cpf, telefone, email, endereco);
        this.registroCRMV = registroCRMV;
        this.especialidade = especialidade;
        this.salario = salario;
        this.consultasRealizadas = new ArrayList<>();
    }

    public void realizarConsulta(Animal animal, String diagnostico) {
        System.out.println("Veterinário " + nome + " realizou consulta em " + animal.getNome());
        System.out.println("Diagnóstico: " + diagnostico);
    }

    public void aplicarVacina(Animal animal, String vacina) {
        System.out.println("Veterinário " + nome + " aplicou vacina " + vacina + " em " + animal.getNome());
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- VETERINÁRIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("CRMV: " + registroCRMV);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Salário: R$" + salario);
        System.out.println("Consultas realizadas: " + consultasRealizadas.size());
    }
}
