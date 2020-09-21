package com.lovezz.dto;

public class RawDataDto
{
    private String nickName;

    private int gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public String getNickName(){
        return this.nickName;
    }
    public void setGender(int gender){
        this.gender = gender;
    }
    public int getGender(){
        return this.gender;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }
    public String getAvatarUrl(){
        return this.avatarUrl;
    }
}
