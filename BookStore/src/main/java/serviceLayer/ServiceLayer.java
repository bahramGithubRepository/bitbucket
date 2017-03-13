package serviceLayer;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import dataModels.Book;

import repositoryLayer.IDatabase;

/**
 * 
 * @author Mr Bahram
 *Service layer
 */
@Component
public class ServiceLayer implements IServiceLayer {
	IDatabase repository;
	
	public ServiceLayer(IDatabase repository) {
		this.repository=repository;
	}
	

	/**
	 * sends add a new book in repository layer and returns result to BookAPI class
	 */
	@Override
	public boolean addNewBook(Book data){
		return repository.addToBookStore(data);
	}

	/**
	 * sends add a new book in repository layer
	 */
	@Override
	public int buy(String ISBN, String sessionId){
		return repository.addToBasket(ISBN, sessionId);
	}


	/**
	 * sends delete a item from user basket to repository
	 */
	@Override
	public int deleteItemInBasket(String ISBN, String sessionId){
		return repository.removeFromBasket(ISBN, sessionId);
	}


	/**
	 * returns list of books
	 */
	@Override
	public ArrayList<Book> getAllBook(){
		return repository.getAllData();
	}


	/**
	 * sends search item based on Author name to database and returns result to BookAPI class
	 */
	@Override
	public ArrayList<Book> searchInAuthor(String searchItem){
		return repository.getAuthor(searchItem);
	}
	

	/**
	 * sends search item based on Title name to database and returns result to BookAPI class
	 */
	@Override
	public ArrayList<Book> searchInTitle(String searchItem){
		return repository.getTitle(searchItem);
	}
	

	/**
	 * returns the use basket
	 */
	@Override
	public ArrayList<Book> Basket(String id){
		return repository.getMyBasket(id);
	}

}
