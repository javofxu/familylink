package com.example.common.utils;

import android.text.InputFilter;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：限制EditText输入的字符类型，如空格，特殊字符等
 */
public class InputFilterUtil {
    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText) {
        InputFilter filter_space = (source, start, end, dest, dstart, dend) -> {
            if (source.equals(" "))
                return "";
            else
                return null;
        };
        InputFilter filter_speChat = (charSequence, i, i1, spanned, i2, i3) -> {
            String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(charSequence.toString());
            if (matcher.find()) return "";
            else return null;
        };
        editText.setFilters(new InputFilter[]{filter_space, filter_speChat});
    }
}
