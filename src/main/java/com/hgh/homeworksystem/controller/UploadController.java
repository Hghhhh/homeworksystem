package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.CodeMsg;
import com.hgh.homeworksystem.dto.Result;
import com.hgh.homeworksystem.util.FileNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static final String FILE_PATH = "G:/file/";

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(new CodeMsg(-1,"上传失败，请选择文件"));
        }

        String fileName = file.getOriginalFilename();
        String filePathPre = FileNameUtil.generateFileName() + "-";
        File dest = new File(FILE_PATH + filePathPre + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info(filePathPre + fileName);
            return Result.success(filePathPre + fileName);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return Result.error(new CodeMsg(-1,"上传失败，请重试"));
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        String fileNameAbs = FILE_PATH + fileName;
        //设置文件MIME类型
        response.setContentType("application/msword");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        //读取目标文件，通过response将目标文件写到客户端
        //读取文件
        InputStream in = new FileInputStream(fileNameAbs);
        OutputStream out = response.getOutputStream();
        //写文件
        int b;
        while((b=in.read())!= -1)
        {
            out.write(b);
        }
        in.close();
        out.close();
    }
    
}

