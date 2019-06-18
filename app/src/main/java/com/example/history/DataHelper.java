package com.example.history;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    private List<String[]> datas;

    public DataHelper(InputStream filePath) {
        List<String[]> content = new ArrayList<String[]>();

        com.opencsv.CSVReader reader = null;

        try {

            reader = new com.opencsv.CSVReader(new InputStreamReader(filePath, "euc-kr"));

            content = reader.readAll(); //전체 데이터를 가져옴.

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {  if(reader != null) reader.close(); } catch (IOException e) {}

        }

        setDatas(content);
    }

    public List<String[]> getDatas() {
        return datas;
    }

    public void setDatas(List<String[]> datas) {
        this.datas = datas;
    }
}
