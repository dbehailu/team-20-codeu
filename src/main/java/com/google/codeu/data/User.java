package com.google.codeu.data;

public class User {

  private String email;
  private String title;
  private String description;
  private String location;
  private String lostOrFound;

  public User(String email, String title, String description, String location, String lostOrFound) {
    this.email = email;
    this.title = title;
    this.description = description;
    this.location = location;
    this.lostOrFound = lostOrFound;
  }

  public String getEmail(){
    return email;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getLocation() {
    return location;
  }

  public String getLostOrFound() {
    return lostOrFound;
  }


  public void setEmail(String email){
    this.email = email ;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
