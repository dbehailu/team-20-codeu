package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/my-form-handler")
public class FormHandlerServlet extends HttpServlet {

    private Datastore datastore;

    @Override
    public void init() {
        datastore = new Datastore();
    }

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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }

        String user = userService.getCurrentUser().getEmail();
        String userText = Jsoup.clean(request.getParameter("text"), Whitelist.relaxed());
        String regex = "(https?://\\S+\\.(png|jpg|jpeg|gif))";
        String replacement = "<img src=\"$1\" />";
        String textWithImagesReplaced = userText.replaceAll(regex, replacement);
        String textWithImagesReplacedMarkdown = basicMarkdown(textWithImagesReplaced);
        String imageUrl = getUploadedFileUrl(request, "image");
        if (imageUrl != null) {
            imageUrl = "<a href=\"" + imageUrl + "\">" + "<img src=\"" + imageUrl + "\">";
            textWithImagesReplacedMarkdown=textWithImagesReplacedMarkdown+imageUrl;
        }
        Message message = new Message(user,textWithImagesReplacedMarkdown,imageUrl);
        datastore.storeMessage(message);

        response.sendRedirect("/user-page.jsp?user=" + user);
    }

    /**
     * Returns a URL that points to the uploaded file, or null if the user didn't upload a file.
     */
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
            System.out.println("<p>the user did not select a file</p>");
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