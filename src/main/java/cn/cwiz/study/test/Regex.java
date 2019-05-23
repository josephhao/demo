package cn.cwiz.study.test;

import com.sun.deploy.util.StringUtils;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static void main(String[] args)
    {
//       String tt = "ee.ttt.001.ooo";
//        String key = null;
//        StringTokenizer st = new StringTokenizer(tt, ".");
//        int count =st.countTokens();
//        if(count>2){
//            while (st.hasMoreTokens()) {
//
//                if(count==1){
//                    key += "." + st.nextToken();
//                }else {
//                    key=st.nextToken();
//                }
//                count--;
//            }
//
//        }
//        System.out.println("key: "+ key);


        String str = "\\u003d";

        // String destZoneCode ="172       .18.1.129[设备名:暂无],MF ZZDS_YLJ_AC_SW1 %%10PORTSEC/6/PORTSEC_DOT1X_LOGIN_FAILURE(l): -IfName WLAN-DBSS2:19-MACAddr 90:CD:B6:46:A1:4F-VlanId 700-UserName zhangjiahan.ccbp; T       he user failed the 802.1X authentication";
        //String destZoneCode ="IP$ 设备名:暂无 MF ZZDS_YLJ_AC_SW# #WMAC # Station Deassociate t : Station Deassoc:# # # # # # # # # # # # #<hh#cDot#;StationDeA StaMac#:F#:D#:BF:#B: StaMac#:F#:D#:BF:#B: UserName:mayu ccbp StaMac#:F#:D#:BF:#B: VLANId:# ";
       // System.out.println(destZoneCode.replace("\\u003d","="));
        String destZoneCode = "Client #cd-b##e# successfully joins WLAN YLJ-office on APID # with BSSID ac##-d# ";

        destZoneCode = destZoneCode.replace("#","1");
        String regex = "(UserName[:|\\s])(\\w+)(.ccbp|\\s)";
        String mac   = "(?<=(Client|BSSID|WLAN)\\s)((-|\\w)+)(\\s|\n|\r)" ;

        regex = mac;
//        destZoneCode = "Jun 20 11:51:10 by11ccsfrap3001 kernel: BA_ALS_TRAN[13831]: segfault at 7fff0064d420 ip 0000003625a480ac sp 00007fff951b9790 error 4 in libc-2.12.so[3625a00000+18a000]"; // degfault;
//        regex = "(?<=segfault) (at \\w+ ip \\w+ sp \\w+)";

//        destZoneCode="Jun 17 20:01:28 localhost haproxy[9100]: 10.3.33.122:28531 [17/Jun/2018:20:00:54.668] CCVEP_CCBIS_GG P1_CCBIS_GG/by11cvpykap1001 0/0/0/11/34085 200 117405 - - CDNI 69/13/13/1/0 0/0  GET /CCBIS/B2CMainPlatVM?CCB_IBSVersion\u003dV6\u0026PT_STYLE\u003d2\u0026BRANCHID\u003d310000000\u0026NSKEY\u003d8570141508188387\u0026CERT_ID\u003d220381197902287686\u0026Cst_ID\u003d6DC42CFA1827115C7E657C997BD2C88D4EBACCCB8B7B0C9A\u0026HOLD_ACC_NO\u003d6253624060902515\u0026MICRO_CUST_ID\u003do8QqFjg1L55g2tyUZ69yNxh7dZbc\u0026CUST_NAME4\u003d%E4%BE%AF%E5%B7%8D%E5%B7%8D\u0026CERT_TYP\u003d01\u0026IFMORE\u003d2\u0026TOTXCODE\u003dIW0031\u0026CHECK_DOWN\u003d0\u0026CLIENT_FROM\u003dWC\u0026AL_VERSION\u003d\u0026errURL\u003d%2FCCBIS%2FB2CMainPlatVM%3FCCB_IBSVersion%3DV6%26PT_STYLE%3D2%26TXCODE%3DIW0100%26SKEY%3D%26USERID%3D%26BRANCHID%3D310000000%26errflag%3D1%26wParam%3DQMerSTmogaHKx%252FJD4S7CwxAsZfy5lZRdZ32EvnWSZ9I2%252FB%252FdeM8oIwj025OkqiiSlyoB2l1uglLuYyvkXLDw4JmqbrKh5W9uWE19N6UqErbAx%252BgYR1M7xn1yf0%252ByDp33alwMTCubwty7kJXP%252BJcwbBvO4rsP243Mwj3WJJvNVJ4rVH5FNfs664W0FD7OEK%252BhVkiM4MILFkrMucgfXtWmDkFxG8NykSFY\u0026TXCODE\u003dIW0031 HTTP/1.1";
//        destZoneCode = destZoneCode.replace("\\\\u003d","=");
//        String[] s = destZoneCode.split("&");
//        System.out.println(destZoneCode);
//        for(String s1: s){
//            System.out.println(s1);
//        }
//        regex = "(?<=\\?)((\\w+=(=|%|\\w)+)(&\\w+=(=|%|\\w)*)*)";
//        regex = "(?<=(\\?|&)\\w{1,18}=)((=|%|\\w)+)";

//        destZoneCode= "20180613 16:30:01[ERROR] 20180613电子同城对账清算状态为:联机对账结果异常[27];主机联机清算结果异常，P6划转[376114199.54]，我行轧差[376117181.79]，人行轧差[376484970.77]，我行轧差联机[376117181.79] 批量[367788.98];，";
//        regex = "((?<=(=|\\[))(([A-Za-z]+[0-9]+\\w+)|([0-9\\.]+\\w+))(?=(\\]|\\s)))";
        //String regex = "((?<=(\\s))(\\\\u003d\\w+))";        //^[1-9][0-9]{4,}$
//        destZoneCode = "11.155.21.65[设备名:YQBWY-APP-SW1],MF %SEC_LOGIN-5-LOGIN_SUCCESS: Login Success [user: user] [Source: 192.168.15.78] [localport: 22] at 14:05:49 Beijing Wed Jun 27 2018,";
//        regex = "(Sun|Sat|Fri|Thu|Wed|Tue|Mon)\\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2}\\s\\d{4}";

        destZoneCode="Jun 12 01:06:47 localhost haproxy[20419]: 10.3.33.61:34300 [12/Jun/2018:01:04:59.526] CCVEP_CCBIS_GG P1_CCBIS_GG/by11cvpykap3001 0/0/0/59/107895 200 487630 - - cDNI 32/4/4/0/0 0/0  GET /CCBIS/B2CMainPlatVM?CCB_IBSVersion=V6&PT_STYLE=2&BRANCHID=310000000&NSKEY=1114306821919645&CERT_ID=341225199304188574&Cst_ID=D35FF169418416118F12CD70E96B63B8108B0C933699BB22&HOLD_ACC_NO=4367455086079949&MICRO_CUST_ID=o8QqFju1_F_msy3Au6OT5uJS1GpY&CUST_NAME4=%E5%88%98%E4%BF%8A%E8%99%8E&CERT_TYP=01&IFMORE=2&TOTXCODE=IW3400&CHECK_DOWN=0&CLIENT_FROM=WC&AL_VERSION=&errURL=%252FCCBIS%252FB2CMainPlatVM%253FCCB_IBSVersion%253DV6%2526PT_STYLE%253D2%2526TXCODE%253DIW3400%2526SKEY%253D%2526USERID%253D%2526BRANCHID%253D310000000%2526errflag%253D1%2526wParam%253DQMerSTmogaHKx%252FJD4S7Cw6yu296DgdQ2V2VPzODvWnpw5CMALFUoBjq2LYAJc6ytT8ooPDbyo7sFQEuaLxxaG58xHrhriCXMf75YOQkFBINRjDFbiGN7eQA5RSnnZA3muI7YnG2Jt02pIbLoAjXdbVNIupt08FEEOjbD7MDG%252B46Sa%252Fi4Adtkbf7MFaVfdIGA65TLY1AXiSIxE6i0ypHC38FFIalFr6uC8gcNPmTNrjeyOwSfBaitXg%253D%253D&TXCODE=IW3400";
        regex = "(?<=(\\?|&)[-\\w]{1,18}=)((=|%|-|\\w|\\(|\\)|\\]|\\[|\\{|\\})+)";

        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (destZoneCode);
        String logN = destZoneCode;

        System.out.println('0'-'.');
        System.out.println ("======   test 3  ========");
        test3();

    }

    public  static void test1 (){
        String regexString ="%\\{[0-9A-Z_]+:([0-9a-zA-Z_]+)\\}";
        regexString ="\\(\\?<([0-9a-zA-Z_]+)>[^\\)]+\\)";

        Pattern pattern = Pattern.compile(regexString);
        String input = "%{TIMESTAMP_ISO8601:timestamp} %{WORD:module}";
        input = "(?<exception>[A-Za-z]+[Ee]xception[A-Za-z]*)";

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String tag = matcher.group(1);
            if (tag!=null && !"".equals(tag.trim()))
                System.out.println(tag);
        }
    }
    public static  void test2(){

//        String destZoneCode = "[ERROR][ti\\u003d1080920051529550857857274][tc\\u003dA0330A009][sn\\u003d937113][";
        String destZoneCode ="2018-06-21 11:14:17 636][ERROR][ti\\u003d1080920051529550857857274][tc\\u003dA0330A009]" +
                "[sn\\u003d937113][um\\u003d外呼服务[P1ELBFP01]，响应码[YBLP1KJZ0021]，响应描述[签约校验错误次数大于6]]:" +
                "[com.ccb.npms.pe.common.outbound.OutboundExecutorImpl.doOutbound(OutboundExecutorImpl.java:115)]";
        String regex = "((?<==|\\\\u003d|\\[)([A-Za-z]+[0-9]+\\w+|[0-9]+\\w+)(?=\\]))";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (destZoneCode);
        while (matcher.find ())
        {
            System.out.println (matcher.group());
        }


        System.out.println ("==============");


    }


    public static void test3(){
         Pattern metricPattern = Pattern.compile("^([1-9][0-9]{0,9}\\.[1-9][0-9]{0,9}\\.)(([0-9a-zA-Z_-]+\\.)*[0-9a-zA-Z_-]+)$");
         String metric = "2.2.joseph.test-1";
         String metric1 = "joseph.test-1";
         String metric2 = "2.4.3.5.joseph.test-1";
         String[] m = {metric, metric1, metric2};

         for( String s :  m){
             Matcher matcher = metricPattern.matcher(s);
             if(matcher.find()){

                 System.out.println( matcher.group() + " "+ matcher.start() + "=>" + matcher.end());
                 int count = matcher.groupCount();
                 int i =0;
                 while(++i <=count  ){
                     System.out.println( "\t : "+ matcher.group(i) );
                 }
                 System.out.println(":: " + matcher.group( matcher.groupCount() -1));
             }else{
                 System.out.println(s);
             }
         }
    }


}
