package com.example.creatorconnectbackend.models;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Influencer {
	
	private Long influencerID;

	@NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    private String profileImage;

    private Gender gender;

    // Online alias or handle for the influencer. Cannot be null or empty.
	@NotNull(message = "Influencer name cannot be null")
    @Size(min = 1, message = "Influencer name cannot be empty")
    private String influencerName;

    private String influencerType;

    private List<String> influencerNiche;

    private Long minRate;

    private String previousBrands;

    private String location;

	@Size(max = 250, message = "Bio cannot be more than 250 characters")
    private String bio;

	@Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    private String instagram;
    private String tikTok;
    private String tweeter;  
    private String youtube;
    private String facebook;
    private String twitch;

    private List<String> bestPosts;

    private User user;

    public Long getInfluencerID() {
		return influencerID;
	}

	public void setInfluencerID(Long influencerID) {
		this.influencerID = influencerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getInfluencerName() {
		return influencerName;
	}

	public void setInfluencerName(String influencerName) {
		this.influencerName = influencerName;
	}

	public String getInfluencerType() {
		return influencerType;
	}

	public void setInfluencerType(String influencerType) {
		this.influencerType = influencerType;
	}

	public Long getMinRate() {
		return minRate;
	}

	public void setMinRate(Long minRate) {
		this.minRate = minRate;
	}

	public String getPreviousBrands() {
		return previousBrands;
	}

	public void setPreviousBrands(String previousBrands) {
		this.previousBrands = previousBrands;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getInfluencerNiche() {
		return influencerNiche;
	}

	public void setInfluencerNiche(List<String> influencerNiche) {
		this.influencerNiche = influencerNiche;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTikTok() {
		return tikTok;
	}

	public void setTikTok(String tikTok) {
		this.tikTok = tikTok;
	}

	public String getTweeter() {
		return tweeter;
	}

	public void setTweeter(String tweeter) {
		this.tweeter = tweeter;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitch() {
		return twitch;
	}

	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	public List<String> getBestPosts() {
		return bestPosts;
	}

	public void setBestPosts(List<String> bestPosts) {
		this.bestPosts = bestPosts;
	}
}
