package org.application.system.eventos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SistemaEventosArtistas {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar artistas
        Cantor caetano = new Cantor("Caetano Veloso", "Caetano", LocalDate.parse("07/08/1942", formatter),
                "Brasileiro", 50000.0, "Tenor", false);
        Cantor anitta = new Cantor("Anitta", "Anitta", LocalDate.parse("30/03/1993", formatter),
                "Brasileira", 80000.0, "Soprano", true);

        Guitarrista steve = new Guitarrista("Steve Vai", "Steve Vai", LocalDate.parse("06/06/1960", formatter),
                "Americano", 40000.0, "Guitarra", 6, "Ibanez JEM");
        Baixista jaco = new Baixista("Jaco Pastorius", "Jaco", LocalDate.parse("01/12/1951", formatter),
                "Americano", 35000.0, "Baixo", true, 24);
        Baterista neil = new Baterista("Neil Peart", "Neil Peart", LocalDate.parse("12/09/1952", formatter),
                "Canadense", 38000.0, "Bateria", 10, true);

        // Criar banda
        Banda bandaRock = new Banda("Os Trovões", LocalDate.parse("15/05/2010", formatter), EstiloMusical.ROCK, 120000.0);
        bandaRock.adicionarMembro(steve);
        bandaRock.adicionarMembro(jaco);
        bandaRock.adicionarMembro(neil);

        // Empresa organizadora
        EmpresaResponsavel empresa = new EmpresaResponsavel("Eventos Musicais Ltda", "12.345.678/0001-99",
                "Av. das Artes, 2000", "(11) 3333-4444");

        // Criar local do evento
        LocalEvento localEvento = new LocalEvento(
                TipoLocal.ESTADIO,
                "Brasil",
                "São Paulo",
                "São Paulo",
                "Av. Roberto Marinho, 100 - Morumbi",
                50000
        );

        // Criar evento
        Evento festival = new Evento("Festival de Verão", TipoEvento.FESTIVAL,
                LocalDate.parse("15/12/2025", formatter), localEvento, 500000.0, empresa);
        empresa.adicionarEvento(festival);

        // Contratar artistas e banda
        festival.contratarArtista(caetano);
        festival.contratarArtista(anitta);
        festival.contratarBanda(bandaRock);

        // Patrocínios
        Patrocinio patrocinio1 = new Patrocinio("Banco Central", 150000.0, NivelPatrocinio.OURO);
        patrocinio1.adicionarBeneficio("Logotipo no palco principal");
        patrocinio1.adicionarBeneficio("Camarote exclusivo");
        festival.adicionarPatrocinio(patrocinio1);

        Patrocinio patrocinio2 = new Patrocinio("Cerveja Artesanal", 80000.0, NivelPatrocinio.PRATA);
        patrocinio2.adicionarBeneficio("Distribuição de brindes");
        festival.adicionarPatrocinio(patrocinio2);

        Patrocinio patrocinio3 = new Patrocinio("Rádio FM", 30000.0, NivelPatrocinio.BRONZE);
        festival.adicionarPatrocinio(patrocinio3);

        // Venda de ingressos
        festival.venderIngressos(20000, 200.0);
        festival.venderIngressos(15000, 150.0);
        festival.venderIngressos(10000, 100.0);
        festival.venderIngressos(10000, 50.0);   // deve falhar por capacidade

        // Confirmar e executar evento
        festival.confirmarEvento();
        festival.iniciar();
        festival.finalizar();

        // Exibir detalhes completos
        festival.exibirDetalhes();

        // Exibir informações da banda
        bandaRock.exibirInformacoes();

        // Polimorfismo
        System.out.println("\n===== ARTISTAS E SEUS CACHES =====");
        List<Artista> artistasSolo = List.of(caetano, anitta, steve, jaco, neil);
        for (Artista a : artistasSolo) {
            System.out.println(a.getNomeArtistico() + " - Cache para evento: R$" + a.calcularCacheEvento());
            a.apresentar();
        }

        empresa.exibirInformacoes();
    }
}
