package palembang.gelumbang.zefta.uwalq.transitpalembang.Activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.TimeLine;
import palembang.gelumbang.zefta.uwalq.transitpalembang.adapter.TimelineAdapter;
import palembang.gelumbang.zefta.uwalq.transitpalembang.dijkstra.SQLHelper;
import palembang.gelumbang.zefta.uwalq.transitpalembang.util.HttpHandler;
import palembang.gelumbang.zefta.uwalq.transitpalembang.util.SetTime;

public class TimelineApproximationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<TimeLine> timelineList = new ArrayList<>();
    private  List<String> listResult = new ArrayList<String>();
    private SQLHelper dbHelper;
    private Cursor cursor;
    final private Double KECEPATAN = Double.valueOf(30000);
    String sResultJarak,sHasilWaktu, sJamSekarang;
    private SetTime setTime;
    private Date waktuPermulaan = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_approximation);

        setTime = new SetTime();
        sJamSekarang = setTime.getJam();

        recyclerView = (RecyclerView) findViewById(R.id.rv_timeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new TimelineAdapter(timelineList);

        recyclerView.setAdapter(adapter);
        prepareTimelineData();

    }

    private void prepareTimelineData() {
        dbHelper = new SQLHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bundle bundle = getIntent().getExtras();
        String intentTercepat = bundle.getString("INTENT_TERCEPAT");
        Log.e("INTENT_TERCEPATTImeline",intentTercepat);

        List<Double> listBobot = new ArrayList<>();
        String kirimRuteKeWeb = "";

        String[] exp = intentTercepat.split("->");
        String[] expJalur = new String[exp.length];
        for (int i = 0; i < exp.length; i++) {
            if (i != exp.length - 1) {
                expJalur[i] = exp[i] + "-" + exp[i + 1];
                Log.i("ARR JALUR",expJalur[i]);
                kirimRuteKeWeb = kirimRuteKeWeb+"q"+exp[i];
            }
        }
        String urlKirimRute = "http://transitpalembang.kajianku.com/fungsi/carihalte/"+kirimRuteKeWeb;
        new TimelineApproximationActivity.GetJson(urlKirimRute).execute();
        int hasilTime = 0;
        String sTime = "";
        for (int i = 1; i < expJalur.length - 1; i++) {

            String[] expSimpul = expJalur[i].split("-");
            String sSimpulAwal = expSimpul[0];
            String sSimpulTujuan= expSimpul[1];
                String sQuery = "SELECT bobot FROM graph where simpul_awal =" + sSimpulAwal + " and simpul_tujuan = " + sSimpulTujuan;
                Log.e("Query TImeline", sQuery);
                cursor = db.rawQuery("SELECT bobot FROM graph where simpul_awal =" + sSimpulAwal + " and simpul_tujuan = " + sSimpulTujuan, null);
                cursor.moveToFirst();
                String bobot = cursor.getString(0).toString();
                Double dBobot = Double.parseDouble(bobot);
                listBobot.add(dBobot);


            SetTime setTime =  new SetTime();

//            Double dResultTime = setTime.addTime(dBobot, KECEPATAN);

            listResult = setTime.estimatedTime(listBobot, KECEPATAN);
            int totalTime =  setTime.totalTime(listBobot, KECEPATAN);
            hasilTime = hasilTime + totalTime;

            Calendar cal = jumlahJam(waktuPermulaan,totalTime);
            String waktuSekarang = getFormatTanggal(cal.getTime());

            for (String res : listResult){
                sResultJarak = res;

            }
            sHasilWaktu = sHasilWaktu+","+sResultJarak+"q"+waktuSekarang;


         }

    }
    private Calendar jumlahJam(Date dWaktuPermulaan, int hasilTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dWaktuPermulaan);
        cal.add(java.util.Calendar.SECOND, hasilTime);
        return cal;
    }

    private String getFormatTanggal(Date tanggal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String tanggalStr;
        tanggalStr = dateFormat.format(tanggal);
        return tanggalStr;

    }
    private class GetJson extends AsyncTask<Void, Void, Void> {
        String urlRute, sResult;
        private ProgressDialog dialog = new ProgressDialog(TimelineApproximationActivity.this);

        public GetJson(String kirimRuteKeWeb)
        {
            urlRute = kirimRuteKeWeb;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Harap Tunggu...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }


            if (sResult!=null){

                String[] expResult =  sResult.split(",");
                String[] expEstimasi = sHasilWaktu.split(",");
                for (int i = 0;i<expResult.length;i++)
                {
                    String[] expSimpul = expResult[i].split("-");
                    String expSimpulAwal = expSimpul[0];
                    String expSimpulTujuan = expSimpul[1];
                    String[] exp =  expEstimasi[i+1].split("q");
                    String sEstimasi = exp[0];
                    String sTambahJam = exp[1];
                    TimeLine timeLine = new TimeLine(expSimpulAwal, expSimpulTujuan,sEstimasi,sTambahJam);
                    timelineList.add(timeLine);
                }
                adapter.notifyDataSetChanged();

            }else{
                Log.i("TRANSITPLG","Elol");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            String TAG = "JSON";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urlRute);
            if (jsonStr!=null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    sResult =  jsonObj.getString("result_nama_halte");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
    }

}
