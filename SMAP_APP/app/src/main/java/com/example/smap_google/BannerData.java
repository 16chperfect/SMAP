package com.example.smap_google;

public class BannerData {
    String imagename;
    String tel;

    BannerData(){}
    BannerData(String imagename, String tel){
        this.imagename = imagename;
        this.tel = tel;
    }
    public  String getImagename(){
        return imagename;
    }
    public void setImagename(String imagename)
    {
        this.imagename = imagename;
    }

    public  String getTel(){
        return  tel;
    }

    public void setTel(String tel){
        this.tel = tel;
    }
}
