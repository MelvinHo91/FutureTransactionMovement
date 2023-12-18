package com.example.abmaro.futuretransaction.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtil {
    public static List<String> getRecordList(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().toList();
    }
}
