package com.hgh.homeworksystem.controller;

import com.hgh.homeworksystem.dto.CodeMsg;
import com.hgh.homeworksystem.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(new CodeMsg(-1,"上传失败，请选择文件"));
        }

        String fileName = file.getOriginalFilename();
        String filePath = "G:/file/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return Result.success("上传成功");
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return Result.error(new CodeMsg(-1,"上传失败，请重试"));
    }

    
}

