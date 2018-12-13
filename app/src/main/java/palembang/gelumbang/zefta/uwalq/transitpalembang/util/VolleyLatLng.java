package palembang.gelumbang.zefta.uwalq.transitpalembang.util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 21/02/18.
 */

public class VolleyLatLng  extends Application{
    private static final String TAG = VolleyLatLng.class.getSimpleName();
    private static VolleyLatLng instance;
    RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
    public  static  synchronized VolleyLatLng getInstance(){
        return instance;
    }

    private  RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public  <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue (Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelAllRequest(Object req)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(req);
        }
    }



}
