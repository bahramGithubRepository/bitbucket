package com.bookStore;


import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dataModels.Book;
import serviceLayer.IServiceLayer;
import serviceLayer.ServiceLayer;

@CrossOrigin
@RestController
@RequestMapping(value="/rest/book")
public class BookAPI implements IBookAPI {
	private IServiceLayer services; 
	
	
	public BookAPI(IServiceLayer services) {
	
		this.services=services;
	}

	/**
	 * returns all books
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value="/",method = RequestMethod.GET)
	   public ArrayList<Book> getAllBooks(){
	      return services.getAllBook();
	   }
	
	@CrossOrigin
	@Override
	@RequestMapping(value="/basket/{id}",method = RequestMethod.GET)
	   public ArrayList<Book> getMyBasket(@PathVariable String id){
		
//		String sessionId=RequestContextHolder.currentRequestAttributes().getSessionId();
		System.out.println("basket "+id);
	      return services.Basket(id);
	   }
	@CrossOrigin
	@Override
	@RequestMapping(value="/id",method = RequestMethod.GET)
	public String getID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * returns item match in Author or Title
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value="/{searchItem}",method = RequestMethod.GET)
	   public ArrayList<Book> searchByItem(@PathVariable String searchItem){

		ArrayList<Book> result= services.searchInAuthor(searchItem);
		if(result.size()==0)
			result= services.searchInTitle(searchItem);

		return result;
	   }
	
	/**
	 * add a new book in database. the BookRequestWapper separate book object from quantity
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value="/add",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	   public Book add(@RequestBody Book book)throws Exception {
	    
	     boolean result= services.addNewBook(book);
	     if(!result){
	    	  throw new Exception("Book "+book.getISBN()+" already exists");
	     } 
	    	 
	     return book;
	   }

	/**
	 * add a book in the user basket
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value="/buy",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	   public int buy(@RequestBody BookRequestWrapper wapper) {
//		String sessionId=RequestContextHolder.currentRequestAttributes().getSessionId();
		System.out.println("Buy    "+wapper.getId());
	     int result= services.buy(wapper.getIsbn(),wapper.getId());

	     return result;
	   }

	/**
	 * remove book from the user basket
	 */
	@CrossOrigin
	@Override
	@RequestMapping(value="/remove",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	   public int remove(@RequestBody BookRequestWrapper wapper) {
//		String sessionId=RequestContextHolder.currentRequestAttributes().getSessionId();
		System.out.println("Remove "+wapper.getId());
	     int result= services.deleteItemInBasket(wapper.getIsbn(),wapper.getId());

	    	 
	     return result;
	   }
}
