package com.greatlearning.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greatlearning.library.model.Book;

@Service
public interface BookService {

	public List<Book> findAll();

	public void save(Book thebook);

	public Book findById(int id);

	public void deleteById(int id);

	public List<Book> searchBy(String name, String author);

}
