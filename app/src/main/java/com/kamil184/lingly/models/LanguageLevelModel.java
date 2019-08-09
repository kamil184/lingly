package com.kamil184.lingly.models;

import java.util.ArrayList;

public class LanguageLevelModel {

    public long getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(long languageLevel) {
        this.languageLevel = languageLevel;
    }

    public long getLanguage(int position) {
        return (long)language.get(position);
    }

    public void setLanguage(ArrayList language) {
        this.language = language;
    }

    //уровней 6, так как есть нулевой, то есть вообще начинающий
    private long languageLevel;

    /* можно использовать другой тип данных
     byte потому что планируется использовать индекс языка в константах
     */
    private ArrayList language;

    public LanguageLevelModel(long languageLevel, ArrayList language) {
        this.languageLevel = languageLevel;
        this.language = language;
    }
}
