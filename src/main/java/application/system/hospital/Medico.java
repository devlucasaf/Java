package application.system.hospital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Funcionario {
    private String          crm;
    private String          especialidade;
    private List<Paciente>  pacientesAtendidos;
    private double          valorConsulta;
    private boolean         disponivel;
    private RegistroTriagem atendimentoAtual;

    public Medico(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco,
                  String matricula, double salarioBase, LocalDate dataAdmissao, String cargo,
                  String crm, String especialidade, double valorConsulta) {
        super(nome, cpf, dataNascimento, telefone, endereco, matricula, salarioBase, dataAdmissao, cargo);
        this.crm = crm;
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.pacientesAtendidos = new ArrayList<>();
        this.disponivel = true;
        this.atendimentoAtual = null;
    }

    @Override
    public double calcularSalario() {
        double bonus = pacientesAtendidos.size() * (valorConsulta * 0.05);
        return salarioBase + bonus;
    }

    public void atenderPaciente(Paciente paciente) {
        if (paciente != null && !pacientesAtendidos.contains(paciente)) {
            pacientesAtendidos.add(paciente);
            System.out.println("Médico " + nome + " atendeu paciente " + paciente.getNome());
        } else {
            System.out.println("Paciente já foi atendido por este médico ou inválido.");
        }
    }

    public void realizarCirurgia() {
        System.out.println("Médico " + nome + " está realizando uma cirurgia.");
    }

    public void emitirReceita(Paciente paciente, String medicamento) {
        System.out.println("Receita emitida para " + paciente.getNome() + ": " + medicamento);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("CRM: " + crm);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Valor Consulta: R$" + valorConsulta);
        System.out.println("Pacientes atendidos: " + pacientesAtendidos.size());
    }

    // --- VERIFICA SE O MÉDICO ESTÁ DISPONÍVEL PARA ATENDIMENTO ---
    public boolean isDisponivel() {
        return disponivel && ativo && atendimentoAtual == null;
    }

    // --- ALTERA MANUALMENTE A DISPONIBILIDADE DO MÉDICO ---
    public void setDisponivel(boolean disponivel) {
        if (atendimentoAtual != null && disponivel) {
            throw new IllegalStateException("O médico não pode ser disponibilizado enquanto possui um atendimento ativo.");
        }

        this.disponivel = disponivel;
    }

    // --- INICIA UM ATENDIMENTO DE TRIAGEM ---
    public void iniciarAtendimentoTriagem(RegistroTriagem registroTriagem) {
        if (registroTriagem == null) {
            throw new IllegalArgumentException("O registro de triagem não pode ser nulo.");
        }

        if (!isDisponivel()) {
            throw new IllegalStateException("O médico não está disponível.");
        }

        this.atendimentoAtual = registroTriagem;
        this.disponivel = false;
        registroTriagem.iniciarAtendimento(this);
    }

    // --- FINALIZA O ATENDIMENTO DE TRIAGEM ATUAL ---
    public RegistroTriagem finalizarAtendimentoTriagem() {
        if (atendimentoAtual == null) {
            throw new IllegalStateException("O médico não possui um atendimento ativo.");
        }

        RegistroTriagem registroFinalizado = atendimentoAtual;
        registroFinalizado.finalizarAtendimento();
        atenderPaciente(registroFinalizado.getPaciente());

        this.atendimentoAtual = null;
        this.disponivel = true;

        return registroFinalizado;
    }

    // --- INTERROMPE O ATENDIMENTO E DEVOLVE O PACIENTE À FILA ---
    public RegistroTriagem devolverPacienteParaFila(String motivo) {
        if (atendimentoAtual == null) {
            throw new IllegalStateException("O médico não possui um atendimento ativo.");
        }

        RegistroTriagem registroDevolvido = atendimentoAtual;
        registroDevolvido.retornarParaFila(motivo);

        this.atendimentoAtual = null;
        this.disponivel = true;

        return registroDevolvido;
    }

    public RegistroTriagem getAtendimentoAtual() {
        return atendimentoAtual;
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<Paciente> getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
}
