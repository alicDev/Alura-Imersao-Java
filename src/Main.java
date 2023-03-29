import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception{

        // fazer uma conexão HTTP e buscar os top 250 filmes da API do ImDB
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body(); // corpo da resposta

        // extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        var geradora = new GeradorDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textoFigurinha;
            if (classificacao >= 8.0) {
                textoFigurinha = "TOPZERA";
            } else if (classificacao >= 7.0){
                textoFigurinha = "MEIO PAIA";
            } else {
                textoFigurinha = "PAIA";
            }

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            System.out.println("Título: " + "\u001b[1m" + titulo + "\u001b[m");

            geradora.cria(inputStream, nomeArquivo, textoFigurinha);

            System.out.println("Classificação: " + filme.get("imDbRating"));
            double nClassificacao = classificacao;
            int nEstrelas = (int) nClassificacao;
            for (int i = 1; i <= nEstrelas; i++) {
                System.out.print("\u001b[46m⭐\u001b[m");
            }
            System.out.println("\n");
        }
    }
}