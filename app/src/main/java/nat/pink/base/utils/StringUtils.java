package nat.pink.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();

    /**
     * use to display list to string, for example, address, city, state
     *
     * @param strings
     * @return
     */
    public static String joinText(String... strings) {
        return joinText(", ", strings);
    }

    /**
     * use to display list to string, for example, address, city, state
     *
     * @param strings
     * @return
     */
    public static String joinText(String joinCharacter, String... strings) {
        try {
            if (strings == null || strings.length <= 0) return "";

            List<String> stringList = new ArrayList<>();
            for (String s : strings) if (!StringUtils.isEmpty(s)) stringList.add(s);
            if (stringList == null || stringList.size() <= 0) return "";

            return TextUtils.join(joinCharacter, stringList);
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * @param strings
     * @return
     */
    public static String joinText(List<String> strings) {
        return joinText(", ", strings);
    }

    /**
     * @param joinCharacter
     * @param strings
     * @return
     */
    public static String joinText(String joinCharacter, List<String> strings) {
        try {
            if (strings == null || strings.size() <= 0) return "";

            List<String> stringList = new ArrayList<>();
            for (String s : strings) if (!StringUtils.isEmpty(s)) stringList.add(s);
            if (stringList == null || stringList.size() <= 0) return "";

            return TextUtils.join(joinCharacter, stringList);
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    @SuppressLint("DefaultLocale")
    public static boolean isEmpty(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("") || value.equalsIgnoreCase(" ") || TextUtils.isEmpty(value)
                    || value.equalsIgnoreCase("null")) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * get random string
     *
     * @param sizeOfRandomString
     * @return
     */
    public static String getRandomString(final int sizeOfRandomString) {
        try {
            final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
            final Random random = new Random();
            final StringBuilder sb = new StringBuilder(sizeOfRandomString);
            for (int i = 0; i < sizeOfRandomString; ++i) {
                sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
            }

            return sb.toString();
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    @SuppressLint("DefaultLocale")
    public static boolean isNullOrEmpty(@Nullable String value) {
        if (TextUtils.isEmpty(value) || value.trim().equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    @SuppressLint("DefaultLocale")
    public static boolean isNullOrEmpty(@Nullable CharSequence value) {
        if (TextUtils.isEmpty(value) || value.toString().trim().equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    public static boolean isNotNullOrEmpty(@Nullable String value) {
        return !isNullOrEmpty(value);
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    public static boolean isNotNullOrEmpty(@Nullable CharSequence value) {
        return !isNullOrEmpty(value);
    }

    /**
     * @param value
     * @param objectConsumer
     */
    public static void isNullOrEmpty(@Nullable String value, Consumer<String> objectConsumer) {
        if (isNullOrEmpty(value) && objectConsumer != null) objectConsumer.accept(null);
    }

    /**
     * @param value
     * @param objectConsumer
     */
    public static void isNullOrEmpty(@Nullable CharSequence value, Consumer<String> objectConsumer) {
        if (isNullOrEmpty(value) && objectConsumer != null) objectConsumer.accept(null);
    }

    /**
     * @param value
     * @param objectConsumer
     */
    public static void isNotNullOrEmpty(@Nullable String value, Consumer<String> objectConsumer) {
        if (!isNullOrEmpty(value) && objectConsumer != null) objectConsumer.accept(value);
    }

    /**
     * @param value
     * @param objectConsumer
     */
    public static void isNotNullOrEmpty(@Nullable CharSequence value, Consumer<String> objectConsumer) {
        if (!isNullOrEmpty(value) && objectConsumer != null) objectConsumer.accept(value + "");
    }

    /**
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        try {
            if (isNullOrEmpty(str)) {
                return str;
            }

            String phrase = "";

            char[] arr = str.toCharArray();
            boolean capitalizeNext = true;
            for (char character : arr) {
                if (capitalizeNext && Character.isLetter(character)) {
                    phrase += Character.toUpperCase(character);
                    capitalizeNext = false;
                    continue;
                } else if (Character.isWhitespace(character)) {
                    capitalizeNext = true;
                }

                phrase += character;
            }

            return phrase;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getString(String string, boolean forward) {
        return getString(string, forward, true);
    }

    public static String getString(String string, boolean forward, boolean encodeUrl) {
        if (!forward) string = encodeUrl ? URLDecoder.decode(string) : string;
        StringBuilder b = new StringBuilder();
        int i = 0;
        boolean f = forward;
        for (char c : string.toCharArray()) {
            int val = (i++) % 4 + 1;
            int c1 = f ? c + val : c - val;
            if (i % 4 == 0) {
                f = !f;
            }
            b.append((char) c1);
        }
        if (!forward) return b.toString();
        return encodeUrl ? URLEncoder.encode(b.toString()) : b.toString();
    }
}

