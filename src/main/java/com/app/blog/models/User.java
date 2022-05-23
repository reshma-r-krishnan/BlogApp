package com.app.blog.models;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> posts;
}
