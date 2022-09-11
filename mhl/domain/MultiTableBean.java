package mhl.domain;

import java.util.Date;

/**
 *  this javabean can reflects multiple tables
 */
public class MultiTableBean {
    // from bill table
    private Integer id;
    private String billId;
    private Integer menuId;
    private Double money;
    private Integer nums;
    private Integer diningTableId;
    private Date billDate;
    private String state;
    // add menu
    private String name;
    private Double price;

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public MultiTableBean() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getBillId() {
        return billId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public Double getMoney() {
        return money;
    }

    public Integer getNums() {
        return nums;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return   id +"\t\t"  + menuId +"\t\t"+  money +"\t\t\t"+ nums +"\t\t"
                +  diningTableId +"\t\t"+  billDate +"\t\t"+ state+"\t\t"+name+"\t\t"+price;
    }
}
