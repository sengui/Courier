import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * @Author: CSG
 * @Description:
 * @Date: Create in 14:49 2018/3/26
 * @Modify by:
 */
public class HttpTest {

    @Test
    public void doGet() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://api.kuaidi100.com/api");
        //uriBuilder.setScheme("api");
        //uriBuilder.setHost("http://api.kuaidi100.com");
        uriBuilder.addParameter("id","b2aae5c018bb0d19");
        uriBuilder.addParameter("com","yunda");
        uriBuilder.addParameter("nu","3831751624028");

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity =httpResponse.getEntity();

        String string = EntityUtils.toString(entity,"utf-8");

        System.out.println(string);

        //关闭httpclient

        httpResponse.close();

        httpClient.close();
    }
}
