package com.kamil184.lingly;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constants {

    public static class Profile {
        public static final int MAX_AVATAR_SIZE = 1280; //px, side of square
        public static final int MIN_AVATAR_SIZE = 100; //px, side of square
        public static final int MAX_NAME_LENGTH = 120;
    }

    public static class Post {
        public static final int MAX_TEXT_LENGTH_IN_LIST = 300; //characters
        public static final int MAX_POST_TITLE_LENGTH = 255; //characters
        public static final int POST_AMOUNT_ON_PAGE = 10;
    }

    public static class Database {
        public static final int MAX_UPLOAD_RETRY_MILLIS = 60000; //1 minute
    }

    public static class PushNotification {
        public static final int LARGE_ICONE_SIZE = 256; //px
    }

    public static class General {
        public static final long DOUBLE_CLICK_TO_EXIT_INTERVAL = 3000; // in milliseconds
    }

    public static class UserData{
        public static final String APP_PREFERENCES = "LinglySettings";
        public static final String APP_PREFERENCES_EMAIL = "email";
        public static final String APP_PREFERENCES_FIRSTNAME = "first_name";
        public static final String APP_PREFERENCES_SECONDNAME = "second_name";
        public static final String APP_PREFERENCES_STATUS = "status";
        public static final String APP_PREFERENCES_ABOUT = "about";
        public static final String APP_PREFERENCES_BIRTHDAY = "birth_day";
        public static final String APP_PREFERENCES_BIRTHMONTH = "birth_month";
        public static final String APP_PREFERENCES_BIRTHYEAR = "birth_year";

        //передается NativeLanguageList
        public static final String APP_PREFERENCES_NATIVE_LANGUAGES = "native_languages";
        //передается NonNativeLanguageList
        public static final String APP_PREFERENCES_NON_NATIVE_LANGUAGES = "learning_languages";
    }

    public static class Languages{
        public static String[] languageArray = {"Русский","Английский","Немецкий","Французский","Испанский","Португальский","Турецкий","Итальянский","Хинди","Японский","Китайский"};
        public static int[] flagArray = {
                R.drawable.ic_ru,   //0
                R.drawable.ic_gb,   //1
                R.drawable.ic_de,   //2
                R.drawable.ic_fr,   //3
                R.drawable.ic_es,   //4
                R.drawable.ic_pt,   //5
                R.drawable.ic_tr,   //6
                R.drawable.ic_it,   //7
                R.drawable.ic_in,   //8
                R.drawable.ic_jp,   //9
                R.drawable.ic_cn    //10
        };
    }
}
