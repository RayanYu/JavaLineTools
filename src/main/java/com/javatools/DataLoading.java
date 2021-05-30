/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.javatools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rayan
 */
public class DataLoading {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\user\\OneDrive\\GTW\\[LINE] 🎏Game Time! Who-的聊天_20210529.txt");
        long join_Count = 0; // 加入人數
        long leave_count = 0;  // 退出人數
        
        Map<String, Integer> menbers = new LinkedHashMap<>();
        
        
        // 1. Files.lines (Stream 串流)
//            Files.lines(path).forEach(System.out::println);
        Files.lines(path).filter(p -> p.contains("已加入群組。") ).forEach(p -> {
            String[] lines = p.split("\\t"); // \\s+ 正則表示式，用來表示一個空白加上後面任意長度的空白。
            int count = 0;
            String name = lines[1];
            if(menbers.get(name) != null){
                count = menbers.get(name) + 1;
                menbers.put(name, count);
            } else{
                menbers.put(name, 1);
            }
//            System.out.println(Arrays.toString(lines));
//                System.out.printf("%s\n", lines);
        });
        System.out.println(menbers.toString());
        join_Count = Files.lines(path).filter(p -> p.contains("已加入群組。")).count();
        leave_count = Files.lines(path).filter(p -> p.contains("已退出群組。")).count();
        System.out.println("已加入群組人數：" + join_Count);
        System.out.println("已退出群組人數：" + leave_count);
        /*
        // 2. Files.readAllLines (List 集合)
        List<String> contentList = Files.readAllLines(path, StandardCharsets.UTF_8);
        System.out.println(contentList);
        */
    }
}
