package com.google.codeu.data;
import java.util.UUID;

public class Item {
    private UUID id;
    private String user;
    private String text;
    private long timestamp;
    private String imageUrl;

    private String title;
    private String description;
    private String location;
    private String lostOrFound;

    /**
     * Constructs a new {@link Item} posted by {@code user} with {@code text}
     * content. Generates a
     * random ID and uses the current system time for the creation time.
     */
    public Item(String user, String text, String imageUrl, String title,
                String description,
                   String location,
                   String lostOrFound) {
        this(UUID.randomUUID(), user, text, System.currentTimeMillis(),
                imageUrl, title, description, location, lostOrFound);
    }

    public Item(UUID id, String user, String text, long timestamp,
                String imageUrl, String title, String description,
                String location,
                   String lostOrFound) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.location = location;
        this.lostOrFound = lostOrFound;
    }

    public UUID getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

