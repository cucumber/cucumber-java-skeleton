package skeleton;

import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private final Map<String,Integer> productPrices = new HashMap<String, Integer>();
    private int total = 0;

    public void setPriceOfEspresso(int priceOfEspresso) {
        productPrices.put("espresso", priceOfEspresso);
    }

    public void setPriceOfCroissant(int priceOfCroissant) {
        productPrices.put("croissant", priceOfCroissant);
    }

    public void setQuantityOfEspresso(int quantityOfEspresso) {
        total += quantityOfEspresso * productPrices.get("espresso");
    }

    public void setQuantityOfCroissant(int quantityOfCroissant) {
        total += quantityOfCroissant * productPrices.get("croissant");
    }

    public int getTotal() {
        return total;
    }

}
