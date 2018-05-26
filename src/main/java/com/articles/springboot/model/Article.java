package com.articles.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ARTICLES")
public class Article implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "HEADER", nullable = false)
	private String header;

	@NotNull
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@NotNull
	@Column(name = "TEXT", nullable = false)
	private String text;

	@NotNull
	@Column(name = "PUBLISHDATE", nullable = false)
	private Date publishDate;

	@NotEmpty
	@Column(name = "AUTHORS", nullable = false)
	private ArrayList<String> authors = new ArrayList<String>();

	@NotEmpty
	@Column(name = "KEYWORDS", nullable = false)
	private ArrayList<String> keywords;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getHeader() { return header; }
	public void setHeader(String header) { this.header = header; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getText() { return text; }
	public void setText(String text) { this.text = text; }
	public Date getPublishDate() { return publishDate; }
	public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }

	public ArrayList<String> getAuthors() { return authors; }
	public void setAuthors(ArrayList<String> authors) { this.authors = authors; }
	public ArrayList<String> getKeywords() { return keywords; }
	public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }
}