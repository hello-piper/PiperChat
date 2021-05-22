//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package piper.im.common.pojo.message;

import java.io.Serializable;

/**
 * 地址消息
 *
 * @author piper
 */
public class LocationMessageBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 地址
     */
    public String address;

    /**
     * 维度
     */
    public double latitude;

    /**
     * 经度
     */
    public double longitude;


    public LocationMessageBody(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
