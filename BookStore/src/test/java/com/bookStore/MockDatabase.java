package com.bookStore;

import java.util.ArrayList;

import dataModels.Book;
import repositoryLayer.IDatabase;

public class MockDatabase implements IDatabase{
	private ArrayList<Book> database=new ArrayList<Book>();
	
	public MockDatabase() {
	
		Book b1=new Book("1111", "test", "test", 10);
		b1.setQuantity(10);
    	Book b2=new Book("2222", "test2", "test2", 20);
    	
    	database.add(b1);database.add(b2);
	}

	@Override
	public boolean addToBookStore(Book data) {
		boolean result=true;
		for (Book book : database) {
			if(book.getISBN().equals(data.getISBN()))
				result=false;
		}
		if(result)
			database.add(data);
		return result;
	}

	@Override
	public int addToBasket(String ISBN, String sessionId) {
		int result=2;
		for (Book book : database) {
			if(book.getISBN().equals(ISBN))
				if(book.getQuantity()>0)
					result=0;
				else result=1;
			
		}
		return result;
	}

	@Override
	public int removeFromBasket(String ISBN, String sessionId) {
		int result=2;
		for (Book book : database) {
			if(book.getISBN().equals(ISBN))
				result=0;
			
		}
		return result;
	}

	@Override
	public ArrayList<Book> getAllData() {
		// TODO Auto-generated method stub
		return database;
	}

	@Override
	public ArrayList<Book> getAuthor(String searchItem) {
		ArrayList<Book> result=new ArrayList<Book>();
		for (Book book : database) {
			if(book.getAuthor().equals(searchItem))
				result.add(book);
		}
		return result;
	}

	@Override
	public ArrayList<Book> getTitle(String searchItem) {
		ArrayList<Book> result=new ArrayList<Book>();
		for (Book book : database) {
			if(book.getTitle().equals(searchItem))
				result.add(book);
		}
		return result;
	}

	@Override
	public ArrayList<Book> getMyBasket(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
