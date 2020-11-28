package com.xck.config;

import com.xck.util.DateUtil;
import org.junit.Test;

import java.io.File;

/**
 * @Classname DateUtilTest
 * @Description TODO
 * @Date 2020/11/28 8:41
 * @Created by xck503c
 */
public class DateUtilTest {

    @Test
    public void yyyyMMddhhmmssSSS(){
        File file = new File("D:/work/shareSource");
        System.out.println(DateUtil.yyyyMMddhhmmssSSSFormat(file.lastModified()));
    }
}
