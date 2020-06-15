package util;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * @author liukuiyong
 * 2018-05-23
 *
 */
public class PinyinUtils {

    private static String path= "/pinyin.propertites";

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 患者姓名
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            String  substring = diffPro(inputString);
            char[] input = substring.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0]+" ";
                    } else{
                        output += java.lang.Character.toString(input[i]);
                    }
                    output = output;
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }


        } else {
            return "";
        }
        return output;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音首字母
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 与pinyin.properties中多音字的姓名对比
     * @param inputString 中文姓名
     * @return 百家姓的拼音读法
     */
    private static String diffPro(String inputString){
        Properties properties = new Properties();
        try {
            InputStreamReader reader =new InputStreamReader(PinyinUtils.class.getResourceAsStream(path),"utf-8");
            properties.load(reader);
            reader.close();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Set keyValue = properties.keySet();
        for (Iterator it = keyValue.iterator(); it.hasNext();){
            String key = (String) it.next();

            if(inputString.substring(0, key.length()).equals(key)){
                String value =  inputString.substring(key.length());
                return properties.getProperty(key)+" "+value;
            }
        }
        return inputString;
    }
}