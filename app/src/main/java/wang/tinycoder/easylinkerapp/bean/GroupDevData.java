package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangYh
 * @version V1.0
 * @Name: GroupDevData
 * @Package wang.tinycoder.easylinkerapp.bean
 * @Description: 分组的设备数据
 * @date 2018/5/4 0004
 */
public class GroupDevData implements Serializable {

    /**
     * isFirst : true
     * total : 1
     * size : 10
     * data : [{"name":"device_Auto_3","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319410672,"describe":"Product_3","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA6ElEQVR42u3WwQ6CQAwE0N76y721t/7yHkzWmWIE8cb2pBBi4JlQbGdBmV/bQ277eQsRzRBPHjRYYh8anlnH6xaar0LaZnPobLThOduMe5jPU1+uWv18bue5XbRZ9208/8jVVQtDDwDHma8Y8zNMh44qs2zsgsYm2WBDzAMJQtLfPV2xyRsmh+91FwzjMUchcWCHoZ1IED4wrQbLujSXIcp0GMNtJiL7zFesMo5vOCRvsFp6DDqKaYNtS4X9PPR0xfDoQlsHemDeZIi3sYg2GWJepVqMjFceY95hnBEND27PBrv/0/yXPQF0iJDRTCF7RwAAAABJRU5ErkJggg=="},{"name":"device_Auto_6","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319405879,"describe":"Product_6","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA2klEQVR42u3WMY7EMAgFUDquTAcdV6YYieXbUbKb7QLVzFgp7FfEDv5Cofw3XvS1tzcj4kz3Y9I2B5Swr3nf9sp0T0bMSIQHLUpyytaBl+aE4Woc43ZvTw0jSPH+P7l6akZspFGnFtEBCz7uPfTco2WK/EA9eMJEa14V5euOekYclckVygEztg1y1aBjXkVwnPx3j+iYoQa1VXDoiBGWQjUmLI9ccqKkfcOnI5JojD5gfrbZK5MtQ7r9Xpee7XOb8JStGuAZsMWIuuaE7R67etit7z6z7z/NZ9kPuBWRhYs3evgAAAAASUVORK5CYII="},{"name":"device_Auto_1","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319392802,"describe":"Product_1","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA3UlEQVR42u3WsQ6DMAwE0Gz+ZW/25l/2UMm9M12gG/HUEiEUHkPgHCxWfY3XeuznzdcSnCx6sm+BIyUwer5vLlEpbj2ZMWINmisup6wPPPIll7vG0jDRa93uWo9Ama776qa55VJL42zElqWqpCQfed9QG4bAyqcMGEjViGkxYHj7wNs7dnlNmCtDkF6pBiyYKu7YqUdsGKujuEosNmILDQyAMWFVnyZW/Ar3rb9BNgmWfcDYGrrhINcRw+5GqOdcdk2BWESG7Gg7Heq+kRkptuWEHTViy/YRe/5p/sveS8uXJW3qkKsAAAAASUVORK5CYII="},{"name":"device_Auto_2","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319365766,"describe":"Product_2","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA30lEQVR42u2WsQ7DMAhE2fhlb7DxywyV3DsUdXC3wNQGRRF+kWx0h1Fkf8VLHvbzzEUUL4tK+izwpAai8j5zjZ3mVskMA9Q9yHxhOcXqQcmHLncZraGip293WYUbRDj66ibDUpalMZtgqW7QNDVZcp9FGvuRzqcOsM3dFzsSHwcYKhYwR+l7grF3oIHLWh9NW6zaPJcdM+I289obWiyxCYaBwzMEMcH2xaPU6LO6g1n30HWAlaYCz+nUBOPoikOXLkOX4wDXIcbLd4naZ1zSI/TSBCuPamT7CHv+af6LvQGl2aeNlGqshwAAAABJRU5ErkJggg=="},{"name":"device_Auto_5","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319364268,"describe":"Product_5","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA4klEQVR42u2WsQ7DMAhE2fhlNtj4ZYZK7kGGtOkW39TWsizneTjEgR1ZH+Mhf/b1LEQ0Qzx7Q2CJWRqeOft9FppYWkhprEwXkZXnorGeyOm65OUuG496XH27ydbEbf39Vld3WRhyAPDq+RZTCJiW1sjsMw/FwZAksJwKwpp19swOKxExCIWfGlsMISML4oAEBo+6oQs9aEpgfd9Eu1OQYbDuGIQscnq+w6bGcdImOYFN69X0YSiBHXeEWTtFYbi64PZRSSQGn0ZESazMR4rCGndRigmDHW9oP3nhSWD/f5rfYk+sloINecDPvgAAAABJRU5ErkJggg=="},{"name":"device_Auto_7","lastActiveDate":"2018-05-03T03:48:59.000+0000","isOnline":false,"location":"{\"latitude\":\"厦门科技园\"}","id":1525319362830,"describe":"Product_7","barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA5UlEQVR42u3WsQ6EMAgGYDZeuRtsvDLDJdz/V3NRb7NMdzaa1M/EGqCo1Nd4yWM/by6iVRH7ZNmCEOEac75u25XbNmkx1xzaaJTqsvnCU6vDmJqYUT3n7a5xJK7iUld3zUVdLPHWY1iDJfJjDGfaZ40VwwLIDxMUqQ2WYsWEz53TYHhypCAALMoGQ6qHwX0cY3Df2BwQVBzHHrFgfLgrtqGmdRgAgRiC0WG1xRU3GNJ12/dgzbPB2BpQR5TUDmN1X+OyaD6wsdWHNlnMDwuPBptlxCWsOow5QkGyh5367l17/mn+y95dCY3FggalPAAAAABJRU5ErkJggg=="}]
     * isLast : true
     * totalPages : 1
     * page : 0
     * totalElements : 6
     */

    private boolean isFirst;
    private int total;
    private int size;
    private boolean isLast;
    private int totalPages;
    private int page;
    private int totalElements;
    private List<Device> data;

    public boolean isIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<Device> getData() {
        return data;
    }

    public void setData(List<Device> data) {
        this.data = data;
    }

}
