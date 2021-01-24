package io.katkol.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.katkol.moviecatalogservice.models.CatalogItem;
import io.katkol.moviecatalogservice.models.Movie;
import io.katkol.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId")String UserId){
		
		//Creating an object like this creates a new object every time the page is refreshed.
		//To avoid this beans can be used 
		//RestTemplate restTemplate = new RestTemplate();
		
		
		//get all rated movie IDs
		// At this stage of application development, the list of ratings is hardcoded
		
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3));
		
		//For each movie ID, call movie info service and get details
		
		//To call the movie Info service, an API call has to be made using REST Template
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(),"Test",rating.getRating());
		}).collect(Collectors.toList());

		
		//Put them all together
	}

}
