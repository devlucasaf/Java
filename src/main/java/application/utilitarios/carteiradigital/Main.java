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

        Transacao transacao1 = new Transacao(alice.getEndereco(), bob.getEndereco(), 100.0);
        transacao1.assinar(alice);
        System.out.println("Transacao valida: " + transacao1);
        System.out.println("Verificacao: " + transacao1.verificar());
        System.out.println();

        Transacao transacao2 = new Transacao(alice.getEndereco(), eve.getEndereco(), 1000.0);
        transacao2.assinar(eve);
        System.out.println("Transacao forjada por Eve (dizendo ser Alice): " + transacao2);
        System.out.println("Verificacao (esperado false pois eve nao e alice, mas assinatura e valida com sua chave):");
        System.out.println("  -> " + transacao2.verificar() + " (chave publica de Eve valida sua propria assinatura)");
        System.out.println("  No blockchain real, o remetente sempre e derivado da chave publica.");
        System.out.println();

        Transacao transacao3 = new Transacao(alice.getEndereco(), bob.getEndereco(), 50.0);
        transacao3.assinar(alice);
        System.out.println("Antes da adulteracao: " + transacao3.verificar());
        java.lang.reflect.Field campoValor = Transacao.class.getDeclaredField("valor");
        campoValor.setAccessible(true);
        campoValor.setDouble(transacao3, 5000.0);
        System.out.println("Apos adulterar valor para 5000: " + transacao3.verificar() + " (assinatura invalida)");
    }
}

