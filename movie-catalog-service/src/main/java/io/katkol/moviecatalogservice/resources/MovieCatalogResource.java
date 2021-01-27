package io.katkol.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.katkol.moviecatalogservice.models.CatalogItem;
import io.katkol.moviecatalogservice.models.Movie;
import io.katkol.moviecatalogservice.models.Rating;
import io.katkol.moviecatalogservice.models.UserRating;
import io.katkol.moviecatalogservice.services.MovieInfo;
import io.katkol.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId")String userId){
		
		//get all rated movie IDs

		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		//For each movie ID, call movie info service and get details
		
		//To call the movie Info service, an API call has to be made using REST Template
		return ratings.getRatings().stream()
				.map(rating ->movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
	}


}
