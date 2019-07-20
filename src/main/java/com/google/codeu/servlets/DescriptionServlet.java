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
import com.google.codeu.data.Message;
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
  * Responds with the "about item" section for a particular user.
  */
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {


    response.setContentType("text/html");

    String m = request.getParameter("message");

    if(m == null || m.equals("")) {
     // Request is invalid, return empty response
     return;
    }

<<<<<<< HEAD
    Message message = datastore.getMessage(m);

    if(message == null || message.getDescription() == null) {
     return;
    }

    response.getOutputStream().println(message.getDescription());
    }
=======
  if(userData == null || userData.getDescription() == null || userData.getTitle() ==null || userData.getLocation() ==null
  || userData.getLostOrFound() ==null) {
    return;
  }

  Gson gson = new Gson();
  String json = gson.toJson(userData);

  response.getOutputStream().println(json);
 }
>>>>>>> 587fde96cd3d8f58f999c89992dcd86ee77cb4dc

 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

 response.setContentType("text/html");

 String m = request.getParameter("message");

 if(m == null || m.equals("")) {
     // Request is invalid, return empty response
     return;
 }

<<<<<<< HEAD
 Message message = datastore.getMessage(m);

 if(message==null){
   String title = "No title.";
   String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
   String location = "No location.";
   String lostOrFound = "No lost or found information.";
   String user = "No user.";
   String text = "No text.";
   message = new Message(user, text, title, description, location,
             lostOrFound);
     datastore.storeMessage(message);
 } else {
   String title = message.getTitle();
   String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
   String location =  message.getLocation();
   String lostOrFound = message.getLostOrFound();
   String user = message.getUser();
   String text = message.getText();
   message = new Message(user, text, title, description, location,
             lostOrFound);
   datastore.storeMessage(message);
 }
=======
   String userEmail = userService.getCurrentUser().getEmail();

   String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
   String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());
   String location = Jsoup.clean(request.getParameter("location"), Whitelist.none());
   String lostOrFound = Jsoup.clean(request.getParameter("lostOrFound"), Whitelist.none());
   User user = new User(userEmail, title, description, location, lostOrFound);
   datastore.storeUser(user);
>>>>>>> 587fde96cd3d8f58f999c89992dcd86ee77cb4dc

     String title = message.getTitle();
     response.sendRedirect("/item-page.jsp?message=" + title);
 }
}
