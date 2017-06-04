package kncstorytellerapp.mothergoose;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by HP-PC on 4/26/2016.
 */
public class bookClass {


    String sname;
    String story;
    String tagone;
    String tagtwo;
    String tagthree;
    String owner;
    String ownerName;
    String likesCount;
    String contributionCount;
    @JsonIgnore
    String key;

    public bookClass(){

    }

    public bookClass(String sname, String story, String tagone, String tagtwo, String tagthree, String owner, String key,String owName) {
        this.sname = sname;
        this.story = story;
        this.tagone = tagone;
        this.tagtwo = tagtwo;
        this.tagthree = tagthree;
        this.owner = owner;
        this.ownerName = owName;
        this.likesCount="0";
        this.contributionCount="0";

        this.key = key;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTagone() {
        return tagone;
    }

    public void setTagone(String tagone) {
        this.tagone = tagone;
    }

    public String getTagtwo() {
        return tagtwo;
    }

    public void setTagtwo(String tagtwo) {
        this.tagtwo = tagtwo;
    }

    public String getTagthree() {
        return tagthree;
    }

    public void setTagthree(String tagthree) {
        this.tagthree = tagthree;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getContributionCount() {
        return contributionCount;
    }

    public void setContributionCount(String contributionCount) {
        this.contributionCount = contributionCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
