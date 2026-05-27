package com.example.personal_movie_tracker.model;

import java.util.List;

import com.example.personal_movie_tracker.utils.IdentifierGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String favUID = IdentifierGenerator.generate("fav");
	
	@OneToMany(mappedBy = "favourite", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Movie> listOfMovies;
	
	@ManyToOne
	private User user;
}

