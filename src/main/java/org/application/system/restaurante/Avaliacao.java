package org.application.system.restaurante;

public class Avaliacao {
    private static int sequencia = 1;

    private final int       id;
    private final Cliente   cliente;
    private final Pedido    pedido;
    private int             notaComida;
    private int             notaAtendimento;
    private int             notaAmbiente;
    private String          comentario;

    public Avaliacao(Cliente cliente, Pedido pedido, int notaComida, int notaAtendimento, int notaAmbiente, String comentario) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo");
        }

        if (pedido == null) {
            throw new IllegalArgumentException("O pedido não pode ser nulo");
        }

        this.id = sequencia++;
        this.cliente = cliente;
        this.pedido = pedido;
        setNotaComida(notaComida);
        setNotaAtendimento(notaAtendimento);
        setNotaAmbiente(notaAmbiente);
        this.comentario = comentario != null ? comentario : "";
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public int getNotaComida() {
        return notaComida;
    }

    public void setNotaComida(int notaComida) {
        validarNota(notaComida);
        this.notaComida = notaComida;
    }

    public int getNotaAtendimento() {
        return notaAtendimento;
    }

    public void setNotaAtendimento(int notaAtendimento) {
        validarNota(notaAtendimento);
        this.notaAtendimento = notaAtendimento;
    }

    public int getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(int notaAmbiente) {
        validarNota(notaAmbiente);
        this.notaAmbiente = notaAmbiente;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario != null ? comentario : "";
    }

    public double getNotaMedia() {
        return (notaComida + notaAtendimento + notaAmbiente) / 3.0;
    }

    private void validarNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
    }

    @Override
    public String toString() {
        return String.format("Avaliação #%d | Cliente: %s | Comida: %d/5 | Atendimento: %d/5 | Ambiente: %d/5 | Média: %.1f | %s",
                id, cliente.getNome(),
                notaComida,
                notaAtendimento,
                notaAmbiente,
                getNotaMedia(),
                comentario.isEmpty() ? "(sem comentário)" : "\"" + comentario + "\"");
    }
}

