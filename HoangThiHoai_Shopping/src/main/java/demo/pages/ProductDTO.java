package demo.pages;

import java.util.Objects;

public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String buttonText;

    public ProductDTO(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(String name, String description, double price, String imageUrl, String buttonText) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.buttonText = buttonText;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getButtonText() { return buttonText; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Double.compare(that.price, price) == 0 &&
               Objects.equals(name, that.name) &&
               Objects.equals(description, that.description) &&
               Objects.equals(imageUrl, that.imageUrl) &&
               Objects.equals(buttonText, that.buttonText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, imageUrl, buttonText);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", imageUrl='" + imageUrl + '\'' +
               ", buttonText='" + buttonText + '\'' +
               '}';
    }
}
