
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import util.PinyinUtils;

public class SpellHelper {
    //将中文转换为英文
    public static String getEname(String name)
    {
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        //输出设置，大小写，音标方式
        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);

       /*
         声调表示：
           HanyuPinyinToneType.WITHOUT_TONE//无声调表示
           HanyuPinyinToneType.WITH_TONE_MARK//声调符号表示
           HanyuPinyinToneType.WITH_TONE_NUMBER//声调数字表示
          */
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        try {
            return PinyinHelper.toHanYuPinyinString(name, pyFormat, "",true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return "error";
    }

    //姓、名的第一个字母需要为大写
    public static String getUpEname(String name) {
        char[] strs = name.toCharArray();
        String newname = null;

        //名字的长度
        if (strs.length == 2) {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1]));
        } else if (strs.length == 3)
        {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1] + strs[2]));
        }
        else if (strs.length == 4)
        {
            newname = toUpCase(getEname("" + strs[0] + strs[1])) + " "
                    + toUpCase(getEname("" + strs[2] + strs[3]));
        } else
        {
            newname = toUpCase(getEname(name));
        }
        return newname;
    }

    //首字母大写
    private static String toUpCase(String str) {
        return (str.substring(0, 1)).toUpperCase() +
                str.substring(1);
    }


    public static void main(String[] args) {
        System.out.println(getUpEname("单雄信"));


        String strs = PinyinUtils.getPingYin("田单");
        String strs1 = PinyinUtils.getPingYin("王单");
        System.out.println("输出结果:"+strs+"\n输出结果:"+strs1);


    }

}