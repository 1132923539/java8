package net.java8.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;


/**
 * @author eltons,  Date on 2018-09-17.
 */
public class Http1 {

    @Test
    public void test1() {
        String reqUrl = "http://192.168.229.129:10083/users.json";

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(reqUrl);

        getMethod.setRequestHeader("Authorization", "Basic YWRtaW46MTIzNDU2Nzg=");

        int status;
        try {
            status = httpClient.executeMethod(getMethod);
            String response = getMethod.getResponseBodyAsString();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test2() throws UnsupportedEncodingException {
        String reqUrl = "http://192.168.229.129:10083/users.json";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(reqUrl);

        postMethod.setRequestHeader("Authorization", "Basic YWRtaW46MTIzNDU2Nzg=");
//        postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(20);
//        map.put("name", "DevOpsaaa");
//        map.put("identifier", "lululuul");
//        map.put("description", "这是一个好项目");
//        map.put("homepage", "www.baidu.com");
//        map.put("is_public", "true");
//        map.put("parent_id", 1);
//        map.put("inherit_members", "true");
//        map.put("tracker_ids", "");
//        map.put("enabled_module_names", "");
//        map.put("issue_custom_field_ids", "");

        map.put("login", "aaaaaa");
        map.put("password", "12345678");
        map.put("firstname", "lul");
        map.put("lastname", "u");
        map.put("mail", "34545@qq.com");

        JSONObject json = new JSONObject(map);
        String str = json.toJSONString();
        System.out.println(str);
        postMethod.setParameter("user", str);

        try {
            int i = httpClient.executeMethod(postMethod);
            String response = postMethod.getResponseBodyAsString();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    //成功发送post请求
    public void test3() throws IOException {
        String reqUrl = "http://192.168.229.129:10083/users.json";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(reqUrl);

        HashMap<String, Object> params = new HashMap<>(20);
        params.put("login", "cccc");
        params.put("password", "12345678");
        params.put("firstname", "lulu");
        params.put("lastname", "lu");
        params.put("mail", "cccc@qq.com");

        //构建json串
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("user", params);
        JSONObject json = new JSONObject(map1);

        httpPost.setHeader("Authorization", "Basic YWRtaW46MTIzNDU2Nzg=");
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");

        StringEntity stringEntity = new StringEntity(json.toJSONString(), Charset.forName("utf-8"));
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        byte[] bytes = new byte[10240];
        //得到响应内容
        response.getEntity().getContent().read(bytes);
        System.out.println(new String(bytes));

    }

    @Test
    public void test4() throws IOException {
        String reqUrl = "http://192.168.229.129:10083/users.json";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(reqUrl);

        httpGet.setHeader("Authorization", "Basic YWRtaW46MTIzNDU2Nzg=");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        byte[] bytes = new byte[10240];
        response.getEntity().getContent().read(bytes);
        System.out.println(new String(bytes));
    }
}
