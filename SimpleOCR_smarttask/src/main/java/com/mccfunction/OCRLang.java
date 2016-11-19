package com.mccfunction;

public enum OCRLang {
    ENG,
    POL;
    public static String getLanguage(OCRLang lang) {
        switch (lang) {
            case ENG:
                return "eng";
            case POL:
                return "pol";
            default:
                return "eng";
        }
    }
}