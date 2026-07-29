package application.utilitarios.brainfuck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final String HELLO_WORLD =
            "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]" +
            ">>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";

    private static final String SOMA_DOIS_NUMEROS =
            ",>++++++[<-------->-],[<+>-]<.";

    public static void main(String[] args) throws IOException {
        Interpretador interp = new Interpretador();

        if (args.length > 0) {
            String programa = Files.readString(Path.of(args[0]));
            System.out.println(interp.executar(programa));
            return;
        }

        System.out.println("=== Hello World em Brainfuck ===");
        System.out.println(interp.executar(HELLO_WORLD));

        interp.reset();
        System.out.println("\n=== Programa: Soma de dois digitos ===");
        System.out.println("Digite 2 caracteres numericos (ex: 3 e 4)...");
        System.out.println("Programa: " + SOMA_DOIS_NUMEROS);
    }
}

