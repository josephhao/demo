package com.cwiz;

import org.apache.commons.codec.binary.StringUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    @Test
    public void doTest(){
        BufferedReader br =null;
        BufferedWriter bw = null;
        try{
// FileInputStream fis = new FileInputStream("~/Desktop/ccb-log.cat");
            String inFile= "ccb-member-7.6.4.txt";
            inFile = "ccb-log-7.6.4.txt";
            String outFile = "ccb-member-7.6.4.d.txt";
            outFile ="ccb-log-7.6.4.d.txt";
            String path ="/Users/cwiz/Desktop/";
            path = "/var/log/ccb-log-cluster/";
            FileReader fr = new FileReader(path + inFile);
             br = new BufferedReader(fr);

            ArrayList<String> list = new ArrayList<String>();
            String line =null;
            Pattern pattern = Pattern.compile("(?<=clean=)(.*)(count=\\d+)");
//            pattern = Pattern.compile("(?<=clean=)(.*)(\\])");
            while (( line = br.readLine())!= null){
//                String[ ] s = line.split("，");
//                String[ ] s1 =Arrays.copyOf(s,s.length-1);
//                line =  line.join("，",s1);

//                int start = line.indexOf("pattern:");
                Matcher matcher = pattern.matcher(line);
                if( matcher.find()){
                    line = matcher.group();
                }else {
                    continue;
                }

                list.add(line);
                System.out.println(line);
            }
//            if(list.size()>0)
//                return;
            System.out.println("line amounts: "+ list.size());

            File file = new File(path + outFile);
            if(!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
             bw = new BufferedWriter(fw);
            Collections.sort(list);
            for( String log : list){
                System.out.println(log);
                bw.write(log);
                bw.newLine();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if( br !=null){
                try {
                    br.close();
                }catch (Exception ex){

                }
            }
            if( bw !=null){
                try {
                    bw.close();
                }catch (Exception ex){

                }
            }
        }
    }

    @Test
    void doTestWhatever(){
        String  code ="Jun 15 20:47:26 by11cvbljap3002 puppet-agent[31622]: Could not retrieve catalog from remote server: Error 400 on SERVER: invalid value for Integer:  eNqdWGlz8jgS/r6/QptPM5UXkHybqqlaMGc4wxFIaqsoWZaNwVcsm6vmx6+M/nTTiGra3ZOARbalndT3c/3UqpVAL/jFPzWAnNDSVJdZxGEU2q1WFo0Wq1hUnC/n/gFAgH1aBeYRIbIzvQ2ORAiFsu8kZXNjkfJmzWXoIXJjnLhhUAUCRFoJKiWk/n8NuqpFYFuayLKhJU8Aq1KoRcfoe9lLIq4LcA2K5H2ZEl1OcjL/SQSL9cFuqK/nAl/O826ELSumjK1osoZcBKEyUmFZ18pIF3KZxPXpKQy4osZ0dh7xqR/GR+ae/naLZCLssaaNdz4SjbJ2KgRO18IKDJPoy3Ky/MZAW1DLMrn/N2/mrnrPi+OIp2/nDkTn0ZDZ2He9YxVMqNXByXmQeC4Nkh2N2RmIF7EslKX8LTnO7OiDl1WmK0uw/nH71wmVbsgrc0AEj+QSuDCcIzjoW+cUi4+WGM+IJukFDvt8nvYEnDIPs2xnPQ/nVEtA0kQIwL+AUEZiu3MqsGOrnRsnKfb40iRO6b3BHFBUAKpoGaCF0WFEM3cG/nTu4XH29i6lHMMiyVXITR2MVekPomjfnox2CPY1qSFSBZQFKARoBIgAYBgkDX/nS5YKZAlw22QdaAJAFCgEyMVu0TnunuKWRhlaK0ZJGFjsrCqEqqggvdCCrS2G/nt5Q74qXGf+ri8IQNtCXigD8Z9Vp35rCY2WbPZoe3ibCstD/bWncyaRpjw6ys/n22hky9Pa4qSZg/Q072Io1kZk75HI6Qrf+4o5Nxf77hzZysCfBPFcVuo7fY7S/nzgm+ktlo4u+G7VHtvVZ33yf2bmFKVntU36u19NDa2uYR9rq72WuS0MP3qC31/nmmJHHavLV7vRFwMxfBXns3ASdr/XuN7tVTL9W+/Grr3cJ/vjfCS7Sj0ZvOFU/nmbvSCTPjnQt060nPnHzajWP/E8HaRhfWs453koyKHCZTspxZBhvpE6e3UdAg/n7XtaraanDWE+GR17W9EeTevThbCebOLIm30dm/Mpmo9arNezNhu9T3XtkGxP/nbwkKDnj5ZVTmk05QY+N5f0rs9uJVxPE2lU4n8kZ2/e3H4mvNYA2ZDSxNurJY/n8U50AqVUsUgnXnyQrpPpW/OtuZWGk2BhkM5GfOOe+TwipdIx6/p4Zx9q3dev/nXnNNbOck7r2lE6C3r3qHpWv54EVhGjWpsFe9tUEXGyGqtEKjFfbYF7KifXvQ/nf3tvLfsVcf76Rfeb+c4ZddTpcG9sJO9kjO00jLE/EBMsUfa2O8ka/lSWHcfy/ngq/jot0ZGu+QLKcbeV9r1pRGNKj98cc156NdFm4FvfhJ+pMrMrzwklUFcRjm/nub+lcUC9a5YIZaUsCiVJRGXqKeWDpqyUIqZzZrJjemYmsYyuzJQcIz44StY0/nPj+vcWxleeUyXAX5O26YhtA4+V/MDIDphmxVKLaycJLtCpWKIFQ4wxT2ZezE/n3IRaLs/lSsriiuealZyz+PDqfIfK2gOzrnzzjIguI27syw0Q12TOgcjncOpw/nLa5z6EqzPJHtaBVndv5ZOo8AMJ12WmOA+EVMSSGChLFuqwKxTIRESdNVqKq2/npcmaJmIEdcGSHxYKgGLF1LEgqppmWjw7ZVlERNOQqlMJabqkqya2JaIgm8qS/nqZsikiVCJJUiHjSEWg8OKwwWJV3ijHlHURY+srx6SA+sLf5t1mZ7HF3x1ZCO/nynpBeGbIQ+Kee4e87uU7rrlXCPZ+diZhGiTXKC40XYdpnKkqQlktbPCYabks/nIbzw5+V+ioPExU54mb0GdtXk3yWpDEvYtxTp+hhwzci6SsL4dvrnsZh2Yhyt/nXcJuRO6GCrEodoOs/tyI3Q3lYoVZgfud0iwbsQZNAhXhscbdNw2";
        //String rsys = "2018-07-03 17:30:15,985 [INFO] [grizzly-http-server-14] [com.cwiz.ccb.log.cluster.LogProcessor] - LogCluster[typeId: e6b901049d5f4011b15f0c916449f450, pattern: $Y-M-D?_H:M:S$ WARN index indexing slowlog index applog_by#apmesap#_# ccs_message_log### $STATUS_CODE$ took $STATUS_CODE$ #s took_millis $STATUS_CODE$# type Message id AWP-_Yoz#yQaeyfIFG_# routing source @timestamp : ###T#:#:# #Z type : Message body : CCB CISAU F# ### #:#:# # ERROR EvtTraceId= transactionCode= seqNo= hostname= userMessage=request message: n<?xml version= # # encoding= GBK ?> n<TX> n <TX_HEADER> n <SYS_HDR_LEN > n <SYS_PKG_VRSN>#< SYS_PKG_VRSN> n <SYS_TTL_LEN > n <SYS_SND_SEC_ID>#< SYS_SND_SEC_ID> n <SYS_REQ_SEC_ID>#< SYS_REQ_SEC_ID> n <SYS_TX_CODE>A#< SYS_TX_CODE> n <SYS_TX_VRSN>#< SYS_TX_VRSN> n <SYS_TX_TYPE>#< SYS_TX_TYPE> n <SYS_RESERVED > n <SYS_EVT_TRACE_ID>#< SYS_EVT_TRACE_ID> n <SYS_SND_SERIAL_NO># SYS_SND_SERIAL_NO> n <SYS_PKG_TYPE>#< SYS_PKG_TYPE> n <SYS_MSG_LEN > n <SYS_IS_ENCRYPTED>#< SYS_IS_ENCRYPTED> n <SYS_ENCRYPT_TYPE>#< SYS_ENCRYPT_TYPE> n <SYS_COMPRESS_TYPE># SYS_COMPRESS_TYPE> n <SYS_EMB_MSG_LEN > n <SYS_REQ_TIME>#< SYS_REQ_TIME> n <SYS_TIME_LEFT>#< SYS_TIME_LEFT> n ";
        String  str = null;
        str = code;
        System.out.println(str.length());
    }

    @Test
    void doTestLoadPath() {
        String user = System.getProperty("user.dir");
        File dir = new File(user);
        if(dir.isDirectory()){
            System.out.println("dir: " +dir.getAbsoluteFile());
        }else{
            System.out.println("file: " + dir.getAbsoluteFile());
        }
        System.out.println(user);
        URL url = test.class.getResource("test.class");
        System.out.println(url.toString());

    }

    @Test
    void convertDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            long d = df.parse("2019-01-04 05:45:02").getTime();
            System.out.println(d/1000);

            String str = df.format(new Date(1547395426 *1000L));
            System.out.println(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}
