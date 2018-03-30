package org.scnydx.huliang.beans.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: CSG
 * @Description: 订单
 * @Date: Create in 10:40 2018/3/26
 * @Modify by:
 */
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private String orderName;

    private String sendUserName;

    private String sendUserPhone;

    private String sendUserArea;

    private String sendUserAddress;

    private String recUserName;

    private String recUserPhone;

    private String recUserArea;

    private String recUserAddress;

    private String orderType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer userId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendUserPhone() {
        return sendUserPhone;
    }

    public void setSendUserPhone(String sendUserPhone) {
        this.sendUserPhone = sendUserPhone;
    }

    public String getSendUserArea() {
        return sendUserArea;
    }

    public void setSendUserArea(String sendUserArea) {
        this.sendUserArea = sendUserArea;
    }

    public String getSendUserAddress() {
        return sendUserAddress;
    }

    public void setSendUserAddress(String sendUserAddress) {
        this.sendUserAddress = sendUserAddress;
    }

    public String getRecUserName() {
        return recUserName;
    }

    public void setRecUserName(String recUserName) {
        this.recUserName = recUserName;
    }

    public String getRecUserPhone() {
        return recUserPhone;
    }

    public void setRecUserPhone(String recUserPhone) {
        this.recUserPhone = recUserPhone;
    }

    public String getRecUserArea() {
        return recUserArea;
    }

    public void setRecUserArea(String recUserArea) {
        this.recUserArea = recUserArea;
    }

    public String getRecUserAddress() {
        return recUserAddress;
    }

    public void setRecUserAddress(String recUserAddress) {
        this.recUserAddress = recUserAddress;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static void main(String[] args) {
        Date date = new Date(1522402569);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }
}
