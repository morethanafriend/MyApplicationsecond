package com.example.administrator.myapplication;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

/**
 * Created by Administrator on 2016/9/22.
 */
public class StringUtil {

    /**
     * 判断是否为 null 或空字符串
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        if (string == null || string.isEmpty() || "null".equals(string)) {
            return true;
        }
        return false;
    }

    public static String getFormatString(String in) {
        return isNull(in) ? "暂无数据" : in;
    }

    public static String getFillTenString(long time) {
        return time < 0 ? "00" :
                time < 10 ? "0" + time : "" + time;
    }

    public static String getPriceString(long price) {
        if (price == 0) {
            return "0";
        }

        String priceString = String.format("%.2f", price * 1.0 / 100);
        return priceString.replaceAll("\\.00", "");
    }

    public static String getHidPhone(String cellphone) {
//        if (cellphone == null || cellphone.length() < 11) {
//            return "";
//        } else {
//            return cellphone.substring(0, 3) + "****" + cellphone.substring(7, 11);
//        }
        return cellphone;
    }

    public static SpannableString getSpannableStringOfDiscovery(Show show) {

        int quantityStart = 3;
        int quantityEnd = quantityStart + (show.getSellingQuantity() + "").length();

        int postPriceStart = quantityEnd + 5;
        int postPriceEnd = postPriceStart + StringUtil.getPriceString(show.getMinPostingPrice()).length();

        int ticketPriceStart = postPriceEnd + 8;
        int ticketPriceEnd = ticketPriceStart + StringUtil.getPriceString(show.minPrice).length();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("在售 ")
                .append(show.getSellingQuantity())
                .append(" 张 /￥")
                .append(StringUtil.getPriceString(show.getMinPostingPrice()))
                .append(" 起 / 原价￥")
                .append(StringUtil.getPriceString(show.minPrice))
                .append(" 起");

        SpannableString spannableString = new SpannableString(stringBuilder);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e04e44")), quantityStart, quantityEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#e04e44")), postPriceStart - 1, postPriceEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new AbsoluteSizeSpan(14, true), quantityStart, quantityEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), postPriceStart - 1, postPriceEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), ticketPriceStart - 1, ticketPriceEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //斜杠
        spannableString.setSpan(new AbsoluteSizeSpan(15, true), quantityEnd + 3, quantityEnd + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(15, true), postPriceEnd + 3, postPriceEnd + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //中划线
        if (show.getMinPostingPrice() < show.minPrice) {
            spannableString.setSpan(new StrikethroughSpan(), ticketPriceStart - 1, ticketPriceEnd + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    public static double getDoubleFromString(String in) {
        try {
            return Double.parseDouble(in);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
