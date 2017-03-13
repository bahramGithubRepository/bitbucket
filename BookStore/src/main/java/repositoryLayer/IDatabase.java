package repositoryLayer;

import java.util.ArrayList;

import dataModels.Book;


public interface IDatabase {

	boolean  addToBookStore(Book data);

	int addToBasket(String ISBN, String sessionId);

	int removeFromBasket(String ISBN, String sessionId);

	ArrayList<Book> getAllData();

	ArrayList<Book> getAuthor(String searchItem);

	ArrayList<Book> getTitle(String searchItem);
	
	ArrayList<Book> getMyBasket(String id);
	

}