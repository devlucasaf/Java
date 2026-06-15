package org.application.system.hospital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private String          numeroProntuario;
    private String          convenio;
    private List<String>    historicoDoencas;
    private EstadoPaciente  estado;
    private TipoSangue      tipoSangue;
    private Medico          medicoResponsavel;

    public Paciente(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco,
                    String numeroProntuario, String convenio, TipoSangue tipoSangue) {
        super(nome, cpf, dataNascimento, telefone, endereco);
        this.numeroProntuario = numeroProntuario;
        this.convenio = convenio;
        this.tipoSangue = tipoSangue;
        this.historicoDoencas = new ArrayList<>();
        this.estado = EstadoPaciente.AMBULATORIO;
    }

    public void adicionarDoenca(String doenca) {
        historicoDoencas.add(doenca);
        System.out.println("Doença '" + doenca + "' adicionada ao histórico de " + nome);
    }

    public void internar() {
        this.estado = EstadoPaciente.INTERNADO;
        System.out.println("Paciente " + nome + " foi internado.");
    }

    public void receberAlta() {
        this.estado = EstadoPaciente.ALTA;
        System.out.println("Paciente " + nome + " recebeu alta.");
    }

    public void associarMedico(Medico medico) {
        this.medicoResponsavel = medico;
        System.out.println("Médico responsável por " + nome + " é Dr(a). " + medico.getNome());
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- Paciente ---");
        System.out.println("Nome: " + nome);
        System.out.println("Prontuário: " + numeroProntuario);
        System.out.println("Convênio: " + convenio);
        System.out.println("Estado: " + estado);
        System.out.println("Tipo Sanguíneo: " + tipoSangue);
        System.out.println("Idade: " + calcularIdade() + " anos");
        System.out.println("Histórico de doenças: " + (historicoDoencas.isEmpty() ? "Nenhum" : String.join(", ", historicoDoencas)));

        if (medicoResponsavel != null) {
            System.out.println("Médico responsável: " + medicoResponsavel.getNome());
        }
    }

    public String getNumeroProntuario() {
        return numeroProntuario;
    }

    public String getConvenio() {
        return convenio;
    }

    public EstadoPaciente getEstado() {
        return estado;
    }

    public TipoSangue getTipoSangue() {
        return tipoSangue;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public List<String> getHistoricoDoencas() {
        return historicoDoencas;
    }
}
