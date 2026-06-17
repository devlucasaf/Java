package games.text.criptografia;

public class CifraVigenere {
    public static String criptografar(String texto, String chave) {
        StringBuilder resultado = new StringBuilder();
        chave = chave.toUpperCase();
        int j = 0;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int deslocamento = chave.charAt(j % chave.length()) - 'A';
                resultado.append((char) ((c - base + deslocamento) % 26 + base));
                j++;
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static String descriptografar(String texto, String chave) {
        StringBuilder resultado = new StringBuilder();
        chave = chave.toUpperCase();
        int j = 0;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int deslocamento = chave.charAt(j % chave.length()) - 'A';
                resultado.append((char) ((c - base - deslocamento + 26) % 26 + base));
                j++;
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}

