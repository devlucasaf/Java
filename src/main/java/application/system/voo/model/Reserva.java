package application.system.voo.model;

import application.system.voo.enums.ClasseAssento;
import application.system.voo.enums.FormaPagamento;
import application.system.voo.enums.StatusReserva;

import java.time.LocalDate;

public class Reserva {
    private static int contadorId = 1;
    private int             id;
    private Voo             voo;
    private Passageiro      passageiro;
    private ClasseAssento   classeAssento;
    private LocalDate       dataReserva;
    private StatusReserva   status;
    private double          precoPago;
    private FormaPagamento  formaPagamento;

    public Reserva(Voo voo, Passageiro passageiro, ClasseAssento classeAssento,
                   FormaPagamento formaPagamento, double precoPago) {
        this.id = contadorId++;
        this.voo = voo;
        this.passageiro = passageiro;
        this.classeAssento = classeAssento;
        this.dataReserva = LocalDate.now();
        this.status = StatusReserva.CONFIRMADA;
        this.precoPago = precoPago;
        this.formaPagamento = formaPagamento;
    }

    public void cancelar() {
        if (status == StatusReserva.CONFIRMADA || status == StatusReserva.PENDENTE) {
            this.status = StatusReserva.CANCELADA;
            this.voo.liberarLugar();
            System.out.println("Reserva #" + id + " cancelada.");
        } else {
            System.out.println("Reserva #" + id + " não pode ser cancelada (status atual: " + status + ").");
        }
    }

    public void realizar() {
        this.status = StatusReserva.REALIZADA;
    }

    public void exibirInformacoes() {
        System.out.println("--- RESERVA #" + id + " ---");
        System.out.println("Voo: " + voo.getId() + " - " + voo.getOrigem() + " → " + voo.getDestino());
        System.out.println("Passageiro: " + passageiro.getNome() + " (CPF: " + passageiro.getCpf() + ")");
        System.out.println("Classe: " + classeAssento);
        System.out.println("Data da reserva: " + dataReserva);
        System.out.println("Status: " + status);
        System.out.println("Preço pago: R$" + precoPago);
        System.out.println("Forma de pagamento: " + formaPagamento);
    }

    public int getId() {
        return id;
    }

    public Voo getVoo() {
        return voo;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public ClasseAssento getClasseAssento() {
        return classeAssento;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public double getPrecoPago() {
        return precoPago;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

}
