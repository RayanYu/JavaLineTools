package com.javatools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public LoadingDataThread(Path path, String containsString) {
        this.path = path;
        this.containsString = containsString;
    }
    
    @Override
    public void run() {
        try {
            count = Files.lines(path).filter(p -> p.contains(containsString)).count();
        } catch (IOException ex) {
            Logger.getLogger(LoadingDataThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("%s -> %d\n", containsString, count);
    }
    
}
