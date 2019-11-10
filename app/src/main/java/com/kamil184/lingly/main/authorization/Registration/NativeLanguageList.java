package com.kamil184.lingly.main.authorization.Registration;

import java.util.ArrayList;
import java.util.List;

public class NativeLanguageList {
    private List<Long> languages = new ArrayList<>();

    public List<Long> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Long> languages) {
        this.languages = languages;
    }

    public static List<Long> integerListToLong(List<Integer> list) {
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add((long) list.get(i));
        }
        return newList;
    }
}
