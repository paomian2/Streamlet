package com.streamlet.common.util;


import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Various String utility functions. Most of the functions herein are
 * re-implementations of the ones in apache commons StringUtils.
 */
public class StringUtils {

    public static final String ERROR_TOAST = "网络环境不给力，请检查网络";
    public static final String NO_MORE_DATE = "没有更多数据";
    public static final String EMPTY = "";
    public static final String SUCCESSCODE = "200";
    public static final String SUCCESSDESC = "请求成功";

    public static final String EMPTYNUM = "0";

    public static final String BLANK_SPACE = " ";

    public static String trim(String text) {
        if (text == null)
            return "";
        return text.trim();
    }

    /**
     * @param text
     * @return
     * @Description 判断字符串是否为空
     * @author Created by qinxianyuzou on 2014-12-11.
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().compareTo("") == 0 || text.equals("null");
    }

    /**
     * Description: 返回字符串本身，防空指针.<br/><br/>
     * Author: Create by Yu.Yao on 2016/9/13.<br/><br/>
     */
    public static String removalNull(String defaultString, String text) {
        if (isEmpty(text)) {
            return defaultString;
        }
        return text;
    }

    /**
     * Checks if a String is whitespace, empty ("") or null.</p>
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }


    public static String listToString(List<String> list, String separator) {
        if (list == null || list.size() == 0)
            return "";

        StringBuilder string = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            string.append(list.get(i));
            if (i < list.size() - 1) {
                string.append(separator);
            }
        }

        return string.toString();
    }

    public static String stringArrayToString(String[] array, String separator) {
        if (array == null || array.length == 0)
            return "";
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            string.append(array[i]).append(separator);
        }
        return string.toString();
    }

    public final static int getIntValue(String str) {
        if (str != null && str.length() > 0) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public final static long getLongValue(String str) {
        if (str != null && str.length() > 0) {
            try {
                return Long.parseLong(str);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public static String removeEmptyChar(String src) {
        if (src == null || src.length() == 0)
            return src;
        return src.replaceAll("[\r]*[\n]*[　]*[ ]*", "");
    }

    public static String getFileNameFromUrl(String url) {

        // 名字不能只用这个
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        String extName = "";
        String filename;
        int index = url.lastIndexOf('?');
        if (index > 1) {
            extName = url.substring(url.lastIndexOf('.') + 1, index);
        } else {
            extName = url.substring(url.lastIndexOf('.') + 1);
        }
        filename = hashKeyForDisk(url) + "." + extName;
        return filename;
    }

    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String Md5(String string) {
        if (string != null && !string.equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(string.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
                }
                string = sb.toString();
            } catch (Exception e) {
            }
        }
        return string;
    }

    /**
     * 根据各国的手机号码规则，检测输入
     *
     * @param code
     * @param number
     * @return
     * @time 2011-7-22 上午09:41:04
     * @author:linyg
     */
    public static boolean phoneNumberValid(String code, String number) {
        // 手机号固定在5-20范围内
        if (number.length() < 5 || number.length() > 20) {
            return false;
        }

        String match = "";
        if ("86".equals(code)) {// 中国
            if (number.length() != 11) {
                return false;
            } else {
                match = "^[1]{1}[0-9]{2}[0-9]{8}$";
            }
        }

        // 正则匹配
        if (!"".equals(match)) {
            return number.matches(match);
        }
        return true;
    }

    public static boolean phoneNumberValid(String number) {
        // 手机号固定在5-20范围内
        if (number.length() < 5 || number.length() > 20) {
            return false;
        }

        String match = "";
        if (number.length() != 11) {
            return false;
        } else {
            // match = "^[1]{1}[0-9]{2}[0-9]{8}$";
            // match = "^(13[0-9]|14[5|7]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
            match = "^(1[3456789])\\d{9}$";

        }

        // 正则匹配
        if (!"".equals(match)) {
            return number.matches(match);
        }
        return true;
    }

    /**
     * 判断邮箱地址是否有效限
     */
    public static boolean isEmailAddValid(String address) {
        if (address != null && address.length() > 0) {
            char[] cAddress = address.toCharArray();
            for (char c : cAddress) {
                if (c > 127) {
                    return false;
                }
            }

            Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            Matcher m = p.matcher(address);
            return m.matches();
        }
        return false;
    }

    /**
     * 判断密码是否有效
     * <p>
     * 0 -- 非法；1 -- 正确； 2 -- 不一致
     */
    public static int isPasswordValid(String password, String repeated) {
        if (password != null) {
            int len = password.length();
            if (len >= 6 && len <= 16) {
                char[] cPsw = password.toCharArray();
                boolean wrongChar = false;
                for (char c : cPsw) {
                    if (c >= 128) { // 找到非ascii码
                        wrongChar = true;
                        break;
                    }
                }
                if (!wrongChar) {
                    return password.equals(repeated) ? 1 : 2;
                }
            }
        }
        return 0;
    }

    /**
     * 过滤掉 \r 换行 \n回车
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\r+|\n+");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");// .replaceAll(" +", " ");
        }
        return dest;
    }

    // 半角字符与全角字符混乱所致：这种情况一般就是汉字与数字、英文字母混用,可以避免由于占位导致的排版混乱问题了
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

    public static String ListTOString(ArrayList<String> dataList) {
        if (null == dataList || dataList.size() == 0) {
            return "";
        }
        StringBuilder str_b = new StringBuilder();
        for (String ss : dataList) {
            str_b.append(ss);
            str_b.append("$");
        }
        return str_b.substring(0, str_b.length() - 1);

    }

    public static <T> boolean isEmptyData(List<T> newData) {
        if (newData == null) {
            return true;
        } else {
            if (newData.size() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     * 返回值:Map
     */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    /**
     * 判断字符串是不是全是数字
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 距离格式化
     */
    public static String formatDistance(float distance) {
        DecimalFormat df = new DecimalFormat("0.#");
        if (distance < 1000) {
            return df.format(distance) + "m";
        }
        return df.format(distance / 1000) + "km";
    }

    public static String formatDistance(double distance) {
        DecimalFormat df = new DecimalFormat("0.#");
        if (distance < 1000) {
            return df.format(distance) + "m";
        }
        return df.format(distance / 1000) + "km";
    }

	/*
    校验过程： 
1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。 
2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。 
3、将奇数位总和加上偶数位总和，结果应该可以被10整除。       
	 */

    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
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
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int getStrLength(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }


    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }




    /**
     * 对字符串进行格式化，最多显示长度为length，超过时，其后的内容使用“...代替”
     *
     * @param str
     * @param length
     * @return
     */
    public static String regexString(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length - 4) + "...";
        }
        return str;
    }

    /**
     * 对URL进行格式化：构造形式为URL/XXX/XXX
     *
     * @param params
     * @param url
     * @return
     */
    public static String formatUrl(List<Object> params, String url) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        for (Object param : params) {
            builder.append("/" + param);
        }
        return builder.toString();
    }


    /** 拼接链接完整URL */
    public static String generateUrl(String url, Map<String, Object> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        // web接口: url?key=val&key=val&key=val;
        if (null != params) {
            urlBuilder.append("?");
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> param = iterator.next();
                String key = param.getKey();
                Object value = param.getValue();
                urlBuilder.append(key).append('=').append(value);
                if (iterator.hasNext()) {
                    urlBuilder.append('&');
                }
            }
        }
        return urlBuilder.toString();
    }

    /**
     * 用于字符串为null会报错的控件
     */
    public static String getNoNUllString(String txt) {
        if (txt == null)
            return "";
        return txt;
    }


    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    /**
	 * 分转为元
	 * @param fen
	 * @return
     */
	public static String fromFenToYuan(int fen){
        if(!String.valueOf(fen).matches(CURRENCY_FEN_REGEX)) {
            return "金额格式有误";
        }
        String yuan = BigDecimal.valueOf(fen).divide(new BigDecimal(100)).toString();
        return yuan;
	}

}
