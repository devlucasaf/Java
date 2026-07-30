package application.utilitarios.carteiradigital;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CARTEIRA DIGITAL ===\n");

        Carteira alice = new Carteira("Alice");
        Carteira bob = new Carteira("Bob");
        Carteira eve = new Carteira("Eve (maliciosa)");

        System.out.println("Endereco Alice: " + alice.getEndereco());
        System.out.println("Endereco Bob:   " + bob.getEndereco());
        System.out.println();

        Transacao t1 = new Transacao(alice.getEndereco(), bob.getEndereco(), 100.0);
        t1.assinar(alice);
        System.out.println("Transacao valida: " + t1);
        System.out.println("Verificacao: " + t1.verificar());
        System.out.println();

        Transacao t2 = new Transacao(alice.getEndereco(), eve.getEndereco(), 1000.0);
        t2.assinar(eve);
        System.out.println("Transacao forjada por Eve (dizendo ser Alice): " + t2);
        System.out.println("Verificacao (esperado false pois eve nao e alice, mas assinatura e valida com sua chave):");
        System.out.println("  -> " + t2.verificar() + " (chave publica de Eve valida sua propria assinatura)");
        System.out.println("  No blockchain real, o remetente sempre e derivado da chave publica.");
        System.out.println();

        Transacao t3 = new Transacao(alice.getEndereco(), bob.getEndereco(), 50.0);
        t3.assinar(alice);
        System.out.println("Antes da adulteracao: " + t3.verificar());
        java.lang.reflect.Field campoValor = Transacao.class.getDeclaredField("valor");
        campoValor.setAccessible(true);
        campoValor.setDouble(t3, 5000.0);
        System.out.println("Apos adulterar valor para 5000: " + t3.verificar() + " (assinatura invalida)");
    }
}

