package service;


import java.math.BigDecimal;
import java.util.Date;

public class CalculationService {

    private JsonRequestService requestService;

    private final String SPREAD = "0.005";

    public CalculationService() throws Exception {
        requestService = new JsonRequestService();
    }

    public String calculateDifference(Date date, String stringAmount) throws Exception {
        BigDecimal currentPrice = new BigDecimal(requestService.getUsdPrice());
        BigDecimal historyPrice = new BigDecimal(requestService.getUsdPrice(date));
        BigDecimal amount = new BigDecimal(stringAmount);
        BigDecimal spread = new BigDecimal(SPREAD);

        BigDecimal startCapital;
        startCapital = historyPrice.multiply(amount);
        startCapital = startCapital.add(startCapital.multiply(spread));

        BigDecimal resultCapital;
        resultCapital = currentPrice.multiply(amount);
        resultCapital = resultCapital.subtract(resultCapital.multiply(spread));

        return resultCapital.subtract(startCapital).toString();
    }
}
