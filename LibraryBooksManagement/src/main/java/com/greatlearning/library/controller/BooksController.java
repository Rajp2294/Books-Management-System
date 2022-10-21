package com.greatlearning.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.library.model.Book;
import com.greatlearning.library.service.BookService;

@Controller
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookService bookService;

	@RequestMapping("/list")
	public String listBooks(Model theModel) {
		List<Book> theBooks = bookService.findAll();
		theModel.addAttribute("Books", theBooks);
		return "list-books";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Book thebook = new Book();
		theModel.addAttribute("Book", thebook);
		return "book-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("bookId") int id, Model theModel) {
		Book thebook = bookService.findById(id);
		theModel.addAttribute("Book", thebook);
		return "book-form";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("author") String author, @RequestParam("category") String category) {

		System.out.println(id);
		Book thebook;
		if (id != 0) {
			thebook = bookService.findById(id);
			thebook.setName(name);
			thebook.setAuthor(author);
			thebook.setCategory(category);
		} else
			thebook = new Book(name, author, category);

		bookService.save(thebook);
		return "redirect:/books/list";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("bookId") int id) {
		bookService.deleteById(id);
		return "redirect:/books/list";
	}

	@RequestMapping("/search")
	public String search(@RequestParam("name") String name, @RequestParam("author") String author, Model theModel) {
		if (name.trim().isEmpty() && author.trim().isEmpty())
			return "redirect:/books/list";
		else {
			List<Book> thebooks = bookService.searchBy(name, author);
			theModel.addAttribute("Books", thebooks);
			return "list-books";
		}
	}
}
