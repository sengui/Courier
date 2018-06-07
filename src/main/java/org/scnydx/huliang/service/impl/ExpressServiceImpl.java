package org.scnydx.huliang.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.scnydx.huliang.base.BaseServiceImpl;
import org.scnydx.huliang.beans.po.Company;
import org.scnydx.huliang.beans.po.Express;
import org.scnydx.huliang.beans.po.Logistics;
import org.scnydx.huliang.contants.BusiException;
import org.scnydx.huliang.contants.ResultCode;
import org.scnydx.huliang.dao.ICompanyDao;
import org.scnydx.huliang.dao.IExpressDao;
import org.scnydx.huliang.dao.ILogisticsDao;
import org.scnydx.huliang.service.IExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 15:49 2018/3/26
 * @Modify by:
 */
@Service
@Transactional(readOnly = true)
public class ExpressServiceImpl extends BaseServiceImpl<Express> implements IExpressService {

    @Autowired
    private IExpressDao expressDao;

    @Autowired
    private ILogisticsDao logisticsDao;

    @Autowired
    private ICompanyDao companyDao;

    @Value("${searchLogUrl}")
    private String searchLogUrl;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void UpdateExpLogInfo(String type) {
        List<Express> expressList = expressDao.findListByExpType(type);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Express express : expressList) {
            Company company = companyDao.selectByPrimaryKey(express.getComId());
            try {
                JsonNode jsonNode = getLogisticsInfo(express.getExpCode(), company.getComCode());
                if (jsonNode != null) {
                    JsonNode data = jsonNode.path("data");
                    //遍历物流信息
                    if (data.isArray()) {
                        List<Logistics> logisticsList = new ArrayList<Logistics>(data.size());
                        for (JsonNode item : data) {
                            Logistics logistics = new Logistics();
                            logistics.setLogInfo(item.path("context").asText());
                            logistics.setLogAddress(item.path("location").asText());
                            logistics.setCreateTime(sdf.parse(item.path("time").asText()));
                            logistics.setExpId(express.getExpId());
                            logisticsList.add(logistics);
                        }
                        logisticsDao.delLogisticsByExpId(express.getExpId());
                        if (logisticsList.size() > 0) {
                            logisticsDao.insertList(logisticsList);
                        }
                        //等待快件
                        if ("wait".equals(type) && data.size() > 0) {
                            express.setExpStatus("transit");
                            expressDao.updateByPrimaryKey(express);
                        }else if("transit".equals(type)){
                            JsonNode isCheck = jsonNode.path("ischeck");
                            //已到达
                            if ("1".equals(isCheck.asText().trim())) {
                                express.setExpStatus("arrival");
                                expressDao.updateByPrimaryKey(express);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Page<Map<String, Object>> findMyExpressList(Integer userId, String userPhone, String expStatus, String selectType, int pageIndex, int pageSize) throws BusiException {
        PageHelper.startPage(pageIndex, pageSize);
        if ("all".equals(selectType)) {
            return expressDao.findMyAllExpressList(userId, userPhone, expStatus);
        } else if ("send".equals(selectType)) {
            return expressDao.findMySendExpressList(userId, userPhone, expStatus);
        } else if ("receive".equals(selectType)) {
            return expressDao.findMyRecExpressList(userPhone, expStatus);
        } else {
            throw new BusiException(ResultCode.SELECTTYPE_ERROR);
        }
    }

    @Override
    public Map<String, Object> findExpressInfo(Integer expId) {
        return expressDao.findExpressInfoByExpId(expId);
    }

    @Override
    public Page<Map<String, Object>> findWaitExpressList(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return expressDao.findWaitExpressList();
    }

    @Override
    public Page<Map<String, Object>> findAllExpressPage(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return expressDao.findAllExpressPage();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendExpress(Express express) {
        expressDao.sendExpress(express);
    }

    /**
     * 获取物流信息
     * @param expCode 快递号
     * @param comCode 公司编码
     * @return
     */
    private JsonNode getLogisticsInfo(String expCode, String comCode) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(searchLogUrl);
        uriBuilder.addParameter("type",comCode);
        uriBuilder.addParameter("postid",expCode);

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        JsonNode jsonNode = null;
        HttpEntity entity =httpResponse.getEntity();

        if (statusCode == 200) {
            String string = EntityUtils.toString(entity,"utf-8");
            System.out.println(string);
            ObjectMapper mapper = new ObjectMapper();
            jsonNode = mapper.readTree(string);
        }

        //关闭httpclient
        httpResponse.close();
        httpClient.close();

        return jsonNode;
    }
}
