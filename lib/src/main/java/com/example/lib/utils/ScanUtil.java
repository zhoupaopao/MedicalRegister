package com.example.lib.utils;

import java.util.ArrayList;

public class ScanUtil {
    //先判断是零料还是成品，S用零料库模式D用成品库模式
    private static String checkSD(String label) {
        return label.substring(0, 1);

    }

    public static ArrayList<String> getSerMsg(String label) throws Exception {
        String[] splitText = label.split("/");
        ArrayList<String> scanList = new ArrayList<>();
//        if (checkSD(label).equals("S")) {
//            //零料
//            String[] lotSer = splitText[1].split(":");//：前是批次号，：后是序列号
//            String scanItemNumber = splitText[0].substring(1);
//            String scanBatchNumber = lotSer[0];
//            String scanSerNumber = lotSer[0] + lotSer[1];
//            String scanWeight = splitText[2];
//            scanList.add(scanItemNumber);
//            scanList.add(scanBatchNumber);
//            scanList.add(scanSerNumber);
//            scanList.add(scanWeight);
//            return scanList;
//        } else
            if (checkSD(label).equals("D")) {
            //成品
            String[] batchWight=splitText[3].split(":");
            String scanBatchNumber=batchWight[0];//炉号+客户号+重量
            String scanItemNumber=splitText[ 0].substring(1);
            //String scanWeight=batchWight[1];扫描枪暂时不能识别：和-
            String scanWeight=splitText[1];
            String productionDateStr=splitText[4];
            String scanSerNumber=scanBatchNumber+productionDateStr;//炉号+生产日期
            //将序列号中的_转换成-
            String productionDate="20"+productionDateStr.substring(0,2)+"-"+productionDateStr.substring(2,4)+"-"+productionDateStr.substring(4,6)+" "+productionDateStr.substring(6,8)+":"+productionDateStr.substring(8,10)+":"+productionDateStr.substring(10,12);
            String customerNumber=splitText[2];
            scanList.add(scanItemNumber);
            scanList.add(scanBatchNumber);
            scanList.add(scanSerNumber);
            scanList.add(scanWeight);
            scanList.add(productionDate);
            scanList.add(customerNumber);
            return scanList;
        } else {
            throw new Exception("条码异常");
        }

    }

}
