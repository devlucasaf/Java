package games.narrativo.escape;

public final class FabricaEscapeRoom {

    private FabricaEscapeRoom() {
    }

    // --- CRIA UM NOVO ESTADO COM TODAS AS SALAS, ITENS E ENIGMAS ---
    public static EstadoJogo criarJogo() {
        EstadoJogo estado = new EstadoJogo();

        Item bilhete = new Item("bilhete", "bilhete antigo", "O bilhete contém a frase: A luz revela aquilo que o tempo esconde.", true);
        Item vela = new Item("vela", "vela", "Uma vela quase nova, mas sem nada para acendê-la.", true);
        Item fosforos = new Item("fosforos", "caixa de fósforos", "Uma pequena caixa com alguns fósforos secos.", true);
        Item velaAcesa = new Item("vela_acesa", "vela acesa", "A chama ilumina inscrições escondidas nas paredes.", true);
        Item chaveBiblioteca = new Item("chave_biblioteca", "chave da biblioteca", "Uma chave de bronze com o desenho de um livro.", true);
        Item medalhao = new Item("medalhao", "medalhão", "Um medalhão com o símbolo de uma lua crescente.", true);
        Item paginaDiario = new Item("pagina_diario", "página do diário", "A página revela que o antigo proprietário escondia documentos no porão.", true);
        Item chaveSaida = new Item("chave_saida", "chave da saída", "Uma chave grande, provavelmente pertencente à porta principal.", true);
        Item documentoSecreto = new Item("documento_secreto", "documento secreto", "Um documento que revela os verdadeiros acontecimentos da mansão.", true);

        Sala quarto = new Sala("quarto", "Quarto abandonado", "Você acorda em um quarto antigo. Há uma cama, uma escrivaninha e uma porta ao norte.");
        Sala corredor = new Sala("corredor", "Corredor principal", "Retratos antigos acompanham seus movimentos. O corredor leva a diferentes partes da mansão.");
        Sala biblioteca = new Sala("biblioteca", "Biblioteca", "Estantes cobrem todas as paredes. Um pedestal sustenta uma caixa com símbolos.");
        Sala cozinha = new Sala("cozinha", "Cozinha", "Panelas enferrujadas e armários vazios ocupam o ambiente.");
        Sala escritorio = new Sala("escritorio", "Escritório", "Papéis estão espalhados pela mesa. Um cofre está preso à parede.");
        Sala porao = new Sala("porao", "Porão", "O ar é frio. Uma passagem estreita termina diante de um compartimento escondido.");
        Sala entrada = new Sala("entrada", "Entrada principal", "Uma grande porta de madeira separa você da liberdade.");

        quarto.adicionarSaida(Direcao.NORTE, "corredor");

        corredor.adicionarSaida(Direcao.SUL, "quarto");
        corredor.adicionarSaida(Direcao.NORTE, "entrada");
        corredor.adicionarSaida(Direcao.LESTE, "cozinha");
        corredor.adicionarSaida(Direcao.OESTE, "biblioteca");

        biblioteca.adicionarSaida(Direcao.LESTE, "corredor");
        biblioteca.adicionarSaida(Direcao.NORTE, "escritorio");

        cozinha.adicionarSaida(Direcao.OESTE, "corredor");

        escritorio.adicionarSaida(Direcao.SUL, "biblioteca");
        escritorio.adicionarSaida(Direcao.LESTE, "porao");

        porao.adicionarSaida(Direcao.OESTE, "escritorio");

        entrada.adicionarSaida(Direcao.SUL, "corredor");

        quarto.adicionarItem(bilhete);
        quarto.adicionarItem(vela);
        cozinha.adicionarItem(fosforos);

        Enigma enigmaParede = new Enigma("parede", "Inscrição escondida", "Ao aproximar a vela acesa da parede, surge a pergunta: Quantos lados possui um quadrado?", "4", "vela_acesa", "chave_biblioteca", "Uma parte da parede se abre e revela a chave da biblioteca.", "A inscrição desaparece por alguns segundos. A resposta parece incorreta.");

        Enigma enigmaCaixa = new Enigma("caixa", "Caixa de símbolos", "A caixa possui quatro símbolos: estrela, lua, sol e chave. O bilhete sugere que o símbolo correto aparece durante a noite. Qual é o símbolo?", "lua", "chave_biblioteca", "medalhao", "A caixa se abre e revela um medalhão.", "Nada acontece. O símbolo escolhido parece incorreto.");

        Enigma enigmaEstante = new Enigma("estante", "Estante móvel", "Há um encaixe circular no centro da estante. Digite medalhao para encaixar o objeto.", "medalhao", "medalhao", "pagina_diario", "A estante se move e revela uma página perdida do diário.", "O mecanismo da estante permanece imóvel.");

        Enigma enigmaCofre = new Enigma("cofre", "Cofre da parede", "A página do diário contém os números 1, 9, 2 e 7 destacados. Informe o código completo.", "1927", "pagina_diario", "chave_saida", "O cofre se abre e revela a chave da saída.", "O cofre emite um clique, mas continua trancado.");

        Enigma enigmaCompartimento = new Enigma("compartimento", "Compartimento secreto", "O compartimento possui o símbolo da lua. Digite lua para abri-lo.", "lua", "medalhao", "documento_secreto", "O compartimento se abre e revela um documento secreto.", "O compartimento continua fechado.");

        quarto.adicionarEnigma(enigmaParede);
        biblioteca.adicionarEnigma(enigmaCaixa);
        biblioteca.adicionarEnigma(enigmaEstante);
        escritorio.adicionarEnigma(enigmaCofre);
        porao.adicionarEnigma(enigmaCompartimento);

        estado.adicionarSala(quarto);
        estado.adicionarSala(corredor);
        estado.adicionarSala(biblioteca);
        estado.adicionarSala(cozinha);
        estado.adicionarSala(escritorio);
        estado.adicionarSala(porao);
        estado.adicionarSala(entrada);
        estado.setIdentificadorSalaAtual("quarto");

        adicionarItensDeRecompensa(estado, velaAcesa, chaveBiblioteca, medalhao, paginaDiario, chaveSaida, documentoSecreto);

        return estado;
    }

    // --- GUARDA OS ITENS DE RECOMPENSA EM UMA SALA INTERNA NÃO ACESSÍVEL ---
    private static void adicionarItensDeRecompensa(EstadoJogo estado, Item... itens) {
        Sala deposito = new Sala("deposito_interno", "Depósito interno", "Esta sala existe apenas para armazenar recompensas.");

        for (Item item : itens) {
            deposito.adicionarItem(item);
        }

        estado.adicionarSala(deposito);
    }
}

