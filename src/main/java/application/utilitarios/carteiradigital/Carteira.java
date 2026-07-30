package application.utilitarios.carteiradigital;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class Carteira {

    private final String    dono;
    private final KeyPair   chaves;

    public Carteira(String dono) throws Exception {
        this.dono = dono;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        this.chaves = kpg.generateKeyPair();
    }

    public String getDono() {
        return dono;
    }

    public PublicKey getChavePublica() {
        return chaves.getPublic();
    }

    public PrivateKey getChavePrivada() {
        return chaves.getPrivate();
    }

    public String getEndereco() {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(chaves.getPublic().getEncoded());
            return Base64.getEncoder().encodeToString(hash).substring(0, 32);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] assinar(String dados) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initSign(chaves.getPrivate());
        signature.update(dados.getBytes());

        return signature.sign();
    }

    public static boolean verificar(String dados, byte[] assinatura, PublicKey chavePublica) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initVerify(chavePublica);
        signature.update(dados.getBytes());

        return signature.verify(assinatura);
    }
}

