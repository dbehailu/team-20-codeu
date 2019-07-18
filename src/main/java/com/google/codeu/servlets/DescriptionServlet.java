package com.google.codeu.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/description")
public class DescriptionServlet extends HttpServlet {

  private Datastore datastore;

 @Override
 public void init() {
  datastore = new Datastore();
 }

 /**
  * Responds with the "about me" section for a particular user.
  */
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  response.setContentType("text/html");

  String user = request.getParameter("user");

  if(user == null || user.equals("")) {
   // Request is invalid, return empty response
   return;
  }

  User userData = datastore.getUser(user);

  if(userData == null || userData.getDescription() == null || userData.getTitle() ==null || userData.getLocation() ==null
  || userData.getLostOrFound() ==null) {
    return;
  }

  Gson gson = new Gson();
  String json = gson.toJson(userData);

  response.getOutputStream().println(json);
 }

 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  UserService userService = UserServiceFactory.getUserService();
  if (!userService.isUserLoggedIn()) {
   response.sendRedirect("/index.html");
   return;
 }

   String userEmail = userService.getCurrentUser().getEmail();

   String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
   String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
   String location = Jsoup.clean(request.getParameter("location"), Whitelist.none());
   String lostOrFound = Jsoup.clean(request.getParameter("lostOrFound"), Whitelist.none());
   User user = new User(userEmail, title, description, location, lostOrFound);
   datastore.storeUser(user);




  response.sendRedirect("/user-page.jsp?user=" + userEmail);
 }
}
