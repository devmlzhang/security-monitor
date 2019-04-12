package com.weirdo.security.utils;

import com.weirdo.security.common.Constans;

public class NumToStringUtil {

    public static String numToString(long  num){
        StringBuffer str=new StringBuffer();
        String strs=String.valueOf(num);
        if(strs.length()%2==1){
            strs=strs+"0";
        }
        for(int i=0;i<strs.length();){
            String st=strs.substring(i,i+2);
            int t=Integer.valueOf(st).intValue()%22;
            int t1=Integer.valueOf(st).intValue()/22;
            str.append((char)(t+t1+65));
            i=i+2;
        }
        return Constans.PROJ_TOPIC +str.toString();
    }

    public static void main(String[] args) {

    }
}
