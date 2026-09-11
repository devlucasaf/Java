package application.utilitarios.mockapi;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PrincipalMockApi {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final String ARQUIVO_PADRAO = "rotas-mock.json";

    public static void main(String[] args) {
        RepositorioRotas repositorio = new RepositorioRotas();
        GerenciadorConfiguracaoJson gerenciadorJson = new GerenciadorConfiguracaoJson();
        ServidorMockApi servidor = new ServidorMockApi(repositorio);

        carregarOuCriarRotas(repositorio, gerenciadorJson);
        executarMenu(repositorio, gerenciadorJson, servidor);

        if (servidor.isExecutando()) {
            servidor.parar();
        }

        LEITOR.close();
    }

    // --- CARREGA AS ROTAS OU CRIA AS ROTAS DE EXEMPLO ---
    private static void carregarOuCriarRotas(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) {
        if (gerenciadorJson.arquivoExiste(ARQUIVO_PADRAO)) {
            try {
                repositorio.substituirTodas(gerenciadorJson.carregar(ARQUIVO_PADRAO));
                System.out.println(repositorio.listarTodas().size() + " rota(s) carregada(s).");
                return;
            } catch (IOException | IllegalArgumentException excecao) {
                System.out.println("Não foi possível carregar as rotas: " + excecao.getMessage());
            }
        }

        criarRotasExemplo(repositorio);

        try {
            salvarRotas(repositorio, gerenciadorJson);
        } catch (IOException excecao) {
            System.out.println("Não foi possível salvar as rotas iniciais: " + excecao.getMessage());
        }
    }

    // --- CRIA ALGUMAS ROTAS PARA DEMONSTRAÇÃO ---
    private static void criarRotasExemplo(RepositorioRotas repositorio) {
        ConfiguracaoRota listarUsuarios = new ConfiguracaoRota(0, "Listar usuários", "GET", "/api/usuarios", 200, "application/json; charset=UTF-8", "[{\"id\":1,\"nome\":\"Ana\"},{\"id\":2,\"nome\":\"Bruno\"}]", 0);

        ConfiguracaoRota buscarUsuario = new ConfiguracaoRota(0, "Buscar usuário", "GET", "/api/usuarios/{id}", 200, "application/json; charset=UTF-8", "{\"id\":\"{{caminho.id}}\",\"nome\":\"Usuário {{caminho.id}}\"}", 250);

        ConfiguracaoRota criarUsuario = new ConfiguracaoRota(0, "Criar usuário", "POST", "/api/usuarios", 201, "application/json; charset=UTF-8", "{\"mensagem\":\"Usuário criado\",\"dadosRecebidos\":\"{{corpo}}\"}", 500);

        ConfiguracaoRota pesquisarProdutos = new ConfiguracaoRota(0, "Pesquisar produtos", "GET", "/api/produtos", 200, "application/json; charset=UTF-8", "{\"termo\":\"{{consulta.busca}}\",\"resultados\":[]}", 0);

        repositorio.adicionar(listarUsuarios);
        repositorio.adicionar(buscarUsuario);
        repositorio.adicionar(criarUsuario);
        repositorio.adicionar(pesquisarProdutos);
    }

    // --- EXECUTA O MENU PRINCIPAL ---
    private static void executarMenu(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson, ServidorMockApi servidor) {
        boolean executando = true;

        while (executando) {
            exibirMenu(servidor);
            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        iniciarServidor(servidor);
                        break;
                    case 2:
                        servidor.parar();
                        break;
                    case 3:
                        listarRotas(repositorio);
                        break;
                    case 4:
                        cadastrarRota(repositorio, gerenciadorJson);
                        break;
                    case 5:
                        ativarOuDesativarRota(repositorio, gerenciadorJson);
                        break;
                    case 6:
                        removerRota(repositorio, gerenciadorJson);
                        break;
                    case 7:
                        importarRotas(repositorio, gerenciadorJson);
                        break;
                    case 8:
                        exportarRotas(repositorio, gerenciadorJson);
                        break;
                    case 0:
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IOException excecao) {
                System.out.println("Erro de arquivo ou servidor: " + excecao.getMessage());
            } catch (IllegalArgumentException | IllegalStateException excecao) {
                System.out.println("Operação inválida: " + excecao.getMessage());
            }
        }
    }

    // --- INICIA O SERVIDOR ---
    private static void iniciarServidor(ServidorMockApi servidor) throws IOException {
        if (servidor.isExecutando()) {
            System.out.println("O servidor já está em execução.");
            return;
        }

        int porta = lerInteiroComPadrao("Porta ", 8080);
        servidor.iniciar(porta);
    }

    // --- CADASTRA UMA NOVA ROTA ---
    private static void cadastrarRota(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        System.out.println("\nCADASTRO DE ROTA");

        String nome = lerTextoObrigatorio("Nome: ");
        String metodo = lerTextoObrigatorio("Método HTTP: ");
        String caminho = lerTextoObrigatorio("Caminho: ");
        int status = lerInteiroComPadrao("Status HTTP ", 200);
        String tipoConteudo = lerTextoComPadrao("Tipo de conteúdo [application/json; charset=UTF-8]: ", "application/json; charset=UTF-8");
        long atraso = lerLongComPadrao("Atraso em milissegundos ", 0);

        System.out.println("Digite o corpo da resposta. Digite FIM em uma linha separada.");
        String corpo = lerTextoMultilinha();

        ConfiguracaoRota rota = new ConfiguracaoRota(0, nome, metodo, caminho, status, tipoConteudo, corpo, atraso);
        repositorio.adicionar(rota);
        salvarRotas(repositorio, gerenciadorJson);

        System.out.println("Rota cadastrada com o identificador " + rota.getIdentificador() + ".");
    }

    // --- ATIVA OU DESATIVA UMA ROTA ---
    private static void ativarOuDesativarRota(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        long identificador = lerLong("Identificador da rota: ");
        ConfiguracaoRota rota = repositorio.buscarPorIdentificador(identificador);

        if (rota == null) {
            System.out.println("Rota não encontrada.");
            return;
        }

        rota.setAtiva(!rota.isAtiva());
        salvarRotas(repositorio, gerenciadorJson);

        System.out.println("A rota agora está " + (rota.isAtiva() ? "ativa." : "inativa."));
    }

    // --- REMOVE UMA ROTA ---
    private static void removerRota(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        long identificador = lerLong("Identificador da rota: ");
        ConfiguracaoRota rota = repositorio.buscarPorIdentificador(identificador);

        if (rota == null) {
            System.out.println("Rota não encontrada.");
            return;
        }

        String confirmacao = lerTextoObrigatorio("Digite remover para confirmar: ");

        if (!confirmacao.equalsIgnoreCase("remover")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        repositorio.remover(identificador);
        salvarRotas(repositorio, gerenciadorJson);
        System.out.println("Rota removida.");
    }

    // --- LISTA AS ROTAS CADASTRADAS ---
    private static void listarRotas(RepositorioRotas repositorio) {
        List<ConfiguracaoRota> rotas = repositorio.listarTodas();

        if (rotas.isEmpty()) {
            System.out.println("Nenhuma rota cadastrada.");
            return;
        }

        System.out.println("\nROTAS CADASTRADAS");

        for (ConfiguracaoRota rota : rotas) {
            System.out.println("----------------------------------------");
            System.out.println("Identificador: " + rota.getIdentificador());
            System.out.println("Nome: " + rota.getNome());
            System.out.println("Rota: " + rota.getMetodo() + " " + rota.getCaminho());
            System.out.println("Resposta: HTTP " + rota.getStatusResposta());
            System.out.println("Atraso: " + rota.getAtrasoMilissegundos() + " ms");
            System.out.println("Ativa: " + (rota.isAtiva() ? "Sim" : "Não"));
        }

        System.out.println("----------------------------------------");
    }

    // --- IMPORTA AS ROTAS DE UM ARQUIVO JSON ---
    private static void importarRotas(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        String caminho = lerTextoObrigatorio("Arquivo JSON para importar: ");
        List<ConfiguracaoRota> rotas = gerenciadorJson.carregar(caminho);
        repositorio.substituirTodas(rotas);
        salvarRotas(repositorio, gerenciadorJson);
        System.out.println(rotas.size() + " rota(s) importada(s).");
    }

    // --- EXPORTA AS ROTAS PARA UM ARQUIVO JSON ---
    private static void exportarRotas(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        String caminho = lerTextoObrigatorio("Arquivo JSON de destino: ");
        gerenciadorJson.salvar(repositorio.listarTodas(), caminho);
        System.out.println("Rotas exportadas com sucesso.");
    }

    // --- SALVA AS ROTAS NO ARQUIVO PADRÃO ---
    private static void salvarRotas(RepositorioRotas repositorio, GerenciadorConfiguracaoJson gerenciadorJson) throws IOException {
        gerenciadorJson.salvar(repositorio.listarTodas(), ARQUIVO_PADRAO);
    }

    // --- LÊ UM TEXTO COM VÁRIAS LINHAS ---
    private static String lerTextoMultilinha() {
        StringBuilder texto = new StringBuilder();

        while (true) {
            String linha = LEITOR.nextLine();

            if (linha.equals("FIM")) {
                break;
            }

            if (texto.length() > 0) {
                texto.append(System.lineSeparator());
            }

            texto.append(linha);
        }

        return texto.toString();
    }

    // --- LÊ UM TEXTO OBRIGATÓRIO ---
    private static String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = LEITOR.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("O valor não pode estar vazio.");
        }
    }

    // --- LÊ UM TEXTO COM VALOR PADRÃO ---
    private static String lerTextoComPadrao(String mensagem, String valorPadrao) {
        System.out.print(mensagem);
        String valor = LEITOR.nextLine().trim();
        return valor.isEmpty() ? valorPadrao : valor;
    }

    // --- LÊ UM NÚMERO INTEIRO ---
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Integer.parseInt(LEITOR.nextLine().trim());
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // --- LÊ UM INTEIRO COM VALOR PADRÃO ---
    private static int lerInteiroComPadrao(String mensagem, int valorPadrao) {
        while (true) {
            System.out.print(mensagem);
            String entrada = LEITOR.nextLine().trim();

            if (entrada.isEmpty()) {
                return valorPadrao;
            }

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // --- LÊ UM NÚMERO LONG ---
    private static long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Long.parseLong(LEITOR.nextLine().trim());
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    // --- LÊ UM LONG COM VALOR PADRÃO ---
    private static long lerLongComPadrao(String mensagem, long valorPadrao) {
        while (true) {
            System.out.print(mensagem);
            String entrada = LEITOR.nextLine().trim();

            if (entrada.isEmpty()) {
                return valorPadrao;
            }

            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    // --- EXIBE O MENU PRINCIPAL ---
    private static void exibirMenu(ServidorMockApi servidor) {
        System.out.println("\n");
        System.out.println("              MOCK API");
        System.out.println("\n");
        System.out.println("Servidor: " + (servidor.isExecutando() ? "Executando em http://" + servidor.getEndereco() + ":" + servidor.getPorta() : "Parado"));
        System.out.println("1. Iniciar servidor");
        System.out.println("2. Parar servidor");
        System.out.println("3. Listar rotas");
        System.out.println("4. Cadastrar rota");
        System.out.println("5. Ativar ou desativar rota");
        System.out.println("6. Remover rota");
        System.out.println("7. Importar rotas");
        System.out.println("8. Exportar rotas");
        System.out.println("0. Encerrar");
        System.out.println("========================================");
    }
}

