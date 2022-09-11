package mhl.domain;

/**
 * @author Carson
 * @Version
 */
public class Menu {
    private Integer id;
    private String name;
    private String type;
    private Integer price;

    public Menu() {
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return id+"\t\t\t"+name+"\t\t"+type+"\t\t"+price;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }


}
