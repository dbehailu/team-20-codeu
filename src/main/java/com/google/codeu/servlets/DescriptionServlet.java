package com.google.codeu.servlets;

import java.io.IOException;

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

    Message message = datastore.getMessage(m);

    if(message == null || message.getDescription() == null) {
     return;
    }

    response.getOutputStream().println(message.getDescription());
    }

 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

 response.setContentType("text/html");

 String m = request.getParameter("message");

 if(m == null || m.equals("")) {
     // Request is invalid, return empty response
     return;
 }

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

     String title = message.getTitle();
     response.sendRedirect("/item-page.jsp?message=" + title);
 }
}
