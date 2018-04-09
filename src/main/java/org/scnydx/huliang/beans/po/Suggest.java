package org.scnydx.huliang.beans.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: CSG
 * @Description: 意见反馈
 * @Date: Create in 14:53 2018/4/2
 * @Modify by:
 */
@Table(name = "tb_suggest")
public class Suggest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sugId;

    private String sugName;

    private String sugContent;

    private Date createTime;

    private Integer userId;

    private String remark;

    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    public String getSugName() {
        return sugName;
    }

    public void setSugName(String sugName) {
        this.sugName = sugName;
    }

    public String getSugContent() {
        return sugContent;
    }

    public void setSugContent(String sugContent) {
        this.sugContent = sugContent;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
