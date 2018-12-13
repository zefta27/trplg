package palembang.gelumbang.zefta.uwalq.transitpalembang;

/**
 * Created by root on 01/10/18.
 */

public class TimeLine {

    public String simpulAwal;
    public String simpulAkhir;
    public String estimasiRute;

    public String getEstimasiJam() {
        return estimasiJam;
    }

    public void setEstimasiJam(String estimasiJam) {
        this.estimasiJam = estimasiJam;
    }

    public String estimasiJam;

    public TimeLine(String simpulAwal, String simpulAkhir, String estimasiRute,String estimasiJam) {
        this.simpulAwal = simpulAwal;
        this.simpulAkhir = simpulAkhir;
        this.estimasiRute = estimasiRute;
        this.estimasiJam = estimasiJam;
    }

    public String getSimpulAwal() {
        return simpulAwal;
    }

    public void setSimpulAwal(String simpulAwal) {
        this.simpulAwal = simpulAwal;
    }

    public String getSimpulAkhir() {
        return simpulAkhir;
    }

    public void setSimpulAkhir(String simpulAkhir) {
        this.simpulAkhir = simpulAkhir;
    }

    public String getEstimasiRute() {
        return estimasiRute;
    }

    public void setEstimasiRute(String estimasiRute) {
        this.estimasiRute = estimasiRute;
    }




}
