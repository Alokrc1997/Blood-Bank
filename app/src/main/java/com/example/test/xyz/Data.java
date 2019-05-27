package com.example.test.xyz;

public class Data
{
    String nam,phon,blood;
    public Data()
    {
    }
    public Data(String nam, String phon)
    {
        this.nam = nam;
        this.phon = phon;
    }

    public Data(String nam, String phon, String blood) {
        this.nam = nam;
        this.phon = phon;
        this.blood = blood;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getPhon() {
        return phon;
    }

    public void setPhon(String phon) {
        this.phon = phon;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
