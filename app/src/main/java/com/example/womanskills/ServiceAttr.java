package com.example.womanskills;

public class ServiceAttr {
    String Id;
    String UserId;
    String Title;
    String Decription;
    String Category;
    String Image1;
    String Image2;
    String Image3;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDecription() {
        return Decription;
    }

    public void setDecription(String decription) {
        Decription = decription;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public ServiceAttr(String id, String userId, String title, String decription, String category, String image1, String image2, String image3) {
        Id = id;
        UserId = userId;
        Title = title;
        Decription = decription;
        Category = category;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
    }

    public ServiceAttr() {
    }
}
