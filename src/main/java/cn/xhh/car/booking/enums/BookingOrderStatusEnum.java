package cn.xhh.car.booking.enums;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public enum BookingOrderStatusEnum {
    BOOKING(0, "预定中"),
    FINISH(100, "已完成");

    private int code;
    private String msg;

    BookingOrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
