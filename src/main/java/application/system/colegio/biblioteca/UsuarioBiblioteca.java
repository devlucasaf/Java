package application.system.colegio.biblioteca;

public abstract class UsuarioBiblioteca extends Pessoa {
    protected String        matricula;
    protected TipoUsuario   tipo;
    protected double        multaPendente;
    protected boolean       ativo;

    public UsuarioBiblioteca(String nome, String cpf, String telefone, String email, String endereco,
                             String matricula, TipoUsuario tipo) {
        super(nome, cpf, telefone, email, endereco);
        this.matricula = matricula;
        this.tipo = tipo;
        this.multaPendente = 0.0;
        this.ativo = true;
    }

    public abstract int getPrazoEmprestimo();

    public void adicionarMulta(double valor) {
        this.multaPendente += valor;
        System.out.println("Multa de R$" + valor + " aplicada a " + nome + ". Total pendente: R$" + multaPendente);
    }

    public void pagarMulta(double valor) {
        if (valor >= multaPendente) {
            multaPendente = 0;
        } else {
            multaPendente -= valor;
        }
        System.out.println("Pagamento de R$" + valor + " realizado. Multa restante: R$" + multaPendente);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- USUÁRIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Tipo: " + tipo);
        System.out.println("Multa pendente: R$" + multaPendente);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getMatricula() {
        return matricula;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public double getMultaPendente() {
        return multaPendente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}