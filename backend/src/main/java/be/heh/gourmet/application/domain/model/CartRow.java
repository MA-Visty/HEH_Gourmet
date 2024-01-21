package be.heh.gourmet.application.domain.model;

public record CartRow(int ID, String name, float price, int quantity, float totalPrice) {

    public CartRow from(Product product, int quantity) {
        return new CartRow(product.ID(), product.name(), product.price(), quantity, product.price() * quantity);
    }

}