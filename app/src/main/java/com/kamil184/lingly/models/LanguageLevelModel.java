package com.kamil184.lingly.models;

public class LanguageLevelModel {

    public byte getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(byte languageLevel) {
        this.languageLevel = languageLevel;
    }

    public byte getLanguage() {
        return language;
    }

    public void setLanguage(byte language) {
        this.language = language;
    }

    //уровней 6, так как есть нулевой, то есть вообще начинающий
    private byte languageLevel;

    /* можно использовать другой тип данных
     byte потому что планируется использовать индекс языка в константах
     */
    private byte language;

    public LanguageLevelModel(byte languageLevel, byte language) {
        this.languageLevel = languageLevel;
        this.language = language;
    }
}
