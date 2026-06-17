package application.system.hospital;

import java.time.LocalDate;

public class Administrativo extends Funcionario {
    private String  setor;
    private int     nivelAcesso;

    public Administrativo(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco,
                          String matricula, double salarioBase, LocalDate dataAdmissao, String cargo,
                          String setor, int nivelAcesso) {
        super(nome, cpf, dataNascimento, telefone, endereco, matricula, salarioBase, dataAdmissao, cargo);
        this.setor = setor;
        this.nivelAcesso = nivelAcesso;
    }

    public void processarDocumento(String documento) {
        System.out.println("Administrativo " + nome + " processou documento: " + documento);
    }

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDate data) {
        System.out.println("Consulta agendada para " + paciente.getNome() + " com Dr(a). " + medico.getNome() + " em " + data);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Setor adm: " + setor);
        System.out.println("Nível de Acesso: " + nivelAcesso);
    }

    public String getSetor() {
        return setor;
    }
}
