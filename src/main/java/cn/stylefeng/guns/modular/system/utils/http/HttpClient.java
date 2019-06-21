package cn.stylefeng.guns.modular.system.utils.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    /**
     * 缺省超时时间 单位：ms
     */
    private static final int TIMEOUT = 30000;
    /**
     * 是否重定向标识
     */
    private static final boolean IS_REDIRECTS = false;
    private static String EMPTY_STR = "";
    /**
     * 字符集编码格式
     */
    private static String UTF_8 = "UTF-8";

    private static int TRY_TIMES = 8;

    private static int timeout_array[] = {1000,2000,4000,6000,8000,10000,12000,15000,20000};

    private HttpClient() {
    }

    /**
     * 发送 get 请求
     *
     * @param url 请求地址
     * @return String
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet, null);
    }

    /**
     * 发送 get 请求
     *
     * @param url     请求地址
     * @param headers 头信息
     * @return String
     */
    public static String httpGetRequestWithHeaders(String url, Map<String, Object> headers) {
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return execute(httpGet, null);
    }

    /**
     * 发送 get 请求
     *
     * @param url     请求地址
     * @param headers 头信息
     * @param params  参数
     * @return String
     */
    public static String httpGetRequest(String url, Map<String, Object> headers,
                                        Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(createParamUrl(url, params));
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return execute(httpGet, null);
    }

    /**
     * 发送 get 请求
     *
     * @param url 请求地址
     * @return String
     */
    public static String httpGetRequestWithParams(String url, Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(createParamUrl(url, params));
        return execute(httpGet, null);
    }

    /**
     * 创建带参数的 URL
     *
     * @param url    无参URL
     * @param params 参数
     * @return String 带参数URL
     */
    private static String createParamUrl(String url, Map<String, Object> params) {
        Iterator<String> it = params.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        boolean isIncludeQuestionMark = url.contains("?");
        if (!isIncludeQuestionMark) {
            sb.append("?");
        }
        while (it.hasNext()) {
            String key = it.next();
            String value = (String) params.get(key);
            sb.append("&");
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        url += sb.toString();
        return url;
    }

    /**
     * 发送 post 请求
     *
     * @param url 请求地址
     * @return String
     */
    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return execute(httpPost, null);
    }

    /**
     * 发送 post 请求
     *
     * @param url    地址
     * @param params 参数
     * @return String
     */
    public static String httpPostRequest(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return execute(httpPost, null);
    }

    /**
     * 发送 post 请求
     *
     * @param url     地址
     * @param headers 头信息
     * @param params  参数
     * @return String
     */
    public static String httpPostRequest(String url, Map<String, Object> headers,
                                         Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, Object> headerParam : headers.entrySet()) {
            httpPost.addHeader(headerParam.getKey(), String.valueOf(headerParam.getValue()));
        }
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return execute(httpPost, null);
    }

    /**
     * 发送 post 请求
     *
     * @param url     地址
     * @param headers 头信息
     * @param json    json 格式参数
     * @return String
     */
    public static String httpPostRequestByJson(String url, Map<String, Object> headers,
                                               String json) {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, Object> headerParam : headers.entrySet()) {
            httpPost.addHeader(headerParam.getKey(), String.valueOf(headerParam.getValue()));
        }
        try {
            httpPost.setEntity(new StringEntity(json, UTF_8));
        } catch (UnsupportedCharsetException e) {
            e.printStackTrace();
        }
        return execute(httpPost, null);
    }

    /**
     * 发送 post 请求
     *
     * @param url  地址
     * @param json json 格式参数
     * @return String
     */
    public static String sendPost(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        try {
            String referer = "";
            StringEntity entity = new StringEntity(json, UTF_8);
            entity.setContentType("application/json");
            httpPost.setHeader("Referer",referer);
            logger.info("add Referer: "+referer);
            httpPost.setEntity(entity);
        } catch (UnsupportedCharsetException e) {
            logger.error("http请求异常", e);
        }
        return execute(httpPost, null);
    }

    public static String sendXMLPost(String url, String json, String charset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(json, charset);
            entity.setContentType("text/xml");
            httpPost.setEntity(entity);
        } catch (UnsupportedCharsetException e) {
            logger.error("http请求异常", e);
        }
        return execute(httpPost, charset);
    }

    /**
     * 把参数转换为名值对数组
     *
     * @param params 参数
     * @return ArrayList<NameValuePair>
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 执行 HTTP 请求 若重定向返回重定向地址
     *
     * @return String
     */
    private static String execute(HttpRequestBase request, String charset) {
        String result = EMPTY_STR;
        request.setConfig(createConfig(TIMEOUT, IS_REDIRECTS));
        CloseableHttpClient httpClient = getHttpClient();
        try {
            logger.info("httpclient,{}", httpClient);
            CloseableHttpResponse response = httpClient.execute(request);
            logger.info("httpclient,{}", response);
            if (isRedirected(response)) {
                result = getRedirectedUrl(response);
            } else {
                result = getEntityData(response, charset);
            }
        } catch (Exception e) {
            logger.error("http请求异常", e);
        }
        return result;
    }

    /**
     * 创建HTTP请求配置
     *
     * @param timeout          超时时间
     * @param redirectsEnabled 是否开启重定向
     * @return RequestConfig
     */
    private static RequestConfig createConfig(int timeout, boolean redirectsEnabled) {
        return RequestConfig.custom()
                // 读取数据超时时间（毫秒）
                .setSocketTimeout(timeout)
                // 建立连接超时时间（毫秒）
                .setConnectTimeout(timeout)
                // 从连接池获取连接的等待时间（毫秒）
                .setConnectionRequestTimeout(timeout)
                // 当响应状态码为302时，是否进行重定向
                .setRedirectsEnabled(redirectsEnabled)

                .build();
    }

    /**
     * 通过连接池获取 httpclient
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setRetryHandler(getHttpRequestRetryHandler()).setConnectionManager(
                HttpConnectionManager.POOLING_CONNECTION_MANAGER).build();

    }

    /**
     * 获取重试控制器
     *
     * @return
     */
    private static HttpRequestRetryHandler getHttpRequestRetryHandler() {
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exp, int retryTimes, HttpContext context) {
                logger.info("retryRequest trigger...retryTimes="+retryTimes);
                if (retryTimes > TRY_TIMES) {
                    return false;
                }
                try {
                    Thread.sleep(timeout_array[retryTimes]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        };
    }

    /**
     * 判断发送请求是否重定向跳转过
     *
     * @param response 请求响应
     * @return boolean
     */
    private static boolean isRedirected(CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("httpclient-statusCode,{}", statusCode);
        return statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY;
    }

    /**
     * 获得重定向跳转地址
     *
     * @param response 请求响应
     * @return String 重定向地址
     */
    private static String getRedirectedUrl(CloseableHttpResponse response) {
        String result = EMPTY_STR;
        Header[] hs = response.getHeaders("Location");
        if (hs.length > 0) {
            result = hs[0].getValue();
        }
        return result;
    }

    /**
     * 获得响应实体信息
     *
     * @param response 请求响应
     * @return String 消息实体信息
     */
    private static String getEntityData(CloseableHttpResponse response, String charset)
            throws ParseException, IOException {
        String result = EMPTY_STR;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            if (charset == null) {
                result = EntityUtils.toString(entity);
            } else {
                result = EntityUtils.toString(entity, charset);
            }
            response.close();
        }
        return result;
    }

   /* public static void main(String[] args) {
        String url = "http://cha.yeepay.com/app-merchant-proxy/groupTransferController.action";
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>\n" +
                "<data>\n" +
                "    <cmd>TransferSingle</cmd>\n" +
                "    <version>1.1</version>\n" +
                "    <mer_Id>10023286760</mer_Id>\n" +
                "    <group_Id>10023286760</group_Id>\n" +
                "    <batch_No>20180718194255005154</batch_No>\n" +
                "    <order_Id>72_20180718194255005154</order_Id>\n" +
                "    <bank_Code>CMBC</bank_Code>\n" +
                "    <cnaps></cnaps>\n" +
                "    <bank_Name></bank_Name>\n" +
                "    <branch_Bank_Name></branch_Bank_Name>\n" +
                "    <amount>15.00</amount>\n" +
                "    <account_Name>臧阳</account_Name>\n" +
                "    <account_Number>6226220132701003</account_Number>\n" +
                "    <product></product>\n" +
                "    <accountType>pr</accountType>\n" +
                "    <province></province>\n" +
                "    <city></city>\n" +
                "    <fee_Type>SOURCE</fee_Type>\n" +
                "    <payee_Email></payee_Email>\n" +
                "    <payee_Mobile></payee_Mobile>\n" +
                "    <urgency>1</urgency>\n" +
                "    <leave_Word></leave_Word>\n" +
                "    <abstractInfo></abstractInfo>\n" +
                "    <remarksInfo></remarksInfo>\n" +
                "    <hmac>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</hmac>\n" +
                "</data>\n";
        String json = HttpClient.sendXMLPost(url, xml, "GBK");
        EBPayResponse response = null;
        response = XmlUtil.convertToBean(json, EBPayResponse.class);

        System.out.println(response.getErrorMsg());
    }*/
   public static void main(String[] args) {
       HttpClient.sendPost("http://docs.futurepay.vip:8090/pages/viewpage.action?pageId=1279004","");
   }
}
