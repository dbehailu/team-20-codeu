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
@WebServlet("/about")
public class AboutItemServlet extends HttpServlet {

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

        String item = request.getParameter("item");

        if(item == null || item.equals("")) {
            // Request is invalid, return empty response
            return;
        }

        User userData = datastore.getItem(item);

        if(userData == null || userData.getAboutMe() == null) {
            return;
        }

        response.getOutputStream().println(userData.getAboutMe());
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
        String aboutMe = Jsoup.clean(request.getParameter("about-me"), Whitelist.none());
        String suggestion = datastore.getUser(userEmail).getSuggestion();
        User user = new User(userEmail, aboutMe, suggestion);
        datastore.storeUser(user);

        response.sendRedirect("/user-page.jsp?user=" + userEmail);
    }
}