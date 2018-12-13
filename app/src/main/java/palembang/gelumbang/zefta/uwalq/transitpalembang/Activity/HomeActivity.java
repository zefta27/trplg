package palembang.gelumbang.zefta.uwalq.transitpalembang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.dijkstra.MainActivity;
import palembang.gelumbang.zefta.uwalq.transitpalembang.dijkstra.MainWebActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvLokasiAwal, tvLokasiAkhir;
    private int PLACE_PICKER_REQ_START = 1;
    private int PLACE_PICKER_REQ_END = 2;
    private EditText etLokasiAwal, etLokasiAkhir;
    String sPlace, sPlace1;
    private String sPosisiAwal, sPosisiAkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        tvLokasiAwal = (TextView) findViewById(R.id.tv_tujuanawal);
//        tvLokasiAkhir = (TextView) findViewById(R.id.tv_tujuanakhir);
        etLokasiAwal = (EditText) findViewById(R.id.et_tujuanawal);
        etLokasiAkhir = (EditText) findViewById(R.id.et_tujuanakhir);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void onClickStartLcocation(View v)
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(HomeActivity.this), PLACE_PICKER_REQ_START);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    public void onclickEndocation(View v)
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(HomeActivity.this), PLACE_PICKER_REQ_END);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onCLickGo(View v)
    {
//
        EditText et1 = (EditText) findViewById(R.id.et_tujuanawal);
        EditText et2 = (EditText) findViewById(R.id.et_tujuanakhir);
        String sEt1 = et1.getText().toString();
        String sEt2 = et2.getText().toString();
        if (sEt1.matches("")||sEt2.matches("")){
            Toast.makeText(this, "Anda Harus Mengisi Posisi Awal dan Tujuan", Toast.LENGTH_SHORT).show();
        }else
        {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.putExtra("LOKASI_AWAL",sPosisiAwal);
            intent.putExtra("LOKASI_AKHIR",sPosisiAkhir);
            intent.putExtra("ADDRESS_AWAL",sEt1);
            intent.putExtra("ADDRESS_AKHIR",sEt2);

            startActivity(intent);
        }

//        Toast.makeText(this, sPlace+"...."+sPlace1, Toast.LENGTH_SHORT).show();


    }
    public void onCLickGoMysql(View v){
        EditText et1 = (EditText) findViewById(R.id.et_tujuanawal);
        EditText et2 = (EditText) findViewById(R.id.et_tujuanakhir);
        String sEt1 = et1.getText().toString();
        String sEt2 = et2.getText().toString();
        if (sEt1.matches("")||sEt2.matches("")){
            Toast.makeText(this, "Anda Harus Mengisi Posisi Awal dan Tujuan", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(HomeActivity.this, MainWebActivity.class);
            intent.putExtra("LOKASI_AWAL", sPosisiAwal);
            intent.putExtra("LOKASI_AKHIR", sPosisiAkhir);
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQ_START) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format(
//                        "Place: %s \n" +
//                                "Alamat: %s \n" +
//                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
                sPlace = String.format(place.getAddress()+" ");
                sPosisiAwal = String.format(String.format(place.getLatLng().latitude+","+place.getLatLng().longitude));
//                sPlace = String.format(place.getLatLng().latitude+","+place.getLatLng().longitude);
                Toast.makeText(this, sPlace, Toast.LENGTH_SHORT).show();
                etLokasiAwal.setText(sPlace);
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                intent.putExtra("ADDRESS_AWAL",sPlace);
            }
        }
        if (requestCode == PLACE_PICKER_REQ_END) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format(
//                        "Place: %s \n" +
//                                "Alamat: %s \n" +
//                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
//                sPlace1 = String.format(place.getLatLng().latitude+","+place.getLatLng().longitude);
                sPlace1 = String.format(place.getAddress()+" ");
                sPosisiAkhir = String.format(String.format(place.getLatLng().latitude+","+place.getLatLng().longitude));

                Toast.makeText(this, sPlace1, Toast.LENGTH_SHORT).show();
                etLokasiAkhir.setText(sPlace1);
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                intent.putExtra("ADDRESS_AKHIR",sPlace1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wisata) {
           Intent intent = new Intent(HomeActivity.this, TempatWisataActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
