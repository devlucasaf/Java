package application.system.animal.petshop;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SistemaPetshop {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar petshop
        Petshop petshop = new Petshop("Pet & Cia", "12.345.678/0001-90", "Rua dos Animais, 123");

        // Criar serviços
        Servico banho = new Servico(TipoServico.BANHO, "Banho completo", 50.0, 45);
        Servico tosa = new Servico(TipoServico.TOSA, "Tosa higiênica", 40.0, 30);
        Servico consulta = new Servico(TipoServico.CONSULTA_VETERINARIA, "Consulta clínica", 120.0, 60);
        petshop.adicionarServico(banho);
        petshop.adicionarServico(tosa);
        petshop.adicionarServico(consulta);

        // Criar produtos
        Produto racao = new Produto("Ração Premium", TipoProduto.RACAO, 120.0, 20, "Royal Canin");
        Produto brinquedo = new Produto("Bolinha de borracha", TipoProduto.BRINQUEDO, 15.0, 50, "PetPlay");
        Produto vermifugo = new Produto("Vermífugo", TipoProduto.MEDICAMENTO, 25.0, 30, "VetFarma");
        petshop.adicionarProduto(racao);
        petshop.adicionarProduto(brinquedo);
        petshop.adicionarProduto(vermifugo);

        // Criar clientes
        ClientePetshop cliente1 = new ClientePetshop("João Silva", "123.456.789-00",
                "(11) 98765-4321", "joao@email.com", "Rua A, 123", "C001");
        ClientePetshop cliente2 = new ClientePetshop("Maria Santos", "987.654.321-11",
                "(11) 91234-5678", "maria@email.com", "Rua B, 456", "C002");
        petshop.cadastrarCliente(cliente1);
        petshop.cadastrarCliente(cliente2);

        // Criar animais
        Cachorro rex = new Cachorro("Rex", "Pastor Alemão", LocalDate.parse("10/05/2018", formatter),
                PorteAnimal.GRANDE, 35.5, "Preto e castanho", true, "Alta");
        Gato mimi = new Gato("Mimi", "Siamês", LocalDate.parse("15/03/2020", formatter),
                PorteAnimal.PEQUENO, 4.2, "Branco", true, true);
        Passaro pipoca = new Passaro("Pipoca", "Calopsita", LocalDate.parse("20/07/2021", formatter),
                PorteAnimal.PEQUENO, 0.09, "Amarelo", true, "Cinza e amarela");

        cliente1.adicionarAnimal(rex);
        cliente1.adicionarAnimal(mimi);
        cliente2.adicionarAnimal(pipoca);
        petshop.cadastrarAnimal(rex);
        petshop.cadastrarAnimal(mimi);
        petshop.cadastrarAnimal(pipoca);

        // Criar veterinário
        Veterinario vet = new Veterinario("Dra. Carla Lima", "222.333.444-55", "(11) 95555-8888",
                "carla@vet.com", "Rua C, 789", "CRMV-12345", "Clínica geral", 6000.0);
        petshop.contratarVeterinario(vet);

        // Agendar consulta
        LocalDateTime dataConsulta = LocalDateTime.now().plusDays(2).withHour(14).withMinute(0);
        petshop.criarAgendamento(cliente1, rex, consulta, dataConsulta, vet);

        // Agendar banho
        LocalDateTime dataBanho = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        petshop.criarAgendamento(cliente2, pipoca, banho, dataBanho, null);

        // Realizar uma venda
        Venda venda = petshop.iniciarVenda(cliente1, FormaPagamentoPetshop.PIX);
        venda.adicionarItem(racao, 2);
        venda.adicionarItem(brinquedo, 3);
        venda.adicionarServico(tosa, rex);
        venda.aplicarSaldoFidelidade(); // sem saldo inicial
        venda.finalizarVenda();

        // Exibir relatórios
        petshop.exibirClientes();
        petshop.exibirAnimais();
        petshop.exibirAgendamentosDoDia();
        petshop.exibirEstoqueBaixo(10);

        // Polimorfismo: animais emitindo som
        System.out.println("\n=== SONS DOS ANIMAIS ===");
        List<Animal> animais = List.of(rex, mimi, pipoca);

        for (Animal a : animais) {
            a.emitirSom();
        }

        // Informações do veterinário
        vet.exibirInformacoes();
    }
}