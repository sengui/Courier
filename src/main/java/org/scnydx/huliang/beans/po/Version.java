package org.scnydx.huliang.beans.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 14:12 2018/4/13
 * @Modify by:
 */
@Table(name = "tb_version")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date verTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVerTime() {
        return verTime;
    }

    public void setVerTime(Date verTime) {
        this.verTime = verTime;
    }
}
