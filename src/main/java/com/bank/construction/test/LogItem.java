package com.bank.construction.test;




import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogItem {

    private String hostName;
    private String systemName;
    private String logContent;
    private String startTime;
    private String endTime;



    private String testLogString;


    public String getTestLogString() {
        return testLogString;
    }

    public void setTestLogString(String testLogString) {
        this.testLogString = testLogString;
    }

    public static void main(String[] args){
        LogItem iterm = new LogItem();
        iterm.setTestLogString("【主要告警（洋桥）】支付系统[CC] ," +
                "host1日志文件/var/log/asm2.log匹配到2个关键字，" +
                "原始信息：you can fill the information as you wish, produced manually by someone," +
                "发生时间：06/04/2018 00:00:00 【辅助信息：生产地址：ip；资源：tt，应用处：team；】【数据中心】");

            //提取 /var/log/xx.log
            //原始信息: xxxxx,
            //发生时间:
            //辅助信息：
            //数据中心
        String source = "\"【主要告警（洋桥）】支付系统[CC] ,\" +\n" +
                "                \"host1日志文件/var/log/asm2.log匹配到2个关键字，\" +\n" +
                "                \"原始信息：you can fill the information as you wish, produced manually by someone,\" +\n" +
                "                \"发生时间：06/04/2018 00:00:00 【辅助信息：生产地址：ip；资源：tt，应用处：team；】【数据中心】\"";
        String[] rules = {"logPath:日志文件(.*)匹配到","message:原始信息(.*)发生时间"};
        Map<String,String> pStrMap = new HashMap<>();
        for(String rule : rules){
            String[] r = rule.split(":");
            Pattern pt =  Pattern.compile(r[1]);
            pStrMap.put(r[0],r[1]);
        }

        Map<String,Pattern> patternMap = new HashMap<>();
        for(String key : pStrMap.keySet()){
            String pStr = pStrMap.get(key);
            Pattern pt =  Pattern.compile(pStr);
            patternMap.put(key, pt);

        }
        for(String key : patternMap.keySet()){
            Pattern pattern = patternMap.get(key);
            Matcher matcher = pattern.matcher(source);
            while(matcher.find()) {
                System.out.println(matcher.group());
                System.out.println(matcher.group(1));
//            System.out.println(matcher.group(3));
            }

        }


        System.out.println("hello");
    }

}
