package com.google.codeu.data;

public class User {

  private String email;
  private String aboutMe;
  private String suggestion;

  public User(String email, String aboutMe, String suggestion) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.suggestion = suggestion;
  }

  public String getEmail(){
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public String getSuggestion() {
    return suggestion;
  }
}
