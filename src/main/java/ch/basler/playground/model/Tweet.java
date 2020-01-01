package ch.basler.playground.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {

  @JsonProperty("id")
  private Long id;

  @JsonFormat(pattern = "E MMM dd HH:mm:ss Z yyyy", locale = "en")
  @JsonProperty("created_at")
  private Date createdAt;

  @JsonProperty("text")
  private String text;

  @JsonProperty("retweeted")
  private Boolean retweeted;

  @JsonProperty("user")
  private User user;

  private Map<String, Object> optional = new HashMap<>();

  public Tweet() {
  }

//getters/setters for all properties omitted for brevity
  @JsonAnySetter
  public void addOptional(String name, Object value) {
    optional.put(name, value);
  }

  @JsonAnyGetter
  public Object getOptional(String name) {
    return optional.get(name);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getRetweeted() {
    return retweeted;
  }

  public void setRetweeted(Boolean retweeted) {
    this.retweeted = retweeted;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Tweet [id=" + id + ", createdAt=" + createdAt + ", text=" + text + ", retweeted=" + retweeted + ", user="
        + user + "]";
  }

}