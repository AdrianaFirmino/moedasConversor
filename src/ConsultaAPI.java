import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaAPI {

    private static final String API_KEY = "f735d9f2ae33c70946c77bd7";
    private static final String API_BASE = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    public String buscarTaxa(String moedaBase, String moedaAlvo) {

        String url = API_BASE + moedaBase + "/" + moedaAlvo;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Erro HTTP: Falha ao obter dados. Status Code: " + response.statusCode());
                System.err.println("Resposta da API: " + response.body());
                return null;
            }

        } catch (IOException e) {
            System.err.println("Erro de I/O: Não foi possível conectar à API.");
            return null;
        } catch (InterruptedException e) {
            System.err.println("Requisição interrompida.");
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
