package com.cwiz;


import org.testng.annotations.Test;

public class TestForOtherProject {

    @Test
    void test1(){
        for(int i=0; i<1440;i++){
            if(i%10==0) {
                int h = i / 60;
                int m = i % 60;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ",";
                System.out.print(t);
            }
        }

    }

}
