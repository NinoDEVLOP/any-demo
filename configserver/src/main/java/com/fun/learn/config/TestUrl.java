package com.fun.learn.config;

import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/12 16:03
 */
public class TestUrl {

    public static void main(String[] args) throws IOException {
        File file = new UrlResource("file:/workspace/company/work_project/czt/any-demo/configserver/src/main/resources/config").getFile();
        System.out.println(file.exists());
        System.out.println(file.isDirectory());
        System.out.println(file.isFile());
        System.out.println(file.getAbsolutePath());
    }
}
