package palembang.gelumbang.zefta.uwalq.transitpalembang.data;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.model.Transportation;

/**
 * Created by root on 16/10/18.
 */

public class Constant {
    public static List<Transportation> getRideClassData(Context ctx) {
        List<Transportation> items = new ArrayList<>();
        TypedArray images = ctx.getResources().obtainTypedArray(R.array.ride_image);
        String[] names = ctx.getResources().getStringArray(R.array.ride_name);
        String[] prices = ctx.getResources().getStringArray(R.array.ride_price);
        String[] paxs = ctx.getResources().getStringArray(R.array.ride_pax);
        String[] durations = ctx.getResources().getStringArray(R.array.ride_duration);

        for (int i = 0; i < names.length; i++) {
            Transportation item = new Transportation();
            item.class_name = names[i];
            item.image = images.getResourceId(i, -1);
            item.price = prices[i];
            item.pax = paxs[i] + " pax";
            item.duration = durations[i] + " min";
            items.add(item);
        }
        return items;
    }
}
