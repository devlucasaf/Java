package application.system.hospital;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String              nome;
    private String              endereco;
    private List<Funcionario>   funcionarios;
    private List<Paciente>      pacientes;
    private List<Medico>        medicos;
    private FilaTriagem         filaTriagem;

    public Hospital(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.funcionarios = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.filaTriagem = new FilaTriagem();
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

    // --- REGISTRA UM PACIENTE NA FILA DE TRIAGEM ---
    public RegistroTriagem registrarTriagem(Paciente paciente, GravidadeTriagem gravidade, List<String> sintomas, String observacoes) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo.");
        }

        if (!pacientes.contains(paciente)) {
            adicionarPaciente(paciente);
        }

        RegistroTriagem registro = filaTriagem.adicionarPaciente(paciente, gravidade, sintomas, observacoes);
        System.out.println("Paciente " + paciente.getNome() + " incluído na triagem como " + gravidade.getNomeFormatado() + ".");
        return registro;
    }

    // --- BUSCA O PRIMEIRO MÉDICO DISPONÍVEL ---
    public Medico buscarMedicoDisponivel() {
        for (Medico medico : medicos) {
            if (medico.isDisponivel()) {
                return medico;
            }
        }

        return null;
    }

    // --- BUSCA UM MÉDICO DISPONÍVEL PELA ESPECIALIDADE ---
    public Medico buscarMedicoDisponivel(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            return buscarMedicoDisponivel();
        }

        for (Medico medico : medicos) {
            if (medico.isDisponivel() && medico.getEspecialidade().equalsIgnoreCase(especialidade.trim())) {
                return medico;
            }
        }

        return null;
    }

    // --- CHAMA O PRÓXIMO PACIENTE PARA UM MÉDICO DISPONÍVEL ---
    public RegistroTriagem chamarProximoPaciente() {
        Medico medico = buscarMedicoDisponivel();

        if (medico == null) {
            System.out.println("Nenhum médico está disponível.");
            return null;
        }

        RegistroTriagem registro = filaTriagem.obterProximoPaciente();

        if (registro == null) {
            System.out.println("Não existem pacientes aguardando na triagem.");
            return null;
        }

        medico.iniciarAtendimentoTriagem(registro);
        System.out.println("Paciente " + registro.getPaciente().getNome() + " encaminhado para Dr(a). " + medico.getNome() + ".");
        return registro;
    }

    // --- CHAMA O PRÓXIMO PACIENTE PARA UM MÉDICO ESPECÍFICO ---
    public RegistroTriagem chamarProximoPaciente(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("O médico não pode ser nulo.");
        }

        if (!medicos.contains(medico)) {
            throw new IllegalArgumentException("O médico não pertence a este hospital.");
        }

        if (!medico.isDisponivel()) {
            throw new IllegalStateException("O médico informado não está disponível.");
        }

        RegistroTriagem registro = filaTriagem.obterProximoPaciente();

        if (registro == null) {
            return null;
        }

        medico.iniciarAtendimentoTriagem(registro);
        return registro;
    }

    // --- EXECUTA A RECLASSIFICAÇÃO AUTOMÁTICA DA FILA ---
    public int reclassificarFilaTriagem() {
        int quantidade = filaTriagem.reclassificarAutomaticamente();
        System.out.println(quantidade + " paciente(s) reclassificado(s) automaticamente.");
        return quantidade;
    }

    // --- EXIBE A FILA ATUAL DE TRIAGEM ---
    public void listarFilaTriagem() {
        List<RegistroTriagem> filaAtual = filaTriagem.listarPacientesAguardando();

        System.out.println("\n===== FILA DE TRIAGEM =====");

        if (filaAtual.isEmpty()) {
            System.out.println("Nenhum paciente aguardando.");
            return;
        }

        int posicao = 1;

        for (RegistroTriagem registro : filaAtual) {
            System.out.println(posicao + "º - " + registro.getPaciente().getNome());
            System.out.println("Prontuário: " + registro.getPaciente().getNumeroProntuario());
            System.out.println("Classificação: " + registro.getGravidadeAtual().getNomeFormatado());
            System.out.println("Cor: " + registro.getGravidadeAtual().getCor());
            System.out.println("Espera: " + registro.calcularTempoEsperaMinutos() + " minuto(s)");
            System.out.println("Prioridade: " + registro.calcularPontuacaoPrioridade());
            System.out.println("----------------------");
            posicao++;
        }
    }

    public FilaTriagem getFilaTriagem() {
        return filaTriagem;
    }

    public List<Medico> getMedicos() {
        return medicos;
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

