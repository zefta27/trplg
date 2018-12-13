package palembang.gelumbang.zefta.uwalq.transitpalembang.dijkstra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import palembang.gelumbang.zefta.uwalq.transitpalembang.Activity.PerkiraanActivity;
import palembang.gelumbang.zefta.uwalq.transitpalembang.Activity.TimelineApproximationActivity;
import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.model.ReqJalur;
import palembang.gelumbang.zefta.uwalq.transitpalembang.util.HttpHandler;

public class MainWebActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback {
    GoogleMap googleMap;
    SQLHelper dbHelper;
    Cursor cursor;


    public String __global_endposition = null;
    public String __global_startposition = null;
    public int __global_simpul_awal;
    public int __global_simpul_akhir;
    public String __global_old_simpul_awal = "";
    public String __global_old_simpul_akhir = "";
    public int __global_maxRow0;
    public int __global_maxRow1;
    private String[][] __global_graphArray;
    private LatLng __global_yourCoordinate_exist = null;
//    public String LOKASI_AWAL, LOKASI_AKHIR;

    String LOKASI_AWAL;
    String LOKASI_AKHIR;
    String url;
    public String sTitikAwalLat;
    public String sTitikAwalLng;
    public String sTitikTujuanLat;
    public String sTitikTujuanLng;
    List<ReqJalur> mItems;

    /** The default socket timeout in milliseconds */
    public static final int MY_SOCKET_TIMEOUT_MS = 2500;


    /** The default number of retries */
    public static final int DEFAULT_MAX_RETRIES = 1;

    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web);

        Bundle bundle = getIntent().getExtras();
        LOKASI_AWAL = bundle.getString("LOKASI_AWAL");
        LOKASI_AKHIR = bundle.getString("LOKASI_AKHIR");

        String[] sLokasiAwal = LOKASI_AWAL.split(",");
        String[] sLokasiAkhir = LOKASI_AKHIR.split(",");

        sTitikAwalLat = sLokasiAwal[0];
        sTitikAwalLng = sLokasiAkhir[1];
        sTitikTujuanLat = sLokasiAkhir[0];
        sTitikTujuanLng = sLokasiAkhir[1];

//start copyan
        dbHelper = new SQLHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (Exception ioe) {
            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.petaTransit);
//        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);
//		((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.petta)).getMapAsync(this);;


        // event map
        // Query DB to show all SMK
        dbHelper = new SQLHelper(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
//  end coyan


//        Double dLatUser = Double.parseDouble(sLokasiAwal[0]);
//        Double dlngUser = Double.parseDouble(sLokasiAwal[1]);
//        Double dlat_endposition = Double.parseDouble(sLokasiAkhir[0]);
//        Double dlng_endposition = Double.parseDouble(sLokasiAkhir[1]);
//        START VOLLEY
//        final TextView mTextView = (TextView) findViewById(R.id.text);
//
//
//// Instantiate the RequestQueue.
        final ProgressDialog pd = new ProgressDialog(this);
        RequestQueue queue = Volley.newRequestQueue(this);

        url = "http://transitpalembang.kajianku.com/fungsi/dijkstra/"+sTitikAwalLat+
                "/"+sTitikAwalLng+"/"+sTitikTujuanLat+"/"+sTitikTujuanLng;
//        JsonObjectRequest reqRequest = new JsonObjectRequest(Request.Method)

//        START PURE PARSING JSON
        new GetJson().execute();

//        END PURE PARSING JSON

//        final JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("volley", response.toString());
////                        progressDialog.cancel();
//                        try {
//                            Log.d("volleyyy", response.toString());
//                            String sAwalLat = response.getString("lat_awal");
//                            String sAwalLng = response.getString("lng_awal");
//                            String sTujuanLat = response.getString("lat_akhir");
//                            String sTujuanLng = response.getString("lng_akhir");
//
//
//                            TextView tvAwalLat = (TextView) findViewById(R.id.tvAwalLat);
//                            TextView tvAwalLng = (TextView) findViewById(R.id.tvAwalLng);
//                            TextView tvTujuanLat = (TextView) findViewById(R.id.tvTujuanLat);
//                            TextView tvTujuanLng = (TextView) findViewById(R.id.tvTujuanLng);
//
//                            tvAwalLat.setText(sAwalLat);
//                            tvAwalLng.setText(sAwalLng);
//                            tvTujuanLat.setText(sTujuanLat);
//                            tvTujuanLng.setText(sTujuanLng);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("volleyerror", error.getMessage());
//            }
//        }
//        );

//        queue.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(jsonObjectRequest);
//        VolleyLatLng.getInstance().addToRequestQueue(reqData);

// Add the request to the RequestQueue.
//        END  VOLLEY
    }

    public void onClickPerkiraan(View view)
    {
        Intent intent = new Intent(MainWebActivity.this, TimelineApproximationActivity.class);
        startActivity(intent);
    }
    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        Bundle bundle = getIntent().getExtras();
        LOKASI_AWAL = bundle.getString("LOKASI_AWAL");
        LOKASI_AKHIR = bundle.getString("LOKASI_AKHIR");

        __global_endposition = LOKASI_AKHIR;
        __global_startposition = LOKASI_AKHIR;

        String[] sLokasiAwal = LOKASI_AWAL.split(",");
        String[] sLokasiAkhir = LOKASI_AKHIR.split(",");


        Double dLatUser = Double.parseDouble(sLokasiAwal[0]);
        Double dlngUser = Double.parseDouble(sLokasiAwal[1]);
        Double dlat_endposition = Double.parseDouble(sLokasiAkhir[0]);
        Double dlng_endposition = Double.parseDouble(sLokasiAkhir[1]);
        __global_yourCoordinate_exist = new LatLng(dLatUser, dlng_endposition);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatUser, dlngUser), 8.0f));

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        googleMap = mMap;

        String allLatlng = dLatUser+",,"+dlngUser+"=="+dlat_endposition+",,"+dlng_endposition;
//
//		Toast.makeText(this, allLatlng , Toast.LENGTH_SHORT).show();
        Log.e("All Lat Lng",allLatlng);
        mMap.addMarker(new MarkerOptions().position(new LatLng(dLatUser, dlngUser)).title("Titik Awal"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(dlat_endposition, dlng_endposition)).title("Titik Akhir"));
    }


    private class GetJson extends AsyncTask<Void, Void, Void>{
        String sAwalLat, sAwalLng, sTujuanLat, sTujuanLng, sTercepat, sSimpulAwal, sSimpulAkhir;
        final ProgressDialog progressDialog = new ProgressDialog(MainWebActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
//            TextView tvAwalLat = (TextView) findViewById(R.id.tvAwalLat);
//            TextView tvAwalLng = (TextView) findViewById(R.id.tvAwalLng);
//            TextView tvTujuanLat = (TextView) findViewById(R.id.tvTujuanLat);
//            TextView tvTujuanLng = (TextView) findViewById(R.id.tvTujuanLng);
//            TextView tvTercepat = (TextView) findViewById(R.id.tvTercepat);


//            tvAwalLat.setText(sAwalLat);
//            tvAwalLng.setText(sAwalLng);
//            tvTujuanLat.setText(sTujuanLat);
//            tvTujuanLng.setText(sTujuanLng);
//            tvTercepat.setText(sTercepat);

            progressDialog.dismiss();
            Toast.makeText(MainWebActivity.this, "Rute : "+sTercepat, Toast.LENGTH_SHORT).show();
            if (sTercepat!=null)
            {
                String[] exp = sTercepat.split("->");
                Log.i("TRANSITPLG",sTercepat);
//			int iExp = exp.length;
//			for (int i = 0;i<iExp;i++){
//				Log.e("JAlur", exp[i] );
//			}
                // DRAW JALUR ANGKUTAN UMUM
                // =========================================
                try {
                    drawJalur(sTercepat, exp, sSimpulAwal, sSimpulAkhir);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.i("TRANSITPLG","Elol");
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // Making a request to url and getting response
            String TAG = "Pure Parsng JSON";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    sAwalLat = jsonObj.getString("lat_awal");
                    sAwalLng = jsonObj.getString("lng_awal");
                    sTujuanLat = jsonObj.getString("lat_akhir");
                    sTujuanLng = jsonObj.getString("lng_akhir");
                    sSimpulAwal = jsonObj.getString("simpulAwal");
                    sSimpulAkhir = jsonObj.getString("simpulAkhir");
                    sTercepat = jsonObj.getString("visited");

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
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

    public void drawJalur(String alg, String[] exp, String simpulAwal, String simpulAkhir) throws JSONException {

        int start = 0;

        // GAMBAR JALURNYA
        // ======================
        dbHelper = new SQLHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        for (int i = 0; i < exp.length - 1; i++) {
            ArrayList<LatLng> lat_lng = new ArrayList<LatLng>();
            cursor = db.rawQuery("SELECT jalur FROM graph where simpul_awal =" + exp[start] + " and simpul_tujuan =" + exp[(++start)], null);
//           Log.i("Database","SELECT jalur FROM graph where simpul_awal =" + exp[start] + " and simpul_tujuan =" + exp[(++start)]);
            cursor.moveToFirst();
            // dapatkan koordinat Lat,Lng dari field koordinat (3)
            String json = cursor.getString(0).toString();
            // get JSON
            JSONObject jObject = new JSONObject(json);
            JSONArray jArrCoordinates = jObject.getJSONArray("coordinates");
            // get coordinate JSON
            for (int w = 0; w < jArrCoordinates.length(); w++) {
                JSONArray latlngs = jArrCoordinates.getJSONArray(w);
                Double lats = latlngs.getDouble(0);
                Double lngs = latlngs.getDouble(1);
                Log.i("TRANSITPLG", "drawJalur: "+lats+","+lngs);
                lat_lng.add(new LatLng(lats, lngs));
            }
            // buat rute
            PolylineOptions jalurBiasa = new PolylineOptions();
            jalurBiasa.addAll(lat_lng).width(5).color(0xff4b9efa).geodesic(true);
            googleMap.addPolyline(jalurBiasa);

        }


        // BUAT MARKER UNTUK YOUR POSITION AND DESTINATION POSITION
        // ======================
        // your position
//		googleMap.addMarker(new MarkerOptions()
//				.position(__global_yourCoordinate_exist)
//				.title("Your position")
//				.snippet("Your position")
//				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        String[] exp_endCoordinate = __global_endposition.split(",");
        double lat_endPosition = Double.parseDouble(exp_endCoordinate[0]);
        double lng_endPosition = Double.parseDouble(exp_endCoordinate[1]);
        LatLng endx = new LatLng(lat_endPosition, lng_endPosition);

        // destination position
        googleMap.addMarker(new MarkerOptions()
                .position(endx)
                .title("Destination position")
                .snippet("Destination position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        // TENTUKAN JENIS ANGKUTAN UMUM YANG MELEWATI JALUR TERSEBUT
        // ==========================================================
        // misal exp[] = 1->5->6->7
        int m = 0;


//        String[] awal = __global_old_simpul_awal.split("-"); // misal 4-5
        String[] awal = simpulAwal.split("-"); // misal 4-5
        String[] akhir = simpulAkhir.split("-"); // misal 8-7
//        String[] akhir = __global_old_simpul_akhir.split("-"); // misal 8-7

        int ganti_a = 0;
        int ganti_b = 0;
        int simpulAwalDijkstra = Integer.parseInt(exp[0]);

        String gabungSimpul_all = "";
        Map<String, ArrayList> listAngkutanUmum = new HashMap<String, ArrayList>();
        ArrayList<Integer> listSimpulAngkot = new ArrayList<Integer>();

        // cari simpul_old sebelum koordinat dipecah
        // misal 4-5 dipecah menjadi 4-6-5, berarti simpul_old awal = 5, simpul_old akhir = 4
        for (int e = 0; e < (exp.length - 1); e++) {

            if (e == 0) { // awal

                // dijalankan jika hasil algo hanya 2 simpul, example : 4->5
                if (exp.length == 2 /* 2 simpul (4-5)*/) {

                    // ada simpul baru di awal (10) dan di akhir (11), example 10->11
                    if (exp[0].equals(String.valueOf(__global_maxRow0)) && exp[1].equals(String.valueOf(__global_maxRow1))) {

                        if (String.valueOf(__global_maxRow0).equals(akhir[0])) {
                            ganti_b = Integer.parseInt(akhir[1]);
                        } else {
                            ganti_b = Integer.parseInt(akhir[0]);
                        }

                        if (String.valueOf(ganti_b).equals(awal[0])) {
                            ganti_a = Integer.parseInt(awal[1]);
                        } else {
                            ganti_a = Integer.parseInt(awal[0]);
                        }
                    } else {
                        // ada simpul baru di awal (10), example 10->5
                        // maka cari simpul awal yg oldnya
                        if (exp[0].equals(String.valueOf(__global_maxRow0))) {

                            if (exp[1].equals(awal[1])) {
                                ganti_a = Integer.parseInt(awal[0]);
                            } else {
                                ganti_a = Integer.parseInt(awal[1]);
                            }
                            ganti_b = Integer.parseInt(exp[1]);
                        }
                        // ada simpul baru di akhir (10), example 5->10
                        // maka cari simpul akhir yg oldnya
                        else if (exp[1].equals(String.valueOf(__global_maxRow0))) {

                            if (exp[0].equals(akhir[0])) {
                                ganti_b = Integer.parseInt(akhir[1]);
                            } else {
                                ganti_b = Integer.parseInt(akhir[0]);
                            }
                            ganti_a = Integer.parseInt(exp[0]);
                        }
                        // tidak ada penambahan simpul sama sekali
                        else {
                            ganti_a = Integer.parseInt(exp[0]);
                            ganti_b = Integer.parseInt(exp[1]);
                        }
                    }

        			/*
        			// 4 == 4
        			if(exp[0].equals(awal[0])){
            			ganti_a = Integer.parseInt(awal[0]);
            			//ganti_b = Integer.parseInt(awal[1]);
        			}else{
            			ganti_a = Integer.parseInt(awal[1]);
            			//ganti_b = Integer.parseInt(awal[0]);
        			}

        			if(String.valueOf(ganti_a).equals(akhir[0])){
            			ganti_b = Integer.parseInt(akhir[1]);
            			//ganti_b = Integer.parseInt(awal[1]);
        			}else{
            			ganti_b = Integer.parseInt(akhir[0]);
            			//ganti_b = Integer.parseInt(awal[0]);
        			}
        			*/

        			/*
        			 *         			// 4 == 4
        			if(exp[0].equals(awal[0])){
            			ganti_a = Integer.parseInt(akhir[0]);
            			ganti_b = Integer.parseInt(awal[1]);
        			}else{
            			ganti_a = Integer.parseInt(awal[1]);
            			ganti_b = Integer.parseInt(akhir[0]);
        			}
        			 */

                }
                // hasil algo lebih dr 2 : 4->5->8->7-> etc ..
                else {
                    if (exp[1].equals(awal[1])) { // 5 == 5
                        ganti_a = Integer.parseInt(awal[0]); // hasil 4
                    } else {
                        ganti_a = Integer.parseInt(awal[1]); // hasil 5
                    }

                    ganti_b = Integer.parseInt(exp[++m]);
                }
            } else if (e == (exp.length - 2)) { // akhir

                if (exp[(exp.length - 2)].equals(akhir[1])) { // 7 == 7
                    ganti_b = Integer.parseInt(akhir[0]); // hasil 8
                } else {
                    ganti_b = Integer.parseInt(akhir[1]); // hasil 7
                }

                ganti_a = Integer.parseInt(exp[m]);

            } else { // tengah tengah
                ganti_a = Integer.parseInt(exp[m]);
                ganti_b = Integer.parseInt(exp[++m]);
            }

            gabungSimpul_all += "," + ganti_a + "-" + ganti_b + ","; // ,1-5,
            String gabungSimpul = "," + ganti_a + "-" + ganti_b + ","; // ,1-5,

            cursor = db.rawQuery("SELECT * FROM angkutan_umum where simpul like '%" + gabungSimpul + "%'", null);
            cursor.moveToFirst();

            ArrayList<String> listAngkutan = new ArrayList<String>();

            for (int ae = 0; ae < cursor.getCount(); ae++) {
                cursor.moveToPosition(ae);
                listAngkutan.add(cursor.getString(1).toString());
            }

            listAngkutanUmum.put("angkutan" + e, listAngkutan);

            // add simpul angkot
            listSimpulAngkot.add(Integer.parseInt(exp[e]));

        }


        String replace_jalur = gabungSimpul_all.replace(",,", ","); //  ,1-5,,5-6,,6-7, => ,1-5,5-6,6-7,
        cursor = db.rawQuery("SELECT * FROM angkutan_umum where simpul like '%" + replace_jalur + "%'", null);
        cursor.moveToFirst();
        cursor.moveToPosition(0);

        // ada 1 angkot yg melewati jalur dari awal sampek akhir
        if (cursor.getCount() > 0) {

            String siAngkot = cursor.getString(1).toString();

            // get coordinate
            cursor = db.rawQuery("SELECT jalur FROM graph where simpul_awal = '" + simpulAwalDijkstra + "'", null);
            cursor.moveToFirst();
            String json_coordinate = cursor.getString(0).toString();

            // manipulating JSON
            JSONObject jObject = new JSONObject(json_coordinate);
            JSONArray jArrCoordinates = jObject.getJSONArray("coordinates");
            JSONArray latlngs = jArrCoordinates.getJSONArray(0);

            // first latlng
            Double lats = latlngs.getDouble(0);
            Double lngs = latlngs.getDouble(1);

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lats, lngs))
                    .title("Transmusi")
                    .snippet(siAngkot)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))).showInfoWindow();

            // die()
            return;
        }

        // ada 2 atau lebih angkot yg melewati jalur dari awal sampek akhir
        int banyakAngkot = 0;
        int indexUrut = 0;
        int indexSimpulAngkot = 1;
        int lengthAngkutan = listAngkutanUmum.size();
        Map<String, ArrayList> angkotFix = new HashMap<String, ArrayList>();

        for (int en = 0; en < lengthAngkutan; en++) {

            // temporary sementara sebelum di retainAll()
            ArrayList<String> temps = new ArrayList<String>();
            for (int u = 0; u < listAngkutanUmum.get("angkutan0").size(); u++) {
                temps.add(listAngkutanUmum.get("angkutan0").get(u).toString());
            }

            if (en > 0) {
                ArrayList listSekarang1 = listAngkutanUmum.get("angkutan0");
                ArrayList listSelanjutnya1 = listAngkutanUmum.get("angkutan" + en);

                // intersection
                listSekarang1.retainAll(listSelanjutnya1);

                if (listSekarang1.size() > 0) {

                    listSimpulAngkot.remove(indexSimpulAngkot);
                    --indexSimpulAngkot;

                    listAngkutanUmum.remove("angkutan" + en);

                    if (en == (lengthAngkutan - 1)) {

                        ArrayList<String> tempDalam = new ArrayList<String>();
                        for (int es = 0; es < listSekarang1.size(); es++) {
                            tempDalam.add(listSekarang1.get(es).toString());
                        }

                        angkotFix.put("angkutanFix" + indexUrut, tempDalam);
                        ++indexUrut;
                    }
                } else if (listSekarang1.size() == 0) {

                    angkotFix.put("angkutanFix" + indexUrut, temps);

                    ArrayList<String> tempDalam = new ArrayList<String>();
                    for (int es = 0; es < listSelanjutnya1.size(); es++) {
                        tempDalam.add(listSelanjutnya1.get(es).toString());
                    }

                    //if(en == 1) break;
                    listAngkutanUmum.get("angkutan0").clear();
                    listAngkutanUmum.put("angkutan0", tempDalam);

                    //if(en != (listAngkutanUmum.size() - 1)){
                    listAngkutanUmum.remove("angkutan" + en);
                    //}

                    ++indexUrut;

                    if (en == (lengthAngkutan - 1)) {

                        ArrayList<String> tempDalam2 = new ArrayList<String>();
                        for (int es = 0; es < listSelanjutnya1.size(); es++) {
                            tempDalam2.add(listSelanjutnya1.get(es).toString());
                        }

                        angkotFix.put("angkutanFix" + indexUrut, tempDalam2);
                        ++indexUrut;
                    }
                }

                ++indexSimpulAngkot;
            }
        }

        for (int r = 0; r < listSimpulAngkot.size(); r++) {
            String simpulx = listSimpulAngkot.get(r).toString();
            // get coordinate simpulAngkutan
            cursor = db.rawQuery("SELECT jalur FROM graph where simpul_awal = '" + simpulx + "'", null);
            cursor.moveToPosition(0);

            // dapatkan koordinat Lat,Lng dari field koordinat (3)
            String json = cursor.getString(0).toString();

            // get JSON
            JSONObject jObject = new JSONObject(json);
            JSONArray jArrCoordinates = jObject.getJSONArray("coordinates");

            // get first coordinate JSON
            JSONArray latlngs = jArrCoordinates.getJSONArray(0);
            Double lats = latlngs.getDouble(0);
            Double lngs = latlngs.getDouble(1);

            LatLng simpulAngkot = new LatLng(lats, lngs);
            String siAngkot = angkotFix.get("angkutanFix" + r).toString();

            if (r == 0) {
                googleMap.addMarker(new MarkerOptions()
                        .position(simpulAngkot)
                        .title("Angkot")
                        .snippet(siAngkot)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))).showInfoWindow();
            } else {
                googleMap.addMarker(new MarkerOptions()
                        .position(simpulAngkot)
                        .title("Angkot")
                        .snippet(siAngkot)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
            }
        }

    }
}
