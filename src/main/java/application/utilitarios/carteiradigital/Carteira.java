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
        return Base64.getEncoder().encodeToString(chaves.getPublic().getEncoded()).substring(0, 32);
    }

    public byte[] assinar(String dados) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(chaves.getPrivate());
        sig.update(dados.getBytes());
        return sig.sign();
    }

    public static boolean verificar(String dados, byte[] assinatura, PublicKey chavePublica) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(chavePublica);
        sig.update(dados.getBytes());
        return sig.verify(assinatura);
    }
}

