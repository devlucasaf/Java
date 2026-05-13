package org.games.criptografia;

public class CifraCesar {

    public static String criptografar(String texto, int chave) {
        StringBuilder resultado = new StringBuilder();
        chave = ((chave % 26) + 26) % 26; // Normaliza chave

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                resultado.append((char) ((c - base + chave) % 26 + base));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static String descriptografar(String texto, int chave) {
        return criptografar(texto, 26 - (chave % 26));
    }

    public static String[] forcaBruta(String textoCifrado) {
        String[] tentativas = new String[26];
        for (int i = 0; i < 26; i++) {
            tentativas[i] = descriptografar(textoCifrado, i);
        }
        return tentativas;
    }
}

