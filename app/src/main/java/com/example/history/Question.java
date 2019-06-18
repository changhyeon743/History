package com.example.history;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public int era;
    public String title;
    public int type;
    public int answer;
    public List<String> example;
    public String commentary;

    public Question(int era, String title, int type, int answer, String commentary) {
        this.era = era;
        this.title = title;
        //1이면 순서바꾸기 0이면 선택형
        this.type = type;
        this.answer = answer;
        this.commentary = commentary;
    }

    public void setExample(String[] i) {
        List<String> example = new ArrayList<String>();
        for (int j=4;j<8;j++) {
            example.add(i[j]);
        }
        this.example = example;
    }

    public String toString() {
        return "era : " + era + " title: " + title + " answer" + answer;
    }

    ;
}