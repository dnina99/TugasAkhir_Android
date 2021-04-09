package umn.ac.id.tugasakhir_android.Model;

public class Food {

    private String Name, Image, description, price, discount, MenuId;

    public Food() {

    }

    public Food(String description, String discount, String image, String MenuId, String name, String price) {
        this.Name = name;
        this.Image = image;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.MenuId = MenuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String MenuId) {
        this.MenuId = MenuId;
    }
}
