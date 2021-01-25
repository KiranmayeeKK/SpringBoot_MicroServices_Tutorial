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

import io.katkol.moviecatalogservice.models.CatalogItem;
import io.katkol.moviecatalogservice.models.Movie;
import io.katkol.moviecatalogservice.models.Rating;
import io.katkol.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId")String userId){
		
		//get all rated movie IDs

		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		//For each movie ID, call movie info service and get details
		
		//To call the movie Info service, an API call has to be made using REST Template
		return ratings.getUserRating().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

			return new CatalogItem(movie.getName(),"Test",rating.getRating());
		}).collect(Collectors.toList());

		
		//Put them all together
	}

}
