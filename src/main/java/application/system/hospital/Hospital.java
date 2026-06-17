package application.system.hospital;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String              nome;
    private String              endereco;
    private List<Funcionario>   funcionarios;
    private List<Paciente>      pacientes;
    private List<Medico>        medicos;

    public Hospital(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.funcionarios = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
    }

    public void adicionarFuncionario(Funcionario f) {
        funcionarios.add(f);
        if (f instanceof Medico) {
            medicos.add((Medico) f);
        }
        System.out.println("Funcionário " + f.getNome() + " adicionado ao hospital.");
    }

    public void adicionarPaciente(Paciente p) {
        pacientes.add(p);
        System.out.println("Paciente " + p.getNome() + " registrado no hospital.");
    }

    public void listarTodosPacientes() {
        System.out.println("\n===== LISTA DE PACIENTES =====");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Paciente p : pacientes) {
                p.exibirInformacoes();
                System.out.println("----------------------");
            }
        }
    }

    public void listarFuncionariosPorTipo() {
        System.out.println("\n===== FUNCIONÁRIOS DO HOSPITAL =====");
        System.out.println("--- Médicos ---");
        for (Funcionario f : funcionarios) {
            if (f instanceof Medico) {
                System.out.println(f.getNome() + " - " + ((Medico) f).getEspecialidade());
            }
        }
        System.out.println("--- Enfermeiros ---");
        for (Funcionario f : funcionarios) {
            if (f instanceof Enfermeiro) {
                Enfermeiro e = (Enfermeiro) f;
                System.out.println(e.getNome() + " - Setor: " + e.getSetor());
            }
        }
        System.out.println("--- Administrativos ---");
        for (Funcionario f : funcionarios) {
            if (f instanceof Administrativo) {
                System.out.println(f.getNome() + " - Setor: " + ((Administrativo) f).getSetor());
            }
        }
    }

    public void calcularFolhaPagamento() {
        double total = 0;
        System.out.println("\n===== FOLHA DE PAGAMENTO =====");
        for (Funcionario f : funcionarios) {
            double salario = f.calcularSalario();
            total += salario;
            System.out.println(f.getNome() + " (" + f.getCargo() + "): R$" + String.format("%.2f", salario));
        }
        System.out.println("TOTAL: R$" + String.format("%.2f", total));
    }

    public void listarMedicosPorEspecialidade(String especialidade) {
        System.out.println("\nMédicos com especialidade: " + especialidade);
        boolean encontrou = false;
        for (Medico m : medicos) {
            if (m.getEspecialidade().equalsIgnoreCase(especialidade)) {
                System.out.println("- Dr(a). " + m.getNome() + " (CRM: " + m.getCrm() + ")");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum médico encontrado com essa especialidade.");
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
