import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;

public class ConversorDeMoedas {

    private static final String[] MOEDAS_DISPONIVEIS = {
            "ARS", "BOB", "BRL", "CLP", "COP", "USD", "EUR", "GBP", "JPY", "CAD"
    };

    private static final ConsultaAPI CONSULTA_API = new ConsultaAPI();
    private static final List<Conversao> HISTORICO = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.println("=== Bem-vindo ao Conversor de Moedas Dinâmico! ===");
        System.out.print("Por favor, digite seu nome para começarmos: ");
        String nomeUsuario = scanner.nextLine().trim();

        System.out.println("Olá, " + nomeUsuario + "! É um prazer tê-lo por aqui.");

        do {
            exibirMenuPrincipal();
            try {
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        realizarConversaoDinamica(scanner);
                        break;
                    case 2:
                        exibirHistorico();
                        break;
                    case 3:
                        System.out.println("Até logo, " + nomeUsuario + "! Obrigado por utilizar o Conversor de Moedas.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        opcao = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                opcao = 0;
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1: Realizar Conversão (código da moeda)");
        System.out.println("2: Ver Histórico de Conversões");
        System.out.println("3: Sair");
        exibirMoedasDisponiveis();
    }

    private static void exibirMoedasDisponiveis() {
        System.out.println("\n--- Moedas Disponíveis (Códigos) ---");
        String listaMoedas = String.join(", ", MOEDAS_DISPONIVEIS);
        System.out.println(listaMoedas);
    }

    private static void realizarConversaoDinamica(Scanner scanner) {
        System.out.print("\nDigite o CÓDIGO da moeda de ORIGEM (ex: USD): ");
        String moedaOrigem = scanner.nextLine().trim().toUpperCase();

        System.out.print("Digite o CÓDIGO da moeda de DESTINO (ex: BRL): ");
        String moedaDestino = scanner.nextLine().trim().toUpperCase();

        System.out.print("Digite o VALOR em " + moedaOrigem + " para converter: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        String jsonResposta = null;

        try {
            jsonResposta = CONSULTA_API.buscarTaxa(moedaOrigem, moedaDestino);
            if (jsonResposta != null) {
                Gson gson = new Gson();
                TaxaDeCambio taxaDeCambio = gson.fromJson(jsonResposta, TaxaDeCambio.class);

                double taxa = taxaDeCambio.getConversionRate();
                double valorConvertido = valor * taxa;

                DecimalFormat df = new DecimalFormat("#,##0.00");

                Conversao conversao = new Conversao(valor, moedaOrigem, valorConvertido, moedaDestino, taxa);
                HISTORICO.add(conversao);

                System.out.println("\n--- Resultado da Conversão ---");
                System.out.printf("Moeda de Origem: %s\n", moedaOrigem);
                System.out.printf("Moeda de Destino: %s\n", moedaDestino);
                System.out.printf("Taxa de Câmbio: 1 %s = %.4f %s\n", moedaOrigem, taxa, moedaDestino);
                System.out.printf("Valor Convertido: %.2f %s\n", valorConvertido, moedaDestino);
            } else {
                System.out.println("Erro: Não foi possível obter a taxa de câmbio. Verifique os códigos das moedas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada para conversão inválida. Digite um número real.");
            scanner.nextLine();
        } catch (JsonSyntaxException e) {
            System.out.println("Erro ao processar resposta JSON. Verifique os códigos das moedas e tente novamente.");
        }
    }

    private static void exibirHistorico() {
        if (HISTORICO.isEmpty()) {
            System.out.println("\nO histórico está vazio. Realize uma conversão primeiro.");
        } else {
            System.out.println("\n--- Histórico de Conversões ---");
            for (Conversao historico : HISTORICO) {
                System.out.println(historico.toString());
            }
        }
    }
}