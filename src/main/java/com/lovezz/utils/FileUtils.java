package com.lovezz.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:15
 * @Description:
 */
public class FileUtils {

    public static List getTextForLove(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream file = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        List textList = new ArrayList();
        String line = null;
        while (StringUtils.isNotBlank(line = br.readLine())){
            textList.add(line);
        }

        return textList;
    }
}
