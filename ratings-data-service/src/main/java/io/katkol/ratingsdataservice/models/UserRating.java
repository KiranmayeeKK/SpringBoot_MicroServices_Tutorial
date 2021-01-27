package io.katkol.ratingsdataservice.models;

import java.util.List;

public class UserRating {
	
	private List <Rating> ratings;

	
	public UserRating() {
		super();
	}
	
	public UserRating(List<Rating> userRating) {
		super();
		this.ratings = userRating;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> Ratings) {
		this.ratings = Ratings;
	}
	

}
