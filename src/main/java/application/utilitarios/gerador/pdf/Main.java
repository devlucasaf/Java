package application.utilitarios.gerador.pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws IOException {
        Path saida = Path.of("target", "relatorio-demo.pdf");
        saida.getParent().toFile().mkdirs();

        new GeradorPDF()
                .titulo("Relatorio Mensal de Vendas")
                .adicionar("Data: " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .adicionar("Empresa: Exemplo LTDA")
                .pular()
                .cabecalho("Resumo do periodo")
                .adicionar("Total de pedidos: 342")
                .adicionar("Faturamento bruto: R$ 128.450,00")
                .adicionar("Ticket medio: R$ 375,58")
                .pular()
                .cabecalho("Categorias em destaque")
                .adicionar("1. Eletronicos - R$ 52.300,00 (40,7%)")
                .adicionar("2. Vestuario  - R$ 34.100,00 (26,5%)")
                .adicionar("3. Livros     - R$ 21.700,00 (16,9%)")
                .adicionar("4. Outros     - R$ 20.350,00 (15,9%)")
                .pular()
                .cabecalho("Observacoes")
                .adicionar("Crescimento de 12,3% em relacao ao mes anterior.")
                .adicionar("Meta trimestral atingida em 87%.")
                .salvar(saida);

        System.out.println("PDF gerado em: " + saida.toAbsolutePath());
        System.out.println("Abra o arquivo em qualquer leitor de PDF.");
    }
}

