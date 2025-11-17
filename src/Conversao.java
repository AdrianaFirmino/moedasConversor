import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Conversao {
    private double valor;
    private String moedaOrigem;
    private double valorConvertido;
    private String moedaDestino;
    private double taxa;
    private LocalDateTime dataHora;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Conversao(double valor, String moedaOrigem,
                     double valorConvertido, String moedaDestino, double taxa) {
        this.valor = valor;
        this.moedaOrigem = moedaOrigem;
        this.valorConvertido = valorConvertido;
        this.moedaDestino = moedaDestino;
        this.taxa = taxa;
        this.dataHora = LocalDateTime.now();
    }

    public Conversao(double valor, double valorConvertido, String moedaDestino, double taxa) {
    }


    @Override
    public String toString() {
        return String.format("[%s] Conversão: %.2f %s => %.2f %s (Taxa: %.4f)",
                dataHora.format(FORMATTER), valor, moedaOrigem,
                valorConvertido, moedaDestino, taxa);
    }
}

