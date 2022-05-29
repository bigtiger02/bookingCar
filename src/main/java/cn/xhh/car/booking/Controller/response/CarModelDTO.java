package cn.xhh.car.booking.Controller.response;

import cn.xhh.car.booking.entity.CarModel;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public class CarModelDTO {

    private Long id;
    private String name;
    private Integer stock;

    public CarModelDTO() {
    }

    public CarModelDTO(CarModel carModel) {
        this.id = carModel.getId();
        this.name = carModel.getName();
        this.stock = carModel.getStockAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
