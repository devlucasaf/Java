package application.utilitarios.carteira;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class Carteira {

    private final String    dono;
    private final KeyPair   chaves;

    // --- INICIALIZA A CARTEIRA E GERA UM PAR DE CHAVES RSA ---
    public Carteira(String dono) throws Exception {
        this.dono = dono;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        this.chaves = kpg.generateKeyPair();
    }

    // --- RETORNA O NOME DO DONO DA CARTEIRA ---
    public String getDono() {
        return dono;
    }

    // --- RETORNA A CHAVE PUBLICA DA CARTEIRA ---
    public PublicKey getChavePublica() {
        return chaves.getPublic();
    }

    // --- RETORNA A CHAVE PRIVADA DA CARTEIRA ---
    public PrivateKey getChavePrivada() {
        return chaves.getPrivate();
    }

    // --- GERA O ENDERECO DA CARTEIRA A PARTIR DA CHAVE PUBLICA ---
    public String getEndereco() {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(chaves.getPublic().getEncoded());
            return Base64.getEncoder().encodeToString(hash).substring(0, 32);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- ASSINA OS DADOS UTILIZANDO A CHAVE PRIVADA DA CARTEIRA ---
    public byte[] assinar(String dados) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initSign(chaves.getPrivate());
        signature.update(dados.getBytes());

        return signature.sign();
    }

    // --- VERIFICA A AUTENTICIDADE DA ASSINATURA COM A CHAVE PUBLICA ---
    public static boolean verificar(String dados, byte[] assinatura, PublicKey chavePublica) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initVerify(chavePublica);
        signature.update(dados.getBytes());

        return signature.verify(assinatura);
    }
}

