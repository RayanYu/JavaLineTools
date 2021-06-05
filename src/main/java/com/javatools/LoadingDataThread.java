package com.javatools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class LoadingDataThread implements Runnable{
    private Path path;
    private String containsString;
    private long count;
    private Map<String, Integer> menbers = new LinkedHashMap<>();
    
    public LoadingDataThread(Path path, String containsString) {
        this.path = path;
        this.containsString = containsString;
    }

    public LoadingDataThread(Path path) {
        this.path = path;
    }
    
    @Override
    public void run() {
        if(containsString != null){
            try {
                count = Files.lines(path).filter(p -> p.contains(containsString)).count();
            } catch (IOException ex) {
                Logger.getLogger(LoadingDataThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.printf("%s -> %d\n", containsString, count);
        } else{
            try {
                Files.lines(path).filter(p -> p.contains("⁩⁩")).forEach(p -> {
//                Files.lines(path).forEach(p -> {
                    String[] lines = p.split("\\t"); // \\s+ 正則表示式，用來表示一個空白加上後面任意長度的空白。
                    int count = 0;
//                    if(lines.length == 1) System.out.println(lines[1]);
//                    System.out.println(lines.length);
                    String name = lines[1].replaceAll("⁩⁩", "");
                    if(name.contains("邀請"))return;
                    if(menbers.get(name) != null){
                        count = menbers.get(name);
//                        System.out.println(name.contains("⁨⁨段松佑🛵Rayan🚗Ryu⁩⁩已加入群組。"));
                        if(name.contains("已加入群組。")){
                            count++;
                            System.out.println("已加入 -> " + name + count);
                        }else{ //if (name.contains("已退出群組。"))
                            count--;
                            System.out.println("已退出 -> " + name + count);
                        }   
                        menbers.put(name, count);
                    } else{
//                        name = name.replace("已加入群組。", name);
                        menbers.put(name, 1);
                    }
                });
            } catch (Exception e) {
            }
            System.out.println(menbers.get("段松佑🛵Rayan🚗Ryu"));
            System.out.println(menbers.values());
            System.out.println(menbers);
              
        }
    }
    
}
