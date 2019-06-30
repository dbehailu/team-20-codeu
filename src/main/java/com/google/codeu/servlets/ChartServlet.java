package com.google.codeu.servlets;


import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.Scanner;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/lostandfoundchart")
public class ChartServlet extends HttpServlet {

  private JsonArray lostItemArray;

  // This class could be its own file if we needed it outside this servlet
  private static class lostItem {
    String location;
    int count;

    private lostItem (String location, int count) {
      this.location = location;
      this.count = count;
    }
  }

  @Override
 public void init() {
   lostItemArray = new JsonArray();
   Gson gson = new Gson();
   Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/lost-item.csv"));
   scanner.nextLine(); //skips first line (the csv header)
   while(scanner.hasNextLine()) {
     String line = scanner.nextLine();
     String[] cells = line.split(",");

     String curLocation = cells[0];
     int curCount = Integer.valueOf(cells[1]);

     lostItemArray.add(gson.toJsonTree(new lostItem(curLocation, curCount)));
   }
   scanner.close();
 }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.getOutputStream().println(lostItemArray.toString());
  }




}
