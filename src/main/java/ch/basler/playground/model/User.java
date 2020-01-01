package ch.basler.playground.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("screen_name")
  private String screenName;

  @JsonProperty("location")
  private String location;

  @JsonProperty("description")
  private String description;

  @JsonFormat(pattern = "E MMM dd HH:mm:ss Z yyyy", locale = "en")
  @JsonProperty("created_at")
  private Date createdAt;

  private Map<String, Object> optional = new HashMap<>();

  public User() {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Map<String, Object> getOptional() {
    return optional;
  }

  public void setOptional(Map<String, Object> optional) {
    this.optional = optional;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", screenName=" + screenName + ", location=" + location
        + ", description=" + description + ", createdAt=" + createdAt + "]";
  }

}