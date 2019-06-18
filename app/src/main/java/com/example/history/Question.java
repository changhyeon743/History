package com.example.history;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int era;
    private String title;
    private int type;
    private int answer;
    private List<String> example;
    private String commentary;

    public int getEra() {
        return era;
    }

    public void setEra(int era) {
        this.era = era;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        //0이면 단답형 //1이면 순서
        this.type = type;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExample(int index) {
        return example.get(index);
    }
    public List<String> getExamples() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

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


}