package palembang.gelumbang.zefta.uwalq.transitpalembang.model;

/**
 * Created by root on 21/02/18.
 */

public class ReqJalur {

    public ReqJalur(){}
    public ReqJalur(String titikAwalLat, String titikAwalLng, String titikTujuanLat, String titikTujuanLng) {
        this.titikAwalLat = titikAwalLat;
        this.titikAwalLng = titikAwalLng;
        this.titikTujuanLat = titikTujuanLat;
        this.titikTujuanLng = titikTujuanLng;
    }

    public String titikAwalLat;
    public String titikAwalLng;
    public String titikTujuanLat;

    public String getTitikAwalLat() {
        return titikAwalLat;
    }

    public void setTitikAwalLat(String titikAwalLat) {
        this.titikAwalLat = titikAwalLat;
    }

    public String getTitikAwalLng() {
        return titikAwalLng;
    }

    public void setTitikAwalLng(String titikAwalLng) {
        this.titikAwalLng = titikAwalLng;
    }

    public String getTitikTujuanLat() {
        return titikTujuanLat;
    }

    public void setTitikTujuanLat(String titikTujuanLat) {
        this.titikTujuanLat = titikTujuanLat;
    }

    public String getTitikTujuanLng() {
        return titikTujuanLng;
    }

    public void setTitikTujuanLng(String titikTujuanLng) {
        this.titikTujuanLng = titikTujuanLng;
    }

    public String titikTujuanLng;

}
