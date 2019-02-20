package com.ng.crud.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Event
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-13T16:09:38.079Z")

//TODO: activate list's!

//@Entity - SQL Databases
@Document
public class Event
{
  @JsonProperty("id")
  @Id
  private String id = null;

  @NotBlank
  @JsonProperty("title")
  private String title = null;

  @NotNull
  @JsonProperty("startDate")
  private Long startDate = null;

  @JsonProperty("endDate")
  private Long endDate = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("location")
  private String location = null;

  @JsonProperty("description")
  private String description = null;
/*
  @JsonProperty("participants")
  @Valid
  private List<Long> participants = null;
*/
  @JsonProperty("createdBy")
  private Long createdBy = null;

  @JsonProperty("lastUpdatedBy")
  private Long lastUpdatedBy = null;

  @JsonProperty("createdAt")
  private Long createdAt = null;

  @JsonProperty("lastUpdatedAt")
  private Long lastUpdatedAt = null;

  public Event id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Event title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Event startDate(Long startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(value = "")


  public Long getStartDate() {
    return startDate;
  }

  public void setStartDate(Long startDate) {
    this.startDate = startDate;
  }

  public Event endDate(Long endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(value = "")


  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
  }

  public Event type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Event location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
  **/
  @ApiModelProperty(value = "")


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Event description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
/*
  public Event participants(List<Long> participants) {
    this.participants = participants;
    return this;
  }

  public Event addParticipantsItem(Long participantsItem) {
    if (this.participants == null) {
      this.participants = new ArrayList<Long>();
    }
    this.participants.add(participantsItem);
    return this;
  }
*/
/*
  /**
   * Get participants
   * @return participants
  * * /
  @ApiModelProperty(value = "")


  public List<Long> getParticipants() {
    return participants;
  }

  public void setParticipants(List<Long> participants) {
    this.participants = participants;
  }
*/
  public Event createdBy(Long createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * Get createdBy
   * @return createdBy
  **/
  @ApiModelProperty(value = "")


  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public Event lastUpdatedBy(Long lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  /**
   * Get lastUpdatedBy
   * @return lastUpdatedBy
  **/
  @ApiModelProperty(value = "")


  public Long getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(Long lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Event createdAt(Long createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")


  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public Event lastUpdatedAt(Long lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

  /**
   * Get lastUpdatedAt
   * @return lastUpdatedAt
  **/
  @ApiModelProperty(value = "")


  public Long getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public void setLastUpdatedAt(Long lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(this.id, event.id) &&
        Objects.equals(this.title, event.title) &&
        Objects.equals(this.startDate, event.startDate) &&
        Objects.equals(this.endDate, event.endDate) &&
        Objects.equals(this.type, event.type) &&
        Objects.equals(this.location, event.location) &&
        Objects.equals(this.description, event.description) &&
//        Objects.equals(this.participants, event.participants) &&
        Objects.equals(this.createdBy, event.createdBy) &&
        Objects.equals(this.lastUpdatedBy, event.lastUpdatedBy) &&
        Objects.equals(this.createdAt, event.createdAt) &&
        Objects.equals(this.lastUpdatedAt, event.lastUpdatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, startDate, endDate, type, location, description, /*participants,*/ createdBy, lastUpdatedBy, createdAt, lastUpdatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
//    sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    lastUpdatedBy: ").append(toIndentedString(lastUpdatedBy)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    lastUpdatedAt: ").append(toIndentedString(lastUpdatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
  @PrePersist
  public void prePersist() {
      createdAt = System.currentTimeMillis();
      //createdBy = LoggedUser.get();	//TODO
      this.preUpdate();
  }
  @PreUpdate
  public void preUpdate() {
      lastUpdatedAt = System.currentTimeMillis();
      //updatedBy = LoggedUser.get();	//TODO
  }
}
