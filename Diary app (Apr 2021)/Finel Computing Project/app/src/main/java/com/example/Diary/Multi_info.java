package com.example.Diary;

public class Multi_info {
    private String ID_Number;
    private String Title;
    private String Diary;
    private String UserID;
    private String Additional;

    public Multi_info() {
    }

    public Multi_info(String ID_Number, String Title, String Diary, String Additional, String UserID) {
        this.ID_Number = ID_Number;
        this.Title = Title;
        this.Diary = Diary;
        this.Additional = Additional;
        this.UserID = UserID;
    }

    public String getID_Number() {
        return ID_Number;
    }

    public void setID_Number(String ID_Number) {
        this.ID_Number = ID_Number;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDiary() {
        return Diary;
    }

    public void setDiary(String Diary) {
        this.Diary = Diary;
    }


    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String Additional) {
        this.Additional = Additional;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
}
