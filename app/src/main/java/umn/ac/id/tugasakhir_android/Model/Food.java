package umn.ac.id.tugasakhir_android.Model;

public class Food {

    private String Name, Image, Description, Price, discount, MenuId;

    public Food() {

    }

    public Food(String description, String discount, String image, String MenuId, String name, String price) {
        this.Name = name;
        this.Image = image;
        this.Description = description;
        this.Price = price;
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
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
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
