package uj.jwzp.w2.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class BufferedReaderService {
    public BufferedReader getBufferedReader(String fileName){
        try {
            return  new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
