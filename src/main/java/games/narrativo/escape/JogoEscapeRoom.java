package games.narrativo.escape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JogoEscapeRoom {

    private EstadoJogo estado;
    private final GerenciadorPersistencia persistencia;

    public JogoEscapeRoom() {
        this.estado = FabricaEscapeRoom.criarJogo();
        this.persistencia = new GerenciadorPersistencia();
        this.estado.getSalaAtual().setVisitada(true);
    }

    // --- MOVIMENTA O JOGADOR ENTRE AS SALAS ---
    public String mover(Direcao direcao) {
        if (direcao == null) {
            return "Direção inválida.";
        }

        if (estado.isJogoEncerrado()) {
            return "O jogo já foi encerrado.";
        }

        Sala salaAtual = estado.getSalaAtual();
        String identificadorDestino = salaAtual.getSalaDestino(direcao);

        if (identificadorDestino == null) {
            return "Não existe uma passagem nessa direção.";
        }

        if (!podeEntrar(identificadorDestino)) {
            return getMensagemBloqueio(identificadorDestino);
        }

        estado.setIdentificadorSalaAtual(identificadorDestino);
        estado.getSalaAtual().setVisitada(true);
        estado.registrarAcao();

        return "Você seguiu para " + direcao.getNomeFormatado() + " e entrou em: " + estado.getSalaAtual().getNome() + ".";
    }

    // --- VERIFICA SE O JOGADOR PODE ENTRAR EM UMA SALA ---
    private boolean podeEntrar(String identificadorSala) {
        if ("biblioteca".equals(identificadorSala)) {
            return estado.getInventario().possuiItem("chave_biblioteca");
        }

        if ("escritorio".equals(identificadorSala)) {
            return buscarEnigmaGeral("estante").isResolvido();
        }

        if ("porao".equals(identificadorSala)) {
            return estado.getInventario().possuiItem("pagina_diario");
        }

        return true;
    }

    // --- RETORNA A MENSAGEM DE BLOQUEIO DA SALA ---
    private String getMensagemBloqueio(String identificadorSala) {
        switch (identificadorSala) {
            case "biblioteca":
                return "A porta da biblioteca está trancada. Você precisa encontrar uma chave.";
            case "escritorio":
                return "A passagem está bloqueada pela estante. Existe algum mecanismo escondido nela.";
            case "porao":
                return "A entrada do porão está escondida. Talvez alguma pista revele sua localização.";
            default:
                return "Você não pode entrar nessa sala agora.";
        }
    }

    // --- EXAMINA A SALA, UM ITEM OU UM ENIGMA ---
    public String examinar(String alvo) {
        Sala salaAtual = estado.getSalaAtual();

        if (alvo == null || alvo.trim().isEmpty() || alvo.equalsIgnoreCase("sala")) {
            return descreverSalaAtual();
        }

        Item itemSala = salaAtual.buscarItem(alvo);

        if (itemSala != null) {
            return itemSala.getNome() + ": " + itemSala.getDescricao();
        }

        Item itemInventario = estado.getInventario().buscarItem(alvo);

        if (itemInventario != null) {
            return itemInventario.getNome() + ": " + itemInventario.getDescricao();
        }

        Enigma enigma = salaAtual.buscarEnigma(alvo);

        if (enigma != null) {
            if (enigma.isResolvido()) {
                return enigma.getTitulo() + ": este enigma já foi resolvido.";
            }

            if (!enigma.possuiRequisito(estado.getInventario())) {
                return enigma.getTitulo() + ": você ainda não possui o item necessário para compreender este enigma.";
            }

            return enigma.getTitulo() + ": " + enigma.getDescricao();
        }

        return "Não foi possível encontrar isso na sala ou no inventário.";
    }

    // --- COLETA UM ITEM DA SALA ATUAL ---
    public String pegarItem(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "Informe o item que deseja pegar.";
        }

        Item item = estado.getSalaAtual().buscarItem(texto);

        if (item == null) {
            return "Esse item não está nesta sala.";
        }

        if (!item.isColetavel()) {
            return "Esse item não pode ser coletado.";
        }

        estado.getSalaAtual().removerItem(texto);
        estado.getInventario().adicionarItem(item);
        estado.registrarAcao();

        return "Você adicionou " + item.getNome() + " ao inventário.";
    }

    // --- COMBINA DOIS ITENS DO INVENTÁRIO ---
    public String combinarItens(String primeiroTexto, String segundoTexto) {
        Item primeiroItem = estado.getInventario().buscarItem(primeiroTexto);
        Item segundoItem = estado.getInventario().buscarItem(segundoTexto);

        if (primeiroItem == null || segundoItem == null) {
            return "Você precisa possuir os dois itens para combiná-los.";
        }

        boolean combinacaoVela = correspondeCombinacao(primeiroItem, segundoItem, "vela", "fosforos");

        if (!combinacaoVela) {
            return "Esses itens não podem ser combinados.";
        }

        estado.getInventario().removerItem("vela");
        estado.getInventario().removerItem("fosforos");

        Item velaAcesa = retirarRecompensa("vela_acesa");
        estado.getInventario().adicionarItem(velaAcesa);
        estado.registrarAcao();

        return "Você acendeu a vela utilizando os fósforos. A vela acesa foi adicionada ao inventário.";
    }

    // --- VERIFICA SE DOIS ITENS CORRESPONDEM A UMA COMBINAÇÃO ---
    private boolean correspondeCombinacao(Item primeiroItem, Item segundoItem, String primeiroIdentificador, String segundoIdentificador) {
        boolean ordemNormal = primeiroItem.getIdentificador().equals(primeiroIdentificador) && segundoItem.getIdentificador().equals(segundoIdentificador);
        boolean ordemInvertida = primeiroItem.getIdentificador().equals(segundoIdentificador) && segundoItem.getIdentificador().equals(primeiroIdentificador);
        return ordemNormal || ordemInvertida;
    }

    // --- TENTA RESOLVER UM ENIGMA DA SALA ATUAL ---
    public String resolverEnigma(String textoEnigma, String resposta) {
        Enigma enigma = estado.getSalaAtual().buscarEnigma(textoEnigma);

        if (enigma == null) {
            return "Esse enigma não existe na sala atual.";
        }

        if (enigma.isResolvido()) {
            return "Esse enigma já foi resolvido.";
        }

        if (!enigma.possuiRequisito(estado.getInventario())) {
            return "Você ainda não possui o item necessário para resolver esse enigma.";
        }

        if (!enigma.tentarResolver(resposta, estado.getInventario())) {
            estado.registrarAcao();
            return enigma.getMensagemErro();
        }

        entregarRecompensa(enigma);
        estado.registrarAcao();

        return enigma.getMensagemSucesso();
    }

    // --- ENTREGA A RECOMPENSA DO ENIGMA AO JOGADOR ---
    private void entregarRecompensa(Enigma enigma) {
        String identificadorRecompensa = enigma.getIdentificadorItemRecompensa();

        if (identificadorRecompensa == null) {
            return;
        }

        Item recompensa = retirarRecompensa(identificadorRecompensa);

        if (recompensa != null) {
            estado.getInventario().adicionarItem(recompensa);
        }
    }

    // --- RETIRA UM ITEM DO DEPÓSITO INTERNO ---
    private Item retirarRecompensa(String identificador) {
        Sala deposito = estado.buscarSala("deposito_interno");

        if (deposito == null) {
            return null;
        }

        return deposito.removerItem(identificador);
    }

    // --- TENTA ABRIR A PORTA PRINCIPAL E DEFINE O FINAL ---
    public String abrirSaida() {
        if (!"entrada".equals(estado.getIdentificadorSalaAtual())) {
            return "Você precisa estar na entrada principal para tentar sair.";
        }

        if (!estado.getInventario().possuiItem("chave_saida")) {
            return "A porta principal está trancada. Você ainda não encontrou a chave correta.";
        }

        estado.registrarAcao();
        estado.setJogoEncerrado(true);

        if (estado.getInventario().possuiItem("documento_secreto")) {
            estado.setTipoFinal(TipoFinal.SEGREDO_DESCOBERTO);
            return "FINAL SECRETO: você escapou levando o documento que revela a verdade sobre a mansão. O mistério finalmente poderá ser esclarecido.";
        }

        estado.setTipoFinal(TipoFinal.FUGA_PRINCIPAL);
        return "FINAL DA FUGA: você abriu a porta principal e escapou da mansão, mas alguns de seus segredos permaneceram escondidos.";
    }

    // --- PERMITE QUE O JOGADOR DESISTA E ATIVA O FINAL ALTERNATIVO ---
    public String desistir() {
        estado.registrarAcao();
        estado.setTipoFinal(TipoFinal.PRISIONEIRO_DA_MANSAO);
        estado.setJogoEncerrado(true);

        return "FINAL ALTERNATIVO: sem encontrar uma saída, você decidiu permanecer na mansão. Seus corredores agora guardam mais uma história sem conclusão.";
    }

    // --- DESCREVE A SALA ATUAL E SEUS ELEMENTOS VISÍVEIS ---
    public String descreverSalaAtual() {
        Sala sala = estado.getSalaAtual();
        StringBuilder descricao = new StringBuilder();

        descricao.append("\n").append(sala.getNome().toUpperCase()).append("\n");
        descricao.append(sala.getDescricao()).append("\n");

        if (!sala.getItens().isEmpty()) {
            descricao.append("Itens visíveis: ");

            for (int indice = 0; indice < sala.getItens().size(); indice++) {
                if (indice > 0) {
                    descricao.append(", ");
                }

                descricao.append(sala.getItens().get(indice).getNome());
            }

            descricao.append(".\n");
        }

        List<Enigma> enigmasDisponiveis = new ArrayList<>();

        for (Enigma enigma : sala.getEnigmas()) {
            if (!enigma.isResolvido()) {
                enigmasDisponiveis.add(enigma);
            }
        }

        if (!enigmasDisponiveis.isEmpty()) {
            descricao.append("Elementos enigmáticos: ");

            for (int indice = 0; indice < enigmasDisponiveis.size(); indice++) {
                if (indice > 0) {
                    descricao.append(", ");
                }

                descricao.append(enigmasDisponiveis.get(indice).getTitulo());
            }

            descricao.append(".\n");
        }

        descricao.append("Saídas: ");

        if (sala.getSaidas().isEmpty()) {
            descricao.append("nenhuma");
        } else {
            int indice = 0;

            for (Map.Entry<Direcao, String> saida : sala.getSaidas().entrySet()) {
                if (indice > 0) {
                    descricao.append(", ");
                }

                descricao.append(saida.getKey().getNomeFormatado());
                indice++;
            }
        }

        descricao.append(".");

        return descricao.toString();
    }

    // --- RETORNA A DESCRIÇÃO DO INVENTÁRIO ---
    public String exibirInventario() {
        if (estado.getInventario().isVazio()) {
            return "Seu inventário está vazio.";
        }

        StringBuilder resultado = new StringBuilder("Inventário:\n");

        for (Item item : estado.getInventario().getItens()) {
            resultado.append("- ").append(item.getNome()).append(": ").append(item.getDescricao()).append("\n");
        }

        return resultado.toString().trim();
    }

    // --- PROCURA UM ENIGMA EM TODAS AS SALAS ---
    private Enigma buscarEnigmaGeral(String identificador) {
        for (Sala sala : estado.getSalas().values()) {
            Enigma enigma = sala.buscarEnigma(identificador);

            if (enigma != null) {
                return enigma;
            }
        }
        return null;
    }

    // --- SALVA O ESTADO ATUAL DA PARTIDA ---
    public String salvar(String caminhoArquivo) {
        try {
            persistencia.salvar(estado, caminhoArquivo);
            return "Jogo salvo com sucesso em " + caminhoArquivo + ".";
        } catch (IOException excecao) {
            return "Não foi possível salvar o jogo: " + excecao.getMessage();
        }
    }

    // --- CARREGA UMA PARTIDA SALVA ---
    public String carregar(String caminhoArquivo) {
        try {
            estado = persistencia.carregar(caminhoArquivo);
            return "Jogo carregado com sucesso.";
        } catch (IOException | ClassNotFoundException excecao) {
            return "Não foi possível carregar o jogo: " + excecao.getMessage();
        }
    }

    // --- RETORNA O ESTADO ATUAL DO JOGO ---
    public EstadoJogo getEstado() {
        return estado;
    }
}

