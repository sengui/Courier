package org.scnydx.huliang.beans.po;

import org.scnydx.huliang.mappers.Like;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: CSG
 * @Description: 公司
 * @Date: Create in 10:39 2018/3/26
 * @Modify by:
 */
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comId;

    @Like
    private String comName;

    private String comInfo;

    private String comCode;

    private String remark;

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComInfo() {
        return comInfo;
    }

    public void setComInfo(String comInfo) {
        this.comInfo = comInfo;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
