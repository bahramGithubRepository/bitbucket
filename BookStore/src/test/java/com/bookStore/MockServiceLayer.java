package com.bookStore;

import java.util.ArrayList;

import dataModels.Book;
import serviceLayer.IServiceLayer;

public class MockServiceLayer implements IServiceLayer{
	
private ArrayList<Book> database=new ArrayList<Book>();
private MockDatabase repository=new MockDatabase();
	
	

	@Override
	public boolean addNewBook(Book data) {
		
		return repository.addToBookStore(data);

	}

	@Override
	public int buy(String ISBN, String sessionId) {
		
		return repository.addToBasket(ISBN, sessionId);
	}

	@Override
	public int deleteItemInBasket(String ISBN, String sessionId) {
		
		return repository.removeFromBasket(ISBN, sessionId);
	}

	@Override
	public ArrayList<Book> getAllBook() {
		
		return repository.getAllData();
	}

	@Override
	public ArrayList<Book> searchInAuthor(String searchItem) {

		return repository.getAuthor(searchItem);
	}

	@Override
	public ArrayList<Book> searchInTitle(String searchItem) {
		
		return repository.getTitle(searchItem);
	}

	@Override
	public ArrayList<Book> Basket(String id) {
		
		return repository.getMyBasket(id);
	}

}
