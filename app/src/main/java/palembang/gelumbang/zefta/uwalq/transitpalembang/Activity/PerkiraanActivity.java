//package palembang.gelumbang.zefta.uwalq.transitpalembang.Activity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baoyachi.stepview.VerticalStepView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
//import palembang.gelumbang.zefta.uwalq.transitpalembang.TimeLine;
//import palembang.gelumbang.zefta.uwalq.transitpalembang.dijkstra.SQLHelper;
//import palembang.gelumbang.zefta.uwalq.transitpalembang.util.HttpHandler;
//import palembang.gelumbang.zefta.uwalq.transitpalembang.adapter.TimelineAdapter;
//
//public class PerkiraanActivity extends AppCompatActivity {
//
//    VerticalStepView verticalStepView;
//    private String intentTercepat;
//    private TextView tvTercepat, tvResultHalte;
//    SQLHelper dbHelper;
//    Cursor cursor;
//    Double KECEPATAN = Double.valueOf(30000);
//    TextView tvTotalTime;
//    Date waktuPermulaan = new Date();
//    List<String> sources = new ArrayList<>();
//    List<String> listHasil = new ArrayList<>();
//    String sResultJarak;
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    Button btnLihatRv ;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_perkiraan);
//
//        tvTotalTime = (TextView)findViewById(R.id.tvTotalTime);
//        tvResultHalte = (TextView) findViewById(R.id.tvResultHalte);
//
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.rvTimeline);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//
//        Bundle bundle = getIntent().getExtras();
//        intentTercepat = bundle.getString("INTENT_TERCEPAT");
//        btnLihatRv = (Button) findViewById(R.id.lihatRv);
////        tvTercepat = (TextView) findViewById(R.id.tvTercepat);
////        tvTercepat.setText(intentTercepat);
//
//
//        String kirimRuteKeWeb = "";
//        String[] exp = intentTercepat.split("->");
//        String[] expJalur = new String[exp.length];
//        for (int i = 0; i < exp.length; i++) {
//            if (i != exp.length - 1) {
//                expJalur[i] = exp[i] + "-" + exp[i + 1];
//                Log.i("ARR JALUR",expJalur[i]);
//                kirimRuteKeWeb = kirimRuteKeWeb+"q"+exp[i];
//            }
//        }
//        String urlKirimRute = "http://transitpalembang.kajianku.com/fungsi/carihalte/"+kirimRuteKeWeb;
//        Log.i("Kirim RUte Ke Web",urlKirimRute);
//        new GetJson(urlKirimRute).execute();
//
//
//
//
//        dbHelper = new SQLHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        List<Double> listBobot = new ArrayList<Double>();
//        List<String> listResult = new ArrayList<String>();
//        List<TimeLine> listTimeline = new ArrayList<TimeLine>();
//        int hasilTime = 0;
//        for (int i = 1; i < expJalur.length - 1; i++) {
//
//            String[] expSimpul = expJalur[i].split("-");
//            String simpulAwal = expSimpul[0];
//            String simpulAkhir = expSimpul[1];
//
//            cursor = db.rawQuery("SELECT bobot FROM graph where simpul_awal =" + simpulAwal + " and simpul_tujuan =" + simpulAkhir, null);
////			Log.i("Database","SELECT jalur FROM graph where simpul_awal =" + exp[start] + " and simpul_tujuan =" + exp[(++start)]);
//            cursor.moveToFirst();
//            String bobot = cursor.getString(0).toString();
//            Double dBobot = Double.parseDouble(bobot);
//            listBobot.add(dBobot);
//
//            Log.i("DISTANCE",expJalur[i]+": "+bobot);
//
//            listResult = estimatedTime(listBobot, KECEPATAN);
//            int totalTime = totalTime(listBobot, KECEPATAN);
//            hasilTime = hasilTime + totalTime;
//            for (String res : listResult){
//
//                sResultJarak = res;
//                Log.i("RESUL JARAK",sResultJarak);
//
//            }
//            TimeLine timeLine = new TimeLine("A","B","C");
//            timeLine.setSimpulAkhir(sResultJarak);
//            listTimeline.add(timeLine);
//            listHasil.add(sResultJarak);
////            sources.add(expJalur[i] +" : "+hasil);
//
//
//        }
//        mAdapter = new TimelineAdapter(listTimeline);
//        mRecyclerView.setAdapter(mAdapter);
//
//        String sEstimasiWaktu = estimasiWaktu(hasilTime);
//        String sJamSekarang = getJam();
//        Calendar cal = jumlahJam(waktuPermulaan, hasilTime);
//        String waktuSekarang = getFormatTanggal(
//                cal.getTime());
//
//        tvTotalTime.setText("Jam : "+sJamSekarang+", Total waktu yang ditempuh :"+sEstimasiWaktu+", Waktu yang dibutuhkan "+waktuSekarang);
//
//
//    }
//
//    private Calendar jumlahJam(Date dWaktuPermulaan, int hasilTime) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(dWaktuPermulaan);
//        cal.add(Calendar.SECOND, hasilTime);
//        return cal;
//    }
//
//    private String getFormatTanggal(Date tanggal) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        String tanggalStr;
//        tanggalStr = dateFormat.format(tanggal);
//        return tanggalStr;
//
//    }
//
//    private String getJam() {
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        Date date = new Date();
//        return dateFormat.format(date);
//    }
//    private String estimasiWaktu(int hasilTime) {
//        int s = 0, jam = 0, menit = 0;
//        s = hasilTime;
//        if (s>=3600) {
//            while (s>=3600) {
//                jam++;
//                s -= 3600;
//            }
//        }
//        if (s>=60) {
//            while (s >= 60) {
//                menit++;
//                s -= 60;
//            }
//        }
//        String res = menit+" menit "+s+" detik";
//        return res;
//    }
//
//    private int totalTime(List<Double> listBobot, Double kecepatan) {
//        int hasil;
//        Double result;
//        hasil = 0;
//        for (Double jarak : listBobot) {
//            result = (jarak / kecepatan)*3600;
//            long total = Math.round(result);
//            hasil = (int) (hasil + total);
//        }
//
//        return hasil;
//    }
//
//    private List<String> estimatedTime(List<Double> listBobot, Double kecepatan) {
//        Double result;
//       List<String> listResult = new ArrayList<String>();
//        for (Double jarak : listBobot) {
//            result = (jarak / kecepatan)*3600;
//            long total = Math.round(result);
//            int s = 0, jam = 0, menit = 0;
//            s = (int) total;
//            if (s>=3600) {
//                while (s>=3600) {
//                    jam++;
//                    s -= 3600;
//                }
//            }
//            if (s>=60) {
//                while (s >= 60) {
//                    menit++;
//                    s -= 60;
//                }
//            }
//            String res = menit+" menit "+s+" detik";
//            listResult.add(res);
//
//        }
//
//        return listResult;
//    }
//    private class GetJson extends AsyncTask<Void, Void, Void> {
//        String sAwalLat, sAwalLng, sTujuanLat, sTujuanLng, sTercepat, sSimpulAwal, sSimpulAkhir;
//        String sResultHalte;
//        final ProgressDialog progressDialog = new ProgressDialog(PerkiraanActivity.this);
//        String urlRute;
//        public GetJson(String kirimRuteKeWeb) {
//             urlRute = kirimRuteKeWeb;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
//            if (sResultHalte!=null)
//            {
//                setTimeline(sResultHalte, listHasil);
//                tvResultHalte.setText(sResultHalte);
////                String[] expResultHalte = sResultHalte.split("-");
////                adapter = new MasjidAdapter(MainActivity.this, feedItemList);
////                mRecyclerView.setAdapter(adapter);
//
//            }
//            else{
//                Log.i("TRANSITPLG","Elol");
//            }
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            // Making a request to url and getting response
//            String TAG = "Pure Parsng JSON";
//            HttpHandler sh = new HttpHandler();
//            String jsonStr = sh.makeServiceCall(urlRute);
//            Log.e(TAG, "Url Rute : " + urlRute);
//            Log.e(TAG, "Response from url: " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    sResultHalte = jsonObj.getString("result_nama_halte");
//
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
//
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
//
//            return null;
//        }
//    }
//
//    private void setTimeline(String sResultHalte, List<String> listHasil) {
//
//        int j = 0;
//        String[] exp = sResultHalte.split(",");
//
//
//        for (String lHasil : listHasil) {
//            sources.add(exp[j] +" : "+lHasil);
//            j++;
//        }
//        verticalStepView = (VerticalStepView) findViewById(R.id.stepView);
//
//        verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size())
//                .reverseDraw(false)
//                .setStepViewTexts(sources)
//                .setLinePaddingProportion(1.2f)
//                .setTextSize(12)
//                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#f67720"))
//                .setStepViewComplectedTextColor(Color.parseColor("#f67720"))
//                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this,R.color.uncompleted_text_color))
//                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#f67720"))
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this,R.drawable.ic_circle_orange))
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.ic_circle_orange))
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.ic_circle_orange))
//        ;
//    }
//}
