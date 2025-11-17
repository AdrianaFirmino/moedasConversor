public class TaxaDeCambio {


    private String result;
    private String base_code;
    private String target_code;
    private double conversion_rate; // O campo chave para a conversão


    public TaxaDeCambio() {}


    public double getConversionRate() {
        return conversion_rate;
    }

    public String getResult() {
        return result;
    }

    public String getBase_code(){return base_code;}

    public String getTarget_code() {
        return target_code;
    }

    @Override
    public String toString() {
        return String.format("Taxa de 1 %s para %s = %.4f", base_code, target_code, conversion_rate);
    }


}

