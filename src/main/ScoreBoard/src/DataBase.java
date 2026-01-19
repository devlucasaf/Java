public class DataBase {
    // Variáveis estáticas para armazenar os nomes dos jogadores
    static String playerOne;  // Nome do primeiro jogador
    static String playerTwo;  // Nome do segundo jogador

    // Variáveis para controle de sets e pontos atuais (propósito não totalmente claro)
    static int set;   // Possivelmente representa o set atual em andamento
    static int point; // Possivelmente representa o ponto atual em jogo

    // Contadores de sets ganhos por cada jogador
    static int setPointPlayerOne = 0;  // Quantidade de sets ganhos pelo jogador 1
    static int setPointPlayerTwo = 0;  // Quantidade de sets ganhos pelo jogador 2

    // Contadores de pontos dentro do set atual para cada jogador
    static int pointPlayerOne = 0;  // Pontos do jogador 1 no set atual
    static int pointPlayerTwo = 0;  // Pontos do jogador 2 no set atual
}
