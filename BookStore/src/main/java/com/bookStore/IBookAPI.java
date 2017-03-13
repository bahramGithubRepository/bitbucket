package com.bookStore;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dataModels.Book;

public interface IBookAPI {

	ArrayList<Book> getAllBooks();
	
	String getID();
	
	ArrayList<Book> getMyBasket(String id);

	ArrayList<Book> searchByItem(String searchItem);

	Book add(Book book) throws Exception;

	int buy(BookRequestWrapper wapper);

	int remove(BookRequestWrapper wapper);

}