package com.kamil184.lingly.models;

public class LanguageLevelModel {

    public long getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(long languageLevel) {
        this.languageLevel = languageLevel;
    }

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long language) {
        this.language = language;
    }

    //уровней 6, так как есть нулевой, то есть вообще начинающий
    private long languageLevel;

    //индекс языка в константах
    private long language;

    public LanguageLevelModel(long languageLevel, long language) {
        this.languageLevel = languageLevel;
        this.language = language;
    }
}
