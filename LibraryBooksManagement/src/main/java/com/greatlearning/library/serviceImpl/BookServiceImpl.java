package com.greatlearning.library.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.greatlearning.library.model.Book;
import com.greatlearning.library.service.BookService;

@Service
@Repository
public class BookServiceImpl implements BookService{
	
	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	public BookServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		
		try {
			session = sessionFactory.getCurrentSession();
		}
		catch (HibernateException e)
		{
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	@Override
	public List<Book> findAll() {
		Transaction tx = session.beginTransaction();
		
		List<Book> books = session.createQuery("from Book").list();
		
		tx.commit();
		return books;
	}

	@Transactional
	public void save(Book thebook) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(thebook);
		tx.commit();		
	}

	@Transactional
	public Book findById(int id) {
		Book book = new Book();
		Transaction tx = session.beginTransaction();
		book = session.get(Book.class, id);
		tx.commit();
		return book;
	}

	@Transactional
	public void deleteById(int id) {
		Transaction tx = session.beginTransaction();
		Book book = session.get(Book.class, id);
		session.delete(book);
		tx.commit();		
	}

	@Transactional
	public List<Book> searchBy(String name, String author) {
		Transaction tx = session.beginTransaction();
		String query = "";
		if(name.length() != 0 && author.length() != 0)
			query ="from Book where name like '%"+name+"%' or author like '%"+author+"%'";
		else if(name.length()!=0)
			query ="from Book where name like '%"+name+"%'";
		else if(author.length()!=0)
			query ="from Book where author like '%"+author+"%'";
		else
			System.out.println("Cannot search without input data");
		List<Book> book = session.createQuery(query).list();
			tx.commit();
			return book;
	}

}
