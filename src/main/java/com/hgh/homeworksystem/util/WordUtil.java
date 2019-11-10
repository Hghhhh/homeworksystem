package com.hgh.homeworksystem.util;

import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WordUtil {

    private static final String FILE_PATH = "G:/file/";

    /**
     * 读取doc文件内容
     *
     * @param fs
     *            想要读取的文件对象
     * @return 返回文件内容
     * @throws IOException
     */
    public static String doc2String(FileInputStream fs) throws IOException {
        StringBuilder result = new StringBuilder();
        WordExtractor re = new WordExtractor(fs);
        result.append(re.getText());
        re.close();
        return result.toString();
    }

    public static String doc2String(String fileName) throws IOException {
        File file = new File(FILE_PATH+fileName);
        return doc2String(new FileInputStream(file));
    }

}
