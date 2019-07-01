package com.google.codeu.servlets;

import java.io.IOException;

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
@WebServlet("/lostOrFound")
public class LostOrFoundServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with the "title" section for a particular user.
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

    if(userData == null || userData.getLostOrFound() == null) {
      return;
    }

    response.getOutputStream().println(userData.getLostOrFound());
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
    User user = datastore.getUser(userEmail);
    if(user==null){
      String title = "No title.";
      String description = "No description.";
      String location = "No location.";
      String lostOrFound = Jsoup.clean(request.getParameter("lostOrFound"), Whitelist.none());
      user = new User(userEmail, title, description, location, lostOrFound);
      datastore.storeUser(user);
    } else {
      String title = user.getTitle();
      String description = user.getDescription();
      String location =  user.getLocation();
      String lostOrFound = Jsoup.clean(request.getParameter("lostOrFound"), Whitelist.none());
      user = new User(userEmail, title, description, location, lostOrFound);
      datastore.storeUser(user);
    }

    response.sendRedirect("/user-page.jsp?user=" + userEmail);
  }
}