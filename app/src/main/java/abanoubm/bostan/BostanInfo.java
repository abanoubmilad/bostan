package abanoubm.bostan;

import java.util.ArrayList;

public class BostanInfo {
    public static String getArabicNum(int num) {
        char[] arr = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        String arabic = "";

        while (num > 0) {
            arabic = arr[num % 10] + arabic;
            num /= 10;
        }

        return arabic;
    }

    public static final ArrayList<String> menuItems = new ArrayList<String>() {
        {
            add("قراءة بستان الرهبان");
            add("قراءة سير وأقوال");
            add("بحث بستان الرهبان");
            add("تابعنا على فيس بوك");
            add("تواصل مع المُطور");
        }
    };
    public static ArrayList<Section> searchResults;
    public static final String[] characters = {"القديس أنطونيوس الكبير",
            "القديس مقاريوس المصري الكبير", "الأنبا باخوميوس",
            "القديس اكليماكوس", "مار إسحق", "الأنبا أرسانيوس", "مار إسحق",
            "الأنبا أغاثون", "الأنبا إيسيذوروس قس الإسقيط",
            "الأنبا موسى الأسود", "الأنبا زكريا", "الأنبا مقاريوس الكبير",
            "من تعاليم الأنبا إشعياء للمبتدئين", "الأنبا يوحنا القصير",
            "الأب الكبير الأنبا سرابيون", "الأنبا أنوب والأنبا بيمين وإخوتهما",
            "من أقوال الأنبا برصنوفيوس", "أنبا آمونيوس الأسقف",
            "القديس أخيلاس", "الأب سلوانس", "أقوال بعضِ القديسين في الدينونة",
            "من تعاليم القديس برصنوفيوس",
            "من أقوال الأب الروحاني المعروف بالشيخ«تعليم للمبتدئين»",
            "من كلام الأب الروحاني المعروف بالشيخ بخصوص التوبة",
            "مقارة الكاتب", "القديس الأنبا دانيال"

    };
    public static final int[] charactersNum = {1, 33, 74, 91, 92, 94, 131, 134, 158,
            174, 189, 199, 211, 223, 249, 258, 295, 271, 275, 278, 389, 591,
            991, 1193, 1204, 1225, 1226};

}
