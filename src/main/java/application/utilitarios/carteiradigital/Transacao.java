package application.utilitarios.carteiradigital;

import java.security.PublicKey;
import java.util.Base64;

public class Transacao {

    private final String    remetente;
    private final String    destinatario;
    private final double    valor;
    private final long      timestamp;
    private byte[]          assinatura;
    private PublicKey       chavePublicaRemetente;

    public Transacao(String remetente, String destinatario, double valor) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.valor = valor;
        this.timestamp = System.currentTimeMillis();
    }

    public String dadosParaAssinar() {
        return remetente + "|" + destinatario + "|" + valor + "|" + timestamp;
    }

    public void assinar(Carteira remetenteCarteira) throws Exception {
        this.assinatura = remetenteCarteira.assinar(dadosParaAssinar());
        this.chavePublicaRemetente = remetenteCarteira.getChavePublica();
    }

    public boolean verificar() throws Exception {
        if (assinatura == null || chavePublicaRemetente == null) {
            return false;
        }
        return Carteira.verificar(dadosParaAssinar(), assinatura, chavePublicaRemetente);
    }

    @Override
    public String toString() {
        String assinaturaStr = assinatura == null ? "SEM ASSINATURA"
                : Base64.getEncoder().encodeToString(assinatura).substring(0, 24) + "...";
        return String.format("[%s -> %s | R$ %.2f | %s]", remetente, destinatario, valor, assinaturaStr);
    }
}

