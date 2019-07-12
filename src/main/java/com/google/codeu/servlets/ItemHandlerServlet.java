package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.google.codeu.data.Message;

import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/my-item-handler")
public class ItemHandlerServlet extends HttpServlet {

    public void processRequest(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
    IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (ServletOutputStream out = response.getOutputStream()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ItemHandlerServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String user = request.getParameter("user");
            String text = request.getParameter("text");
            String message = request.getParameter("message");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String lostOrFound = request.getParameter("lostOrFound");

            Message m = new Message(user, text, title, description, location, lostOrFound);


            request.setAttribute("message", m);

            RequestDispatcher rd = request.getRequestDispatcher("item-page.jsp");

            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");

        }


    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

}