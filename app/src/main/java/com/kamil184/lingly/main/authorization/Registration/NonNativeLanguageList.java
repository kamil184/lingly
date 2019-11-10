package com.kamil184.lingly.main.authorization.Registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kamil184.lingly.models.LanguageLevelModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NonNativeLanguageList {

    private List<LanguageLevelModel> languageLevelModels = new ArrayList<>();

    public List<LanguageLevelModel> getLanguageLevelModels() {
        return languageLevelModels;
    }

    public void setLanguageLevelModels(List<LanguageLevelModel> languageLevelModels) {
        this.languageLevelModels = languageLevelModels;
    }

    public void setLanguages(List<Long> languages) {
        for (int i = 0; i < languages.size(); i++) {
            languageLevelModels.add(new LanguageLevelModel(-1, languages.get(i)));
        }
    }

    public void setLanguageLevelModels(List<Long> languageLevels, List<Long> languages) {
        for (int i = 0; i < languageLevels.size(); i++) {
            languageLevelModels.add(new LanguageLevelModel(languageLevels.get(i), languages.get(i)));
        }
    }

    public static List<Long> integerListToLong(List<Integer> list) {
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add((long) list.get(i));
        }
        return newList;
    }

    public void setLanguageLevel(int languageLevel, int languageId) {
        for (int i = 0; i < languageLevelModels.size(); i++) {
            if(languageLevelModels.get(i).getLanguage() == languageId){
                languageLevelModels.get(i).setLanguageLevel(languageLevel);
            }
        }
    }
}
