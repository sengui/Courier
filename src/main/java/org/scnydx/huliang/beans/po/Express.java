package org.scnydx.huliang.beans.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: CSG
 * @Description: 快件
 * @Date: Create in 10:49 2018/3/26
 * @Modify by:
 */
@Table(name = "tb_express")
public class Express {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expId;

    private String expName;

    private String expStatus;

    private Integer orderId;

    public Integer getExpId() {
        return expId;
    }

    public void setExpId(Integer expId) {
        this.expId = expId;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpStatus() {
        return expStatus;
    }

    public void setExpStatus(String expStatus) {
        this.expStatus = expStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
