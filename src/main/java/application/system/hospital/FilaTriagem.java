package application.system.hospital;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilaTriagem {

    private final List<RegistroTriagem> registros;

    public FilaTriagem() {
        this.registros = new ArrayList<>();
    }

    // --- ADICIONA UM PACIENTE À FILA DE TRIAGEM ---
    public RegistroTriagem adicionarPaciente(Paciente paciente, GravidadeTriagem gravidade, List<String> sintomas, String observacoes) {
        if (buscarRegistroAtivoPorPaciente(paciente) != null) {
            throw new IllegalStateException("O paciente já possui uma triagem ativa.");
        }

        RegistroTriagem registro = new RegistroTriagem(paciente, gravidade, sintomas, observacoes);
        registros.add(registro);
        return registro;
    }

    // --- RECLASSIFICA AUTOMATICAMENTE OS PACIENTES EM ESPERA ---
    public int reclassificarAutomaticamente() {
        int quantidadeReclassificada = 0;
        boolean houveAlteracao;

        do {
            houveAlteracao = false;

            for (RegistroTriagem registro : registros) {
                if (registro.reclassificarAutomaticamente()) {
                    quantidadeReclassificada++;
                    houveAlteracao = true;
                }
            }
        } while (houveAlteracao);

        return quantidadeReclassificada;
    }

    // --- RETORNA O PRÓXIMO PACIENTE DE ACORDO COM A PRIORIDADE DINÂMICA ---
    public RegistroTriagem obterProximoPaciente() {
        reclassificarAutomaticamente();

        for (RegistroTriagem registro : listarPacientesAguardando()) {
            return registro;
        }

        return null;
    }

    // --- RETORNA OS PACIENTES AGUARDANDO EM ORDEM DE PRIORIDADE ---
    public List<RegistroTriagem> listarPacientesAguardando() {
        reclassificarAutomaticamente();

        List<RegistroTriagem> pacientesAguardando = new ArrayList<>();

        for (RegistroTriagem registro : registros) {
            if (registro.getSituacao() == SituacaoTriagem.AGUARDANDO) {
                pacientesAguardando.add(registro);
            }
        }

        pacientesAguardando.sort(criarComparadorPrioridade());
        return pacientesAguardando;
    }

    // --- RETORNA OS PACIENTES QUE ESTÃO EM ATENDIMENTO ---
    public List<RegistroTriagem> listarPacientesEmAtendimento() {
        List<RegistroTriagem> pacientesEmAtendimento = new ArrayList<>();

        for (RegistroTriagem registro : registros) {
            if (registro.getSituacao() == SituacaoTriagem.EM_ATENDIMENTO) {
                pacientesEmAtendimento.add(registro);
            }
        }

        pacientesEmAtendimento.sort(Comparator.comparing(RegistroTriagem::getHorarioInicioAtendimento));
        return pacientesEmAtendimento;
    }

    // --- RETORNA TODOS OS REGISTROS DE TRIAGEM ---
    public List<RegistroTriagem> listarTodosRegistros() {
        return new ArrayList<>(registros);
    }

    // --- BUSCA UM REGISTRO PELO IDENTIFICADOR ---
    public RegistroTriagem buscarPorIdentificador(long identificador) {
        for (RegistroTriagem registro : registros) {
            if (registro.getIdentificador() == identificador) {
                return registro;
            }
        }

        return null;
    }

    // --- BUSCA UMA TRIAGEM ATIVA PELO PACIENTE ---
    public RegistroTriagem buscarRegistroAtivoPorPaciente(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        for (RegistroTriagem registro : registros) {
            boolean mesmaPessoa = registro.getPaciente() == paciente
                    || registro.getPaciente().getNumeroProntuario().equalsIgnoreCase(paciente.getNumeroProntuario());
            boolean registroAtivo = registro.getSituacao() == SituacaoTriagem.AGUARDANDO
                    || registro.getSituacao() == SituacaoTriagem.EM_ATENDIMENTO;

            if (mesmaPessoa && registroAtivo) {
                return registro;
            }
        }

        return null;
    }

    // --- CRIA A ORDENAÇÃO USADA PELA FILA DINÂMICA ---
    private Comparator<RegistroTriagem> criarComparadorPrioridade() {
        return Comparator.comparingLong(RegistroTriagem::calcularPontuacaoPrioridade).reversed()
                .thenComparing(RegistroTriagem::getHorarioEntrada)
                .thenComparing(registro -> registro.getPaciente().getNome(), String.CASE_INSENSITIVE_ORDER);
    }

    public int getQuantidadeAguardando() {
        return listarPacientesAguardando().size();
    }

    public int getQuantidadeEmAtendimento() {
        return listarPacientesEmAtendimento().size();
    }

    // --- RETORNA O MAIOR TEMPO DE ESPERA ATUAL ---
    public long getMaiorTempoEsperaMinutos() {
        long maiorTempo = 0;

        for (RegistroTriagem registro : listarPacientesAguardando()) {
            maiorTempo = Math.max(maiorTempo, registro.calcularTempoEsperaMinutos());
        }

        return maiorTempo;
    }

    // --- RETORNA O HORÁRIO DA ÚLTIMA ATUALIZAÇÃO DA FILA ---
    public LocalDateTime getHorarioConsultaFila() {
        return LocalDateTime.now();
    }
}

