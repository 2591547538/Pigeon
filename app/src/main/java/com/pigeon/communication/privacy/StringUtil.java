package com.pigeon.communication.privacy;


public class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("...");
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }


    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }



    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }









}
