package org.application.system.automovel.veiculo;

// Código desenvolvido por Lucas Freitas a fim de estudos
// Herança em Java - Classe Veículo
// Código iniciado em 06/01/2026 - 08/01/2026

public class Veiculo {
    public static void main(String[] args) {
        Carro carro1 = new Carro("Honda",
                "Civic Hybrid 2025",
                265.900,
                0,
                2025,
                180,
                4,
                5,
                3,
                3,
                "Automático/Elétrico",
                "Aditivada",
                495,
                false
        );

        Carro carro2 = new Carro("Porsche",
                "Panamera Turbo S E-Hybrid",
                991.755,
                259456,
                2022,
                315,
                4,
                8,
                5,
                2,
                "Automático",
                "Comum",
                425,
                true
        );

        Carro carro3 = new Carro(
                "Renault",
                "Kwid Desgrama Outsider",
                47.520,
                20.547,
                2019,
                2,
                4,
                5,
                5,
                8,
                "Manual",
                "Comum",
                290,
                false
        );

        Onibus onibus1 = new Onibus
                ("Mercedes-Benz",
                        "OF 1519",
                        165.000,
                        0,
                        2014,
                        80,
                        3,
                        32,
                        85,
                        70,
                        5,
                        true,
                        false,
                        true,
                        false
                );

        Onibus onibus2 = new Onibus(
                "BYD",
                "BYD D9W",
                2800000,
                0,
                2025,
                70,
                2,
                40,
                100,
                140,
                6,
                false,
                true,
                false,
                true
        );

        Onibus onibus3 = new Onibus(
                "Volkswagen",
                "Volksbus 17.230 OD",
                300.000,
                80000,
                2016,
                95,
                3,
                45,
                120,
                10,
                5,
                true,
                false,
                true,
                false
        );

        Moto moto1 = new Moto(
                "Kawasaki",
                "Ninja ZX-10R",
                115000.00,
                0.0,
                2024,
                299,
                5,
                8,
                450,
                "Esportiva"
        );

        Moto moto2 = new Moto(
                "Yamaha",
                "MT-07",
                47500.00,
                0.0,
                2024,
                230,
                2,
                6,
                250,
                "Urbano"
        );

        Moto moto3 = new Moto(
                "BMW",
                "R 1250 GS",
                125000.00,
                5500.0,
                2024,
                200,
                4,
                6,
                320,
                "Trabalho"
        );

        Aviao aviao1 = new Aviao(
                "Embraer",
                "Phenom 300",
                50000000.0,
                500.0,
                2023,
                839,
                10,
                8,
                2,
                9500.0,
                false,
                false,
                true,
                true,
                false,
                "Jato"
        );

        Aviao aviao2 = new Aviao(
                "Cessna",
                "172 Skyhawk",
                2500000.0,
                1500.0,
                2022,
                226,
                3,
                2,
                1,
                15000.0,
                false,
                true,
                false,
                false,
                false,
                "Monomotor"
        );

        Aviao aviao3 = new Aviao(
                "LATAM Airlines Group",
                "A320",
                600000000.0,
                2500.0,
                2021,
                876,
                220,
                210,
                2,
                0,
                true,
                true,
                false,
                true,
                false,
                "Passageiro bimotor de fuselagem estreita"
        );

        Trem trem1 = new Trem("Alstom",
                "TGV Euroduplex",
                180000000.0,
                12000.0,
                2022,
                320,
                10,
                70,
                80,
                80,
                false,
                false
        );

        Trem trem2 = new Trem(
                "Siemens",
                "Velaro",
                150000000.0,
                8500.0,
                2021,
                330,
                8,
                70,
                80,
                50,
                true,
                false
        );

        Trem trem3 = new Trem(
                "Bombardier",
                "Zefiro 380",
                140000000.0,
                5000.0,
                2023,
                380,
                15,
                90,
                150,
                200,
                false,
                true
        );

        VeiculoAutoMovel[] veiculos = {
                carro1, carro2, carro3,
                onibus1, onibus2, onibus3,
                moto1, moto2, moto3,
                aviao1, aviao2, aviao3,
                trem1, trem2, trem3
        };

        for (VeiculoAutoMovel v : veiculos) {
            v.mostrarDados();
            System.out.println("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
        }
    }
}
