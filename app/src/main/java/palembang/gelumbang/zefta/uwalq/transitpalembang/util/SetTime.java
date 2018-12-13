package palembang.gelumbang.zefta.uwalq.transitpalembang.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 15/10/18.
 */

public class SetTime {


    public Calendar jumlahJam(Date dWaktuPermulaan, int hasilTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dWaktuPermulaan);
        cal.add(Calendar.SECOND, hasilTime);
        return cal;
    }

    public String getFormatTanggal(Date tanggal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String tanggalStr;
        tanggalStr = dateFormat.format(tanggal);
        return tanggalStr;

    }

    public String getJam() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String estimasiWaktu(int hasilTime) {
        int s = 0, jam = 0, menit = 0;
        s = hasilTime;
        if (s>=3600) {
            while (s>=3600) {
                jam++;
                s -= 3600;
            }
        }
        if (s>=60) {
            while (s >= 60) {
                menit++;
                s -= 60;
            }
        }
        String res = menit+" menit "+s+" detik";
        return res;
    }

    public int totalTime(List<Double> listBobot, Double kecepatan) {
        int hasil;
        Double result;
        hasil = 0;
        for (Double jarak : listBobot) {
            result = (jarak / kecepatan)*3600;
            long total = Math.round(result);
            hasil = (int) (hasil + total);
        }

        return hasil;
    }

    public Double addTime(Double jarak , Double kecepatan)
    {
        Double result;
        result = (jarak / kecepatan)*3600;
        long total = Math.round(result);
        return Double.valueOf(total);
    }
    public Double getDetik (String jam)
    {
        String[] expJam = jam.split(":");
        Double dJam = Double.parseDouble(expJam[0])*3600;
        Double dMenit = Double.parseDouble(expJam[1])*60;
        Double dDetik  = Double.parseDouble(expJam[2]);
        Double dResult = dJam+ dMenit+ dDetik;
        return dResult;
    }
    public String addTimeNow(String now, Double resultTime)
    {

        Double dNow = getDetik(now);
        Double result = dNow  + resultTime;
        long total = Math.round(result);
        int s = 0, jam = 0, menit = 0;
        s = (int) total;
        if (s>=3600) {
            while (s>=3600) {
                jam++;
                s -= 3600;
            }
        }
        if (s>=60) {
            while (s >= 60) {
                menit++;
                s -= 60;
            }
        }
        String res = menit+" : "+s;
        return res;
    }


    public List<String> estimatedTime(List<Double> listBobot, Double kecepatan) {
        Double result;
        List<String> listResult = new ArrayList<String>();
        for (Double jarak : listBobot) {
            result = (jarak / kecepatan)*3600;
            long total = Math.round(result);
            int s = 0, jam = 0, menit = 0;
            s = (int) total;
            if (s>=3600) {
                while (s>=3600) {
                    jam++;
                    s -= 3600;
                }
            }
            if (s>=60) {
                while (s >= 60) {
                    menit++;
                    s -= 60;
                }
            }
            String res = menit+" m "+s+" d";
            listResult.add(res);

        }

        return listResult;
    }
}
