package palembang.gelumbang.zefta.uwalq.transitpalembang.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;

public class tangkapDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangkap_data);

        Bundle bundle = getIntent().getExtras();

        String lAwal = bundle.getString("LOKASI_AWAL");
        String lAkhir = bundle.getString("LOKASI_AKHIR");


        TextView tvAwal = (TextView) findViewById(R.id.textView2);
        TextView tvAkhir = (TextView) findViewById(R.id.textView3);

        tvAwal.setText(lAwal);
        tvAkhir.setText(lAkhir);
    }
}
