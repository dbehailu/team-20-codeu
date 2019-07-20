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

package com.google.codeu.data;

import java.util.UUID;

/** A single message posted by a user. */
public class Message {

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
   * Constructs a new {@link Message} posted by {@code user} with {@code text} content. Generates a
   * random ID and uses the current system time for the creation time.
   */
<<<<<<< HEAD
  public Message(String user, String text, String title, String description,
                 String location,
                 String lostOrFound) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis(),
            title, description, location, lostOrFound);
  }

  public Message(UUID id, String user, String text, long timestamp,
                 String title, String description, String location,
                 String lostOrFound) {
=======
  public Message(String user, String text) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis(), "/elaine.png");
  }

  /**
   * Constructs a new {@link Message} posted by {@code user} with {@code text} content. Generates a
   * random ID and uses the current system time for the creation time.
   */
  public Message(String user, String text, String imageUrl) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis(), imageUrl);
  }

  public Message(UUID id, String user, String text, long timestamp, String imageUrl) {
>>>>>>> 587fde96cd3d8f58f999c89992dcd86ee77cb4dc
    this.id = id;
    this.user = user;
    this.text = text;
    this.timestamp = timestamp;
<<<<<<< HEAD

    this.title = title;
    this.description = description;
    this.location = location;
    this.lostOrFound = lostOrFound;
=======
    this.imageUrl = imageUrl;
>>>>>>> 587fde96cd3d8f58f999c89992dcd86ee77cb4dc
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

<<<<<<< HEAD

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
=======
  public String getImageUrl() {
    return imageUrl;
>>>>>>> 587fde96cd3d8f58f999c89992dcd86ee77cb4dc
  }
}
