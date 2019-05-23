package cn.cwiz.study.test;

//import com.cloudmon.alert.common.http.HttpResult;
//import com.cloudmon.common.ConfigFile;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.time.Clock;

public class ElasticClient {

    private final static Logger logger = LoggerFactory.getLogger(ElasticClient.class);

    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient client;
    private static String esHost;
    private static int esPort;
    private static String postURL; // esHost:esPort/_msearch
    private static Pattern EXCEPTION_NAME_PATTERN = Pattern.compile("([A-Za-z0-9.]+[\\s]?(?:[Ee]xception|[Ee]rror))");
    private static Pattern EXCEPTION_PATTERN = Pattern.compile("(?:Exception|Error)");

    static {

 //       ConfigFile config = ConfigFile.getInstance();
        esHost ="192.168.138.128";            //config.getString(ConfigFile.Prefix.LOGCLUSTERING, "ElasticHost");
        esPort = 9200  ;       //config.getInt(ConfigFile.Prefix.LOGCLUSTERING, "ElasticPort", 9200);
        postURL = esHost + ":" + esPort + "/_msearch";
        int poolSize =3; //config.getInt(ConfigFile.Prefix.LOGCLUSTERING, "ElasticConnectionPoolSize", 3);

        cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(poolSize);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String query() {
        return query(null);
    }


    public static String query(String keyword) {
        String index = "index";
        String results = "";

        URI uri = null;
        HttpGet get = null;
        CloseableHttpResponse response = null;
        String content = "*";
        if (keyword!=null ){
            content = "content:" + keyword;
//            content =  keyword;
        }
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(esHost)
                    .setPort(esPort)
                    .setPath(index + "/_search")
                    .setParameter("size", "100")
                    .setParameter("q", content)
//                    .setParameter("pretty",Boolean.TRUE.toString())
                    .build();
            get = new HttpGet(uri);
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() >= 300) {
                logger.error("Failed to query ES, status = " + response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                String encoding = StandardCharsets.UTF_8.name();
                if (entity.getContentEncoding() != null)
                    encoding = entity.getContentEncoding().getValue();
                results = IOUtils.toString(is, encoding);
            }
        }
        catch (URISyntaxException usex) {
            logger.error("Bad URI: " + usex.toString());
        }
        catch (IOException ioex) {
            logger.error("Failed to query ES, exception = " + ioex.toString());
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                }
                catch (IOException ioex) {
                }
            }

            if (get != null) {
                get.releaseConnection();
            }
        }


        return results;
    }

    /**
     * POST /_msearch request with payload to ES.
     * All search criteria are specified in payload already.
     * Return a json string containing query results.
     * @param payload
     * @return
     */
    public static String post(String payload) {
        return send("POST", "/_msearch", payload).result;
    }

    /**
     * send request with payload to ES.
     * All search criteria are specified in payload already.
     * Return a json string containing query results.
     * @param payload
     * @return
     */
    public static HttpResult send(String method, String url, String payload) {
        HttpResult results = new HttpResult();

        URI uri = null;
        HttpRequestBase connection = null;
        CloseableHttpResponse response = null;

        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(esHost)
                    .setPort(esPort)
                    .setPath(url)
                    .build();
            if (HttpPost.METHOD_NAME.equals(method)) {
                HttpPost post = new HttpPost(uri);

                // Prepare payload in request body entity
                StringEntity requestEntity = new StringEntity(payload, "UTF-8");
                post.setEntity(requestEntity);
                connection = post;
                logger.info("To execute {} {} {}", new Object[] {post.getMethod(), post.getURI(), post.getEntity()});
            } else if (HttpGet.METHOD_NAME.equals(method)) {
                connection = new HttpGet(uri);
                logger.info("To execute {} {}", new Object[] {connection.getMethod(), connection.getURI()});
            } else if (HttpDelete.METHOD_NAME.equals(method)) {
                connection = new HttpDelete(uri);
                logger.info("To execute {} {}", new Object[] {connection.getMethod(), connection.getURI()});
            } else {
                throw new IllegalArgumentException("Not supported");
            }

            response = client.execute(connection);
            if (response.getStatusLine().getStatusCode() >= 300) {
                logger.error("Failed to query ES, status = " + response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                String encoding = StandardCharsets.UTF_8.name();
                if (entity.getContentEncoding() != null)
                    encoding = entity.getContentEncoding().getValue();
                results.result = IOUtils.toString(is, encoding);
            }
        }
        catch (URISyntaxException usex) {
            logger.error("Bad URI: " + usex.toString());
        }
        catch (IOException ioex) {
            logger.error("Failed to query ES, exception = " + ioex.toString());
        }
        finally {
            if (response != null) {
                try {
                    results.status = response.getStatusLine().getStatusCode();
                    response.close();
                }
                catch (IOException ioex) {
                }
            }

            if (connection != null) {
                connection.releaseConnection();
            }
        }

        return results;
    }

    public static List<String> filter(String log){
        Matcher exMatcher = EXCEPTION_PATTERN.matcher(log);
        List<String> result=new ArrayList<>();
        if(exMatcher.find()){
            System.out.println("Exception:  "+ exMatcher.group());
            Matcher nameMatcher = EXCEPTION_NAME_PATTERN.matcher(log);
            while(nameMatcher.find()){
                String exception = nameMatcher.group();
                System.out.println("Exception name Found:  "+ nameMatcher.group());
                int index = exception.indexOf(" ");
                if(index > 0){
                    exception = exception.substring(0,index);
                }
                result.add(exception);
            }

        }

        return result;
    }
    public static String logGenerator(){
        String[] logs = {
        "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"5\",\"_score\":1.0,\"_source\":{\"content\":\"RuntimeException in ClassCollectionLoader.php line 239: Failed to write cache file /var/www/fareast/app/cache/dev/classes.php. I have this error when I run my project in symfony2. I have nginx as my web server and centOS 7 as my OS. I tried deleting the cache folder manually and by php app/console cache:clear then I did a bad practice to app/cache and app/logs chmod -R 777 but I have the same error. /var/log/nginx/error.log is also empty. Do you recommend other solutions? Or do I have to install some php5 libraries for cache?\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"8\",\"_score\":1.0,\"_source\":{\"content\":\"The exact same link java is attempting to download works in the browser and downloads, but java responds with: java.io.IOException:  returned response code -1 at com.atlauncher.data.Downloadable.getConnection(Downloadable.java:149) at com.atlauncher.data.Downloadable.getFilesize\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"9\",\"_score\":1.0,\"_source\":{\"content\":\"but it said [InvalidArgumentException] Could not find package laravel/laravel with version 5.3 in a version installable using your PHP vers\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"10\",\"_score\":1.0,\"_source\":{\"content\":\"Your error message is ImportError: No module named myproject.wsgi You ran the app with gunicorn --bind\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"4\",\"_score\":1.0,\"_source\":{\"content\":\"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"2\",\"_score\":1.0,\"_source\":{\"content\":\"公安部：各地校车将享最高路权\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"6\",\"_score\":1.0,\"_source\":{\"content\":\" throw new System.IO.FileNotFoundException(strOrgFileName); try { using (System.Drawing.Image imgSourceImage = System.Drawing.Image.FromFile(strOrgFileName)) { ms = new System.IO.MemoryStream(); imgSourceImage.Save(ms, ifOutputFormat); ms.Position\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"1\",\"_score\":1.0,\"_source\":{\"content\":\"美国留给伊拉克的是个烂摊子吗\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"7\",\"_score\":1.0,\"_source\":{\"content\":\"All my registered routes are working. They display the view, yet on every request a NotFoundHttpException shows up in my log file. Im using NGINX. Could it be my NGINX config? The error that is logged on every request even though the view show\"}}",
                "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"3\",\"_score\":1.0,\"_source\":{\"content\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\"}}"};

        int index =  new Random().nextInt(20);
        String result = null;

        System.out.println("------log index:" + index);
        if(index >= logs.length){
            result = "{\"_index\":\"index\",\"_type\":\"fulltext\",\"_id\":\"3\",\"_score\":1.0,\"_source\":{\"content\":\"是是是是是 是 是是是是是：大大大大在在在在在在在在在在在在 在\"}}";
        }else {
            result = logs[index];
        }

        return result;


    }

    public static void esQuery(List<String> keyword){
        keyword.removeIf(o -> ("error".equalsIgnoreCase(o)|| "exception".equalsIgnoreCase(o)));
        String q = "".join("+",keyword);
        String result = query(q);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(result,((HashMap) map).getClass() );

        System.out.println("===================================================================================");
        for(Map.Entry<String,Object> e : map.entrySet()){

            if (e.getKey().equals("hits") && (e.getValue() instanceof Map)){
                Map m = (Map)e.getValue();
                System.out.println("hits:{" );
                for(Object key: m.keySet()) {
                    if ("hits".equals(key)) {
                        Object obj = m.get(key);
                        if (obj instanceof List) {
                            List list = (List) obj;
                            System.out.println("\t hits.hits:[");
                            for (Object o : list) {
                                System.out.println("\t\t" + o.toString() + ",");
                            }
                            System.out.println("\t]");
                        }
                    }else {
                        System.out.println("\t" +  key +"\t "+ m.get(key).toString());
                    }
                }
                System.out.println("}" );
            }else {
                System.out.println(e.getKey()+"\t "+ e.getValue().toString());
            }

        }
        System.out.println("==================================================================================");
        System.out.println();
    }

    public static HttpResult sendPostRequestToES(String requestUrl, String payload) throws IOException {
        return send("POST", requestUrl, payload);
    }

    public static HttpResult sendGetRequestToES(String requestUrl) throws IOException {
        return send("GET", requestUrl, null);
    }

    public static HttpResult sendDeleteRequestToES(String requestUrl) throws IOException {
        return send("DELETE", requestUrl, null);
    }
    public static void main(String[] args){

//        HttpResult  httpResult =sendGetRequestToES("index/fulltext/_search");
//        System.out.println(httpResult.getStatus() + ":\t" + httpResult.getResult());
        String log = logGenerator();
       // System.out.println("---"+log);
        log ="Your error message is Import9Error: No module named myproject.wsgi You ran the app with gunicor";
        List<String> keywords = filter(log);

        //System.out.println("---"+ keyword);

        esQuery(keywords);





    }
}
