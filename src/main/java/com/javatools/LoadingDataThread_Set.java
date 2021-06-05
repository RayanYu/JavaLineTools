package com.javatools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class LoadingDataThread_Set implements Runnable{
    private Path path;
    private String containsString;
    private long count;
    private Set<String> menbers = new LinkedHashSet();
    private Set<String> nullMenbers = new LinkedHashSet();
    private Map<String, Integer> 邀請Menbers = new LinkedHashMap<>();

    
    public LoadingDataThread_Set(Path path, String containsString) {
        this.path = path;
        this.containsString = containsString;
    }

    public LoadingDataThread_Set(Path path) {
        this.path = path;
    }
    
    @Override
    public void run() {
        if(containsString != null){
            try {
                count = Files.lines(path).filter(p -> p.contains(containsString)).count();
            } catch (IOException ex) {
                Logger.getLogger(LoadingDataThread_Set.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.printf("%s -> %d\n", containsString, count);
        } else{
            try {
                Files.lines(path).filter(p -> p.contains("⁩⁩")).forEach(p -> {
                    String[] lines = p.split("\\t"); // \\s+ 正則表示式，用來表示一個空白加上後面任意長度的空白。
                    int count = 0;
                    String name = lines[1].replaceAll("⁩⁩", "").replaceAll("⁨⁨", "");
                    // ⁨⁨Jack湯圓 謝子淵⁩⁩已邀請「⁨⁨陳友仁⁩, ⁨UC_課程統籌 Sam⁩, ⁨Gene⁩⁩」加入群組囉，等對方加入後再一起聊天吧！
                    // ⁨⁨柏璁⁩⁩已邀請「⁨⁨willis⁩⁩」加入群組囉，等對方加入後再一起聊天吧！
                    if(name.contains("已邀請")){
                        name = name.replace("」加入群組囉，等對方加入後再一起聊天吧！", "").replace("加入群組，等對方加入再一起聊天吧！", "");
                        System.out.println(name);
                    };
                    if(name.contains("已加入群組。")){
                        name = name.replace("已加入群組。", "");
                        menbers.add(name);
                        nullMenbers.remove(name);
                    }
                    if(name.contains("已退出群組。")){
                        name = name.replace("已退出群組。", "");
                        menbers.remove(name);
                        nullMenbers.add(name);
                    }
                });
            } catch (Exception e) {
            }
            System.out.printf("目前有 %d 已加入\n", menbers.size());
            System.out.println(menbers.contains("段松佑🛵Rayan🚗Ryu"));
            String[] s1 = menbers.stream().toArray(size -> new String[size]);
            System.out.println(Arrays.toString(s1));
            System.out.printf("目前有 %d 已退出\n", nullMenbers.size());
            String[] s2 = nullMenbers.stream().toArray(size -> new String[size]);
            System.out.println(Arrays.toString(s2));
            
            try {
                Files.lines(path).forEach(p ->{
                    String[] lines = p.split("\\t");
                    if(lines.length > 1){
//                        System.out.println("---> " + p);
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(LoadingDataThread_Set.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
