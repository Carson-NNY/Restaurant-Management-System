package mhl.domain;

/**
 * this javabean reflects the diningtable
 */
public class DiningTable {
    private Integer id;
    private String state;
    private String orderName;
    private String orderTel;

    public DiningTable() {
    }

    @Override
    public String toString() {
        return id + "\t\t\t"+state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setOrderTel(String orderTel) {
        this.orderTel = orderTel;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderTel() {
        return orderTel;
    }
}
