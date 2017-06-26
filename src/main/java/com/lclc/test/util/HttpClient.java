package com.lclc.test.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

public class HttpClient {

    // 日志
    private static Log log = LogFactory.getLog(HttpClient.class);

    // 连接超时时间
    private static final int CONNECTION_TIMEOUT_MS = 360000;

    // 读取数据超时时间
    private static final int SO_TIMEOUT_MS = 360000;

    // httpclient读取内容时使用的字符集
    private static final String CONTENT_CHARSET = "UTF-8";

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @SuppressWarnings("unused")
    private static final Charset GBK = Charset.forName(CONTENT_CHARSET);

    // 内容类型
    private static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=utf-8";

    private static final String CONTENT_TYPE_XML_CHARSET = "application/xml;charset=utf-8";

    /**
     * 简单get调用
     * 
     * @param url
     *            请求路径
     * @param params
     *            参数列表
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String simpleGetInvoke(String url, Map<String, String> params)
            throws ClientProtocolException, IOException, URISyntaxException {

        return simpleGetInvoke(url, params, CONTENT_CHARSET);
    }

    /**
     * 简单get调用
     * 
     * @param url
     *            请求路径
     * @param params
     *            参数列表
     * @param charset
     *            编码方式
     * 
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String simpleGetInvoke(String url, Map<String, String> params, String charset)
            throws ClientProtocolException, IOException, URISyntaxException {

        CloseableHttpClient client = buildHttpClient(false, null);

        HttpGet get = buildHttpGet(url, params);

        HttpResponse response = client.execute(get);

        assertStatus(response);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            log.info(returnStr);
            return returnStr;
        }
        return null;
    }

    /**
     * 简单post调用
     * 
     * @param url
     *            请求路径
     * @param params
     *            参数列表
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String simplePostInvoke(String url, Map<String, String> params)
            throws URISyntaxException, ClientProtocolException, IOException {

        return simplePostInvoke(url, params, CONTENT_CHARSET);
    }

    /**
     * 简单post调用
     * 
     * @param url
     *            请求路径
     * @param params
     *            参数列表
     * @param charset
     *            编码方式
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String simplePostInvoke(String url, Map<String, String> params, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {

        CloseableHttpClient client = buildHttpClient(false, null);

        HttpPost postMethod = buildHttpPost(url, params, null);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            log.info(returnStr);
            return returnStr;
        }

        return null;
    }

    /**
     * Json请求体Post请求
     * 
     * @param url
     *            请求路径
     * @param json
     *            json参数
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String jsonPostInvoke(String url, String json)
            throws ClientProtocolException, URISyntaxException, IOException {

        return jsonPostInvoke(url, json, CONTENT_CHARSET);
    }

    /**
     * Json请求体Post请求
     * 
     * @param url
     *            请求路径
     * @param json
     *            json参数
     * @param charset
     *            编码方式
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String jsonPostInvoke(String url, String json, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {

        CloseableHttpClient client = buildHttpClient(false, null);

        HttpPost postMethod = buildHttpPost(url, json, CONTENT_TYPE_JSON_CHARSET);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            log.info(returnStr);
            return returnStr;
        }

        return null;
    }

    /**
     * xml请求体Post请求
     * 
     * @param url
     *            请求路径
     * @param xml
     *            xml参数
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String xmlPostInvoke(String url, String xml)
            throws ClientProtocolException, URISyntaxException, IOException {

        return jsonPostInvoke(url, xml, CONTENT_CHARSET);
    }

    /**
     * xml请求体Post请求
     * 
     * @param url
     *            请求路径
     * @param xml
     *            xml参数
     * @param charset
     *            编码方式
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String xmlPostInvoke(String url, String xml, String charset)
            throws URISyntaxException, ClientProtocolException, IOException {

        CloseableHttpClient client = buildHttpClient(false, null);

        HttpPost postMethod = buildHttpPost(url, xml, CONTENT_TYPE_XML_CHARSET);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            log.info(returnStr);
            return returnStr;
        }

        return null;
    }

    /**
     * xml请求体SSL Post请求
     * 
     * @param url
     *            请求路径
     * @param xml
     *            xml参数
     * @param sslcontext
     *            ssl内容
     * @return
     * @throws ClientProtocolException
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String xmlSSLPostInvoke(String url, String xml, SSLContext sslcontext)
            throws ClientProtocolException, URISyntaxException, IOException {

        return xmlSSLPostInvoke(url, xml, CONTENT_CHARSET, sslcontext);
    }

    /**
     * xml请求体SSL Post请求
     * 
     * @param url
     *            请求路径
     * @param xml
     *            xml参数
     * @param charset
     *            编码方式
     * @param sslcontext
     *            ssl内容
     * 
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String xmlSSLPostInvoke(String url, String xml, String charset, SSLContext sslcontext)
            throws ClientProtocolException, URISyntaxException, IOException {

        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        CloseableHttpClient client = buildHttpClient(false, sslsf);

        HttpPost postMethod = buildHttpPost(url, xml, CONTENT_TYPE_XML_CHARSET);

        HttpResponse response = client.execute(postMethod);

        assertStatus(response);

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String returnStr = EntityUtils.toString(entity, charset);
            log.info(returnStr);
            return returnStr;
        }

        return null;
    }

    /**
     * 创建HttpClient
     * 
     * @param isMultiThread
     * @return
     */
    public static CloseableHttpClient buildHttpClient(boolean isMultiThread, SSLConnectionSocketFactory sslsf) {

        CloseableHttpClient client;
        HttpClientBuilder builder;

        if (isMultiThread) {
            builder = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager());
        } else {
            builder = HttpClientBuilder.create();
        }
        if (sslsf != null) {
            client = builder.setSSLSocketFactory(sslsf).build();
        } else {
            client = builder.build();
        }
        // 设置代理服务器地址和端口
        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        return client;
    }

    /**
     * 构建httpPost对象
     * 
     * @param url
     * @param headers
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public static HttpPost buildHttpPost(String url, Map<String, String> params, String contentType)
            throws UnsupportedEncodingException, URISyntaxException {

        Assert.notNull(url, "构建HttpPost时,url不能为null");
        HttpPost post = new HttpPost(url);
        setCommonHttpMethod(post, contentType);
        HttpEntity he = null;
        if (params != null) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                formparams.add(new BasicNameValuePair(key, params.get(key)));
            }
            he = new UrlEncodedFormEntity(formparams, UTF_8);
            post.setEntity(he);
        }
        // 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
        // setContentLength(post, he);
        return post;
    }

    /**
     * 构建httpPost对象
     * 
     * @param url
     * @param headers
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    public static HttpPost buildHttpPost(String url, String params, String contentType)
            throws UnsupportedEncodingException, URISyntaxException {

        Assert.notNull(url, "构建HttpPost时,url不能为null");
        HttpPost post = new HttpPost(url);
        setCommonHttpMethod(post, contentType);
        HttpEntity he = null;
        if (params != null) {
            he = new StringEntity(params, UTF_8);
            post.setEntity(he);
        }
        // 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
        // setContentLength(post, he);
        return post;
    }

    /**
     * 构建httpGet对象
     * 
     * @param url
     * @param headers
     * @return
     * @throws URISyntaxException
     */
    public static HttpGet buildHttpGet(String url, Map<String, String> params) throws URISyntaxException {

        Assert.notNull(url, "构建HttpGet时,url不能为null");
        HttpGet get = new HttpGet(buildGetUrl(url, params));
        return get;
    }

    /**
     * build getUrl str
     * 
     * @param url
     * @param params
     * @return
     */
    private static String buildGetUrl(String url, Map<String, String> params) {

        StringBuffer uriStr = new StringBuffer(url);
        if (params != null) {
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                ps.add(new BasicNameValuePair(key, params.get(key)));
            }
            uriStr.append("?");
            uriStr.append(URLEncodedUtils.format(ps, UTF_8));
        }
        return uriStr.toString();
    }

    /**
     * 设置HttpMethod通用配置
     * 
     * @param httpMethod
     */
    @SuppressWarnings("deprecation")
    public static void setCommonHttpMethod(HttpRequestBase httpMethod, String contentType) {

        httpMethod.setHeader(HTTP.CONTENT_ENCODING, CONTENT_CHARSET);
        // setting contextCoding
        if (!Tools.isNullOrEmpty(contentType)) {
            httpMethod.setHeader(HTTP.CHARSET_PARAM, CONTENT_CHARSET);
            httpMethod.setHeader(HTTP.CONTENT_TYPE, contentType);
        }
    }

    /**
     * 设置成消息体的长度 setting MessageBody length
     * 
     * @param httpMethod
     * @param he
     */
    public static void setContentLength(HttpRequestBase httpMethod, HttpEntity he) {

        if (he == null) {
            return;
        }
        httpMethod.setHeader(HTTP.CONTENT_LEN, String.valueOf(he.getContentLength()));
    }

    /**
     * 构建公用RequestConfig
     * 
     * @return
     */
    public static RequestConfig buildRequestConfig() {

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS).build();
        return requestConfig;
    }

    /**
     * 强验证必须是200状态否则报异常
     * 
     * @param res
     * @throws HttpException
     */
    static void assertStatus(HttpResponse res) throws IOException {

        Assert.notNull(res, "http响应对象为null");
        Assert.notNull(res.getStatusLine(), "http响应对象的状态为null");
        switch (res.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
                // case HttpStatus.SC_CREATED:
                // case HttpStatus.SC_ACCEPTED:
                // case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
                // case HttpStatus.SC_NO_CONTENT:
                // case HttpStatus.SC_RESET_CONTENT:
                // case HttpStatus.SC_PARTIAL_CONTENT:
                // case HttpStatus.SC_MULTI_STATUS:
                break;
            default:
                throw new IOException("服务器响应状态异常,失败.");
        }
    }
}
