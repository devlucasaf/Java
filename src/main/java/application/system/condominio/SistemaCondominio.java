package application.system.condominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaCondominio {

    public static void main(String[] args) {
        Condominio condominio = new Condominio("Residencial Horizonte", "Rua Central, 500");

        Unidade unidade101 = condominio.cadastrarUnidade("A", "101", 1);
        Unidade unidade202 = condominio.cadastrarUnidade("B", "202", 2);

        Morador moradorJoao = condominio.cadastrarMorador(unidade101, "João da Silva", "12345678900", LocalDate.of(1985, 3, 15), "(61) 99999-1111", "joao@email.com", TipoMorador.PROPRIETARIO);
        Morador moradoraMaria = condominio.cadastrarMorador(unidade202, "Maria Souza", "98765432100", LocalDate.of(1990, 7, 20), "(61) 98888-2222", "maria@email.com", TipoMorador.INQUILINO);

        AreaComum salaoFestas = condominio.cadastrarAreaComum("Salão de festas", 60, new BigDecimal("150.00"), LocalTime.of(8, 0), LocalTime.of(23, 0));
        AreaComum churrasqueira = condominio.cadastrarAreaComum("Churrasqueira", 20, new BigDecimal("80.00"), LocalTime.of(9, 0), LocalTime.of(22, 0));

        Reserva reservaSalao = condominio.reservarArea(salaoFestas, unidade101, moradorJoao, LocalDate.now().plusDays(10), LocalTime.of(18, 0), LocalTime.of(22, 0));
        Reserva reservaChurrasqueira = condominio.reservarArea(churrasqueira, unidade202, moradoraMaria, LocalDate.now().plusDays(7), LocalTime.of(12, 0), LocalTime.of(16, 0));

        Boleto taxaCondominial = condominio.emitirBoleto(unidade101, "Taxa condominial", new BigDecimal("650.00"), LocalDate.now().plusDays(10));

        Ocorrencia ocorrencia = condominio.registrarOcorrencia(unidade202, moradoraMaria, TipoOcorrencia.BARULHO, "Barulho excessivo vindo da unidade vizinha após as 23 horas.");
        ocorrencia.iniciarAnalise();
        ocorrencia.resolver("O responsável pela unidade foi advertido.");

        Multa multa = condominio.aplicarMulta(unidade101, ocorrencia, "Descumprimento das regras de silêncio", new BigDecimal("250.00"), LocalDate.now().plusDays(15));

        taxaCondominial.pagar(taxaCondominial.calcularValorAtualizado(LocalDate.now()));

        exibirResumo(condominio, reservaSalao, reservaChurrasqueira, multa);
    }

    // --- EXIBE UM RESUMO DO SISTEMA ---
    private static void exibirResumo(Condominio condominio, Reserva primeiraReserva, Reserva segundaReserva, Multa multa) {
        System.out.println("==================================================");
        System.out.println("              GESTÃO DE CONDOMÍNIO");
        System.out.println("==================================================");
        System.out.println("Condomínio: " + condominio.getNome());
        System.out.println("Endereço: " + condominio.getEndereco());
        System.out.println("Unidades: " + condominio.getUnidades().size());
        System.out.println("Moradores: " + condominio.getMoradores().size());
        System.out.println("Áreas comuns: " + condominio.getAreasComuns().size());
        System.out.println("Reservas: " + condominio.listarReservasAtivas().size());
        System.out.println("Boletos: " + condominio.getBoletos().size());
        System.out.println("Ocorrências: " + condominio.getOcorrencias().size());
        System.out.println("Multas: " + condominio.getMultas().size());

        System.out.println("\nRESERVAS");
        System.out.println(primeiraReserva.getAreaComum().getNome() + " para " + primeiraReserva.getUnidade().getIdentificacaoCompleta());
        System.out.println(segundaReserva.getAreaComum().getNome() + " para " + segundaReserva.getUnidade().getIdentificacaoCompleta());

        System.out.println("\nMULTA");
        System.out.println("Unidade: " + multa.getUnidade().getIdentificacaoCompleta());
        System.out.println("Motivo: " + multa.getMotivo());
        System.out.println("Valor: R$ " + multa.getValor());

        System.out.println("\nTOTAL EM ABERTO");
        for (Unidade unidade : condominio.getUnidades()) {
            System.out.println(unidade.getIdentificacaoCompleta() + ": R$ " + condominio.calcularTotalEmAberto(unidade));
        }
    }
}

