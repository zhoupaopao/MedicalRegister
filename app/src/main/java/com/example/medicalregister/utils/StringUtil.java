package com.example.medicalregister.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * <p>
 * 说明：处理一下字符串的常用操作，字符串校验等
 */
public class StringUtil {

    /**
     * 判断字符串是否为空或者空字符串 如果字符串是空或空字符串则返回true，否则返回false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static String selectOne(String s) {
        String str = "";
        if (s.contains("=") && s.length() > 8) {
            String[] strArray = s.split("=");
            for (int i = 0; i < strArray.length; i++) {
                str = strArray[i];
                if (str.length() > 6) {
                    return Double.parseDouble(str) + "";
                }
            }
        }

        return "";
    }

    public static String upsideDown(String s) {
        if (s == null)
            return "";
        String p = null;
        int num = s.length();
        char c[] = new char[num];
        for (int i = num - 1; i > -1; i--) {
            c[num - 1 - i] = s.charAt(i);
        }
        p = new String(c);
        return selectOne(p);
    }
    public static String upsideDown1(String s) {
        if (s == null)
            return "";
        String p;
        int num = s.length();
        char[] c = new char[num];
        for (int i = num - 1; i > -1; i--) {
            c[num - 1 - i] = s.charAt(i);
        }
        p = new String(c);
        String[] strArray = p.split("=");
        return Double.parseDouble(strArray[1])+"";
    }
    public static String upsideDown2(String s) {//新蓝牙设备提供的解析
        if (s == null)
            return "";
        String p;
        int num = s.length();
        char[] c = new char[num];
        for (int i = num - 1; i > -1; i--) {
            c[num - 1 - i] = s.charAt(i);
        }
        p = new String(c);
        String[] strArray = p.split("=");
        return Double.parseDouble(strArray[0])+"";
    }

    public static String removeSpace(String str) {
        str = str.replace(" ", "");
        return str;
    }

    public static int str22Int(String str) {
        int i = Integer.parseInt(str);
        return i;
    }

    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        // String strPattern =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否身份证号码
     *
     * @param stridNo
     * @return
     */
    public static boolean isIdNo(String stridNo) {
        // String strPattern =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(stridNo);
        return m.matches();
    }

    /**
     * 验证输入为中文，英文和空格
     *
     * @param strName
     * @return
     */
    public static boolean isRealName(String strName) {
        // String strPattern =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "[\\u4e00-\\u9fa5]+[a-zA-Z]*|[a-zA-Z]+[\\u4e00-\\u9fa5]*|[a-zA-Z]+/[a-zA-Z]+|[\\u4e00-\\u9fa5]+/[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strName);
        return m.matches();
    }

    /**
     * 验证号码 手机号 固话均可
     */
    public static boolean isPhoneOrPhoneValid(String phoneNumber) {
        boolean isValid = false;

        // String expression =
        // "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        // 手机号
        String expression = "(^(0|86|17951)?(13([0-9])|15([012356789])|17([678])|18([0-9])|14([57]))([0-9]){8})";
        // 400 800
        // String expression2 = "(^([48])00\\d)";
        // 固话
        // String expression3 = "((\\d{2,5}\\d{7,8}))";
        // String expression3 = "((\\d{2,5}-\\d{7,8}))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);
        // Pattern pattern2 = Pattern.compile(expression2);
        // Pattern pattern3 = Pattern.compile(expression3);

        Matcher matcher = pattern.matcher(inputStr);
        // Matcher matcher2 = pattern2.matcher(inputStr);
        // Matcher matcher3 = pattern3.matcher(inputStr);

        // if (matcher.matches() || matcher2.matches() || matcher3.matches()) {
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 验证手机号码手机号是否合法
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(17[0-3,5-8])|(18[0-9]))(\\d){8}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * /**
     * MD5加密（多数用于密码加密、一般都在后台操作）
     *
     * @param secret_key
     * @return
     */
    public static String encodeMd5(String secret_key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(secret_key.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    /**
     * 判断是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否超过指定字符数
     *
     * @param content
     * @param stringNum 指定字符数 如：140
     * @return
     */
    public static boolean countStringLength(String content, int stringNum) {
        int result = 0;
        if (content != null && !"".equals(content)) {
            char[] contentArr = content.toCharArray();
            if (contentArr != null) {
                for (int i = 0; i < contentArr.length; i++) {
                    char c = contentArr[i];
                    if (isChinese(c)) {
                        result += 3;
                    } else {
                        result += 1;
                    }
                }
            }
        }
        if (result > stringNum * 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将网络图片路径md5加密作为文件名
     *
     * @param imageUrl
     * @return
     */
    public static String createImageName(String imageUrl) {
        return encodeMd5(imageUrl) + ".jpg";
    }

    /**
     * 将网络图片路径md5加密作为文件名,可以设置图片类型
     *
     * @param imageUrl
     * @param imgSuffix
     * @return
     */
    public static String createImageName(String imageUrl, String imgSuffix) {
        return encodeMd5(imageUrl) + imgSuffix;
    }

    /**
     * 将null转换为""
     *
     * @param str
     * @return
     */
    public static String trimNull(String str) {
        if (str == null || "null".equalsIgnoreCase(str))
            return "";
        else
            return str;
    }

    /**
     * 把异常信息打印出来
     *
     * @param e
     * @return
     */
    public static String getExceptionInfo(Exception e) {
        String result = "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        result = e.getMessage() + "/r/n" + sw.toString();
        pw.close();
        try {
            sw.close();
        } catch (IOException e1) {

        }
        return result;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 头像文件名
     *
     * @param sid
     * @return
     */
    public static String createAvatarFileName(String sid) {
        return "avatar_" + sid + ".jpg";
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static String getTime() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。

        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;

        return hour + ":" + minute + ":" + second;
    }

    /**
     * 获取系统当前小时
     *
     * @return
     */
    public static int getHour() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        return t.hour; // 0-23
    }

    // 保留一位小数
    public static String getDoubleOne(String str) {
        Double c1 = Double.parseDouble(str);

        int b = (int) Math.round(c1 * 10); // 小数点后两位前移，并四舍五入
        double c = ((double) b / 10.0); // 还原小数点后两位
        if ((c * 10) % 5 != 0) {
            int d = (int) Math.round(c); // 小数点前移，并四舍五入
            c = ((double) d); // 还原小数点
        }
        String cao = c + "";
        return cao;
    }

    // 保留两位小数
    public static String getDoubleTwo(String str) {
        Double c1 = Double.parseDouble(str);

        return String.format("%.2f", c1);

        // int b = (int) Math.round(c1 * 10); // 小数点后两位前移，并四舍五入
        // double c = ((double) b / 10.0); // 还原小数点后两位
        // // if ((c * 10) % 5 != 0) {
        // // int d = (int) Math.round(c); // 小数点前移，并四舍五入
        // // c = ((double) d); // 还原小数点
        // // }
        // String cao = c + "";
        // return cao;
    }

    /**
     * 小于十数字补零
     *
     * @param i
     * @return
     */
    public static String addzero(int i) {
        String f;
        if (i < 10) {
            f = "0" + i;
        } else {
            f = i + "";
        }
        return f;
    }

    /**
     * 字符全角化
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 转换全角到半角
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String full2Half(final String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }

        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 65281 && c[i] <= 65374) {
                c[i] = (char) (c[i] - 65248);
            } else if (c[i] == 12288) { // 空格
                c[i] = (char) 32;
            }
        }
        return new String(c);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeDiffer = currentTime - lastClickTime;
        if (0 < timeDiffer && timeDiffer < 500) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 以为数字的字符串补0处理
     *
     * @param num
     * @return
     */
    public static String addZero(int num) {
        return (num < 10 ? "0" : "") + num;
    }


    public static String addZero(String s) {
        int num = 0;
        try {
            num = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
            num = 0;
        }
        return addZero(num);
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 四舍五入保留确定位数小数
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值
     */
    public static double round(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static String format4Four(String code) {
        return code.replaceAll("\\d{4}(?!$)", "$0-");
    }


    /**
     * 判断字符串浮点数（double和float）
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断整数（int）
     *
     * @param str
     * @return
     */
    public boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 判断帐号是否是支付宝账号
     *
     * @param tempNum
     * @return
     */
    public static boolean isAlipay(String tempNum) {

        String pppp = "/^0?(13[0-9]|15[012356789]|18[0123456789]|14[0123456789]|17[0123456789])[0-9]{8}$/";
        String pppp22 = "/^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$/";

        return Pattern.compile(pppp).matcher(tempNum).matches() || Pattern.compile(pppp22).matcher(tempNum).matches();
    }

    /**
     * 限制edittext 不能输入中文
     *
     * @param editText
     */
    public static void setEdNoChinese(final EditText editText) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = s.toString();
                //注意返回值是char数组
                char[] stringArr = txt.toCharArray();
                for (int i = 0; i < stringArr.length; i++) {
                    //转化为string
                    String value = new String(String.valueOf(stringArr[i]));
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(value);
                    if (m.matches()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editText.addTextChangedListener(textWatcher);
    }


    /**
     * 密码复杂度验证
     *
     * @param
     */
    public static boolean isPwdAvailable(String pwd) {

        //复杂（同时包含数字，字母，特殊符号）
        String a1 = "\"^^(?![a-zA-z]+$)(?!\\\\d+$)(?![!@#$%^&*_-]+$)(?![a-zA-z\\\\d]+$)(?![a-zA-z!@#$%^&*_-]+$)(?![\\\\d!@#$%^&*_-]+$)[a-zA-Z\\\\d!@#$%^&*_-]+$\"";
        //简单（只包含数字或字母）
        String a2 = "\"^(?:\\\\d+|[a-zA-Z]+|[!@#$%^&*]+)$\"";
        //中级（包含字母和数字）
        String a3 = "\"^(?![a-zA-z]+$)(?!\\\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\\\d!@#$%^&*]+$\"";


        Pattern p = Pattern.compile(a3);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


    /**
     * 数组里是否包含某字符串
     *
     * @param str
     * @param arrays
     * @return
     */
    public static boolean isContain(String str, String[] arrays) {
        if (arrays == null) return false;
        for (String s : arrays) {
            if (s.equals(str))
                return true;
        }
        return false;
    }

    /**
     * double类型数据保留${length}位小数
     *
     * @param num
     * @param length
     * @return
     */
    public static double doubleSetScale(double num, int length) {
        BigDecimal bg = new BigDecimal(num);
        return bg.setScale(length).doubleValue();
    }

    public static String doubleRemoveEndZero(double num) {
        return BigDecimal.valueOf(num).stripTrailingZeros().toPlainString();
    }


    public static String removeNull(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    /**
     * ascII 转string
     *
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    public static String killDouble(double value) {
        int b = (int) value;
        if (value == b) {
            return String.valueOf(b);
        } else {
            return String.valueOf(value);
        }

    }

}
