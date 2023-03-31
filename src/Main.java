import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception{

        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        String url = "https://api.nasa.gov/planetary/apod?api_key=kgDNmpmSFITKwIWEaZg8DRCNpOeCsMear3mmVJIj&start_date=2022-06-12&end_date=2022-06-14";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        // exibir e manipular os dados

        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();
        var geradora = new GeradorDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.urlImage()).openStream();

            String nomeArquivo = "figurinhas/" + conteudo.titulo() + ".png";
            geradora.cria(inputStream, nomeArquivo, "Alura");

            System.out.println("Título: " + "\u001b[1m" + conteudo.titulo() + "\u001b[m");
            System.out.println();

        }
    }
}