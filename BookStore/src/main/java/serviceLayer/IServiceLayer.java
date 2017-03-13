package serviceLayer;

import java.util.ArrayList;

import dataModels.Book;

public interface IServiceLayer {

	boolean addNewBook(Book data);

	int buy(String ISBN, String sessionId);

	int deleteItemInBasket(String ISBN, String sessionId);

	ArrayList<Book> getAllBook();

	ArrayList<Book> searchInAuthor(String searchItem);

	ArrayList<Book> searchInTitle(String searchItem);

	ArrayList<Book> Basket(String id);

}