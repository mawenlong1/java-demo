import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mawenlong
 * @date 2018/12/29
 */
public class TestJson {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static final SimpleDateFormat format12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final SimpleDateFormat format24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        QueryPrizeGoodsListSendParam param = new QueryPrizeGoodsListSendParam();
        String s = "2018-12-27 13:10:37";
        Date date = simpleDateFormat.parse(s);
        System.out.println(date);
        param.setTran_time(date);
        System.out.println(gsonFactory().toJson(param));

        System.out.println(format12.format(date));
        System.out.println(format24.format(date));

    }

    public static Gson gsonFactory() {
        GsonBuilder gsb = new GsonBuilder();
        gsb.setDateFormat("yyyy-MM-dd hh:mm:ss");
        Gson gs = gsb.create();
        return gs;
    }
}
