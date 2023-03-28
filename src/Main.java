import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception{

        // fazer uma conexão HTTP e buscar os top 250 filmes da API do ImDB
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body(); // corpo da resposta

        // extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("Título: " + "\u001b[1m" + filme.get("title") + "\u001b[m");
            System.out.println("Poster: " + "\u001b[3m" + filme.get("image") + "\u001b[m");
            System.out.println("Classificação: " + filme.get("imDbRating"));
            double nClassificacao = Double.parseDouble(filme.get("imDbRating"));
            int nEstrelas = (int) nClassificacao;
            for (int i = 1; i <= nEstrelas; i++) {
                System.out.print("\u001b[46m⭐\u001b[m");
            }
            System.out.println("\n");
        }
    }
}