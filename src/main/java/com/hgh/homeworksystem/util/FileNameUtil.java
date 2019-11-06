package com.hgh.homeworksystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileNameUtil {

    public static String generateFileName(){
        Date date = TimeUtil.getDayBegin();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Random random = new Random();
        return simpleDateFormat.format(date) + random.nextInt(100);
    }
}
