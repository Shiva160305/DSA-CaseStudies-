import java.util.*;

public class BPlusTreeDemo {

    static class Product {
        String category;
        int price;

        Product(String category, int price) {
            this.category = category;
            this.price = price;
        }
    }

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();

        products.add(new Product("Electronics", 12000));
        products.add(new Product("Electronics", 13000));
        products.add(new Product("Electronics", 14500));
        products.add(new Product("Electronics", 15000));
        products.add(new Product("Electronics", 14800));

        int low = 12000;
        int high = 14800;

        System.out.println("Products in Range:");

        for(Product p : products) {
            if(p.price >= low && p.price <= high) {
                System.out.println(p.category + " - ₹" + p.price);
            }
        }
    }
}
