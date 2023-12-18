package com.example.abmaro.futuretransaction.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void getRecordList() throws IOException {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O");
        expectedValues.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012400     688058000092500000000             O");
        expectedValues.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012450     688098000092500000000             O");
        expectedValues.add("315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012530     688158000092450000000             O");
        String testOutputCsv = "Input.txt";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(testOutputCsv).getFile());
        MultipartFile multipartFile = new MockMultipartFile("Input.txt", new FileInputStream(file));
        List<String> actualValues = FileUtil.getRecordList(multipartFile);
        assertEquals(expectedValues.size(), actualValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(i));
        }
    }
}