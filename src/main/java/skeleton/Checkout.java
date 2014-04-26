package skeleton;

import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private Map<String,Integer> productPrices = new HashMap<String, Integer>();
    private int total = 0;

    public void setProducts(Map<String, Integer> products) {
        this.productPrices = products;
    }

    public void setQuantity(int quantityOfCroissant, String product) {
        total += quantityOfCroissant * productPrices.get(product);
    }

    public int getTotal() {
        return total;
    }

}
