/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  public String basicMarkdown(String text) {
    /**
    Regex converts anything matching ** [ANYTHING OTHER THAN *s] **
          into <b> [ANYTHING OTHER THAN *s] </b>
    */
      text = text.replaceAll("\\*\\*([^\\*]*)\\*\\*", "<b>$1</b>");

      /**
      Regex for italicizing -- converts * [text] *
          <i> [text] </i>
      */
      text = text.replaceAll("\\*([^\\*]*)\\*", "<i>$1</i>");

      text = text.replaceAll("_([^\\_]*)_", "<u>$1</u>");
      return text;
  }

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<Message> messages = datastore.getMessages(user);
    Gson gson = new Gson();
    String json = gson.toJson(messages);

    response.getWriter().println(json);
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

                // Get the message entered by the user.
    String mes = request.getParameter("message");

        // Get the URL of the image that the user uploaded to Blobstore.
    // String imageUrl = getUploadedFileUrl(request, "image");

        // Output some HTML that shows the data the user entered.
        // A real codebase would probably store these in Datastore.
    // ServletOutputStream out = response.getOutputStream();
    // out.println("<p>Here's the image you uploaded:</p>");
    // out.println("<a href=\"" + imageUrl + "\">");
    // out.println("<img src=\"" + imageUrl + "\" />");
    // out.println("</a>");
    // out.println("<p>Here's the text you entered:</p>");
    // out.println(mes);


    String user = userService.getCurrentUser().getEmail();
    String userText = Jsoup.clean(request.getParameter("message"), Whitelist.relaxed());    
    String regex = "(https?://\\S+\\.(png|jpg|jpeg|gif))";
    String replacement = "<img src=\"$1\" />";
    // String replacement = "(hover over card to view image)";

    // Pattern pattern = Pattern.compile("(.*)");
    // String image = "";
    // Matcher matcher = pattern.matcher(userText);
    // if (matcher.find())
    // {
    //     image = "<img src=" + matcher.group(1) +  "/>";
    // }
    
    String textWithImagesReplaced = userText.replaceAll(regex, replacement);
    // String textWithImagesReplacedMarkdown = basicMarkdown(textWithImagesReplaced);
    Message message = new Message(user,textWithImagesReplaced);
    datastore.storeMessage(message);


    response.sendRedirect("/user-page.jsp?user=" + user);
  }

  private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName){
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get("image");

        // User submitted form without selecting a file, so we can't get a URL. (devserver)
        if(blobKeys == null || blobKeys.isEmpty()) {
            return null;
        }

        // Our form only contains a single file input, so get the first index.
        BlobKey blobKey = blobKeys.get(0);

        // User submitted form without selecting a file, so we can't get a URL. (live server)
        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
        if (blobInfo.getSize() == 0) {
            blobstoreService.delete(blobKey);
            return null;
        }

        // We could check the validity of the file here, e.g. to make sure it's an image file
        // https://stackoverflow.com/q/10779564/873165

        // Use ImagesService to get a URL that points to the uploaded file.
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        return imagesService.getServingUrl(options);
    }
}
