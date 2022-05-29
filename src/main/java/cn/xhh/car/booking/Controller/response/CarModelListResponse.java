package cn.xhh.car.booking.Controller.response;

import java.util.List;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public class CarModelListResponse {

    private List<CarModelDTO> list;

    public CarModelListResponse() {
    }

    public CarModelListResponse(List<CarModelDTO> list) {
        this.list = list;
    }

    public List<CarModelDTO> getList() {
        return list;
    }

    public void setList(List<CarModelDTO> list) {
        this.list = list;
    }
}
