package serviceLayer;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dataModels.Book;
import repositoryLayer.BookStoreRepository;

/**
 * 
 * @author Mr Bahram
 * This class gives services into GUI by getting data from Repository Layer
 */
public class BookStoreServices {

	private BookStoreRepository repository=new BookStoreRepository();

	/**
	 * Provides unique ID for user 
	 * @param serverAddress
	 * @param Item
	 * @return ID
	 */
	public String getId(String serverAddress,String Item){
		return repository.get(serverAddress, Item);
	}

	/**
	 * Returns All books
	 * @param serverAddress
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Book> getAllBooks(String serverAddress ) throws Exception{
		String result=repository.get(serverAddress, "");
		if(result.equals("Server Error"))
			throw new Exception("Server is Down or Maybe there is connection problem try again later !!!");
		return convertStringToBojectArray(result);

	}

	/**
	 * Searches item in title and author of the all books 
	 * @param serverAddress
	 * @param searchItem
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Book> search(String serverAddress,String searchItem) throws Exception{
		String result=repository.get(serverAddress, searchItem);
		if(result.equals("Server Error"))
			throw new Exception("Server is Down or Maybe there is connection problem try again later !!!");

		return convertStringToBojectArray(result);

	}

	/**
	 * adds new item 
	 * @param serverAddress
	 * @param book
	 * @return
	 * @throws IOException 
	 * 
	 */
	public Book addNewItem(String serverAddress,Book book) throws IOException {
		Book  resultBook = null;
		try {
			String result = repository.post(serverAddress, "", "add", book);


			JSONParser parser = new JSONParser();
			Object obj;


			obj = parser.parse(result);
			JSONObject obj2=(JSONObject) obj;
			long q=(long) obj2.get("quantity");
			resultBook=new Book((String)obj2.get("isbn"), (String)obj2.get("title"), (String)obj2.get("author"),(double) obj2.get("price"));
			book.setQuantity((int) q);
		} catch (ParseException e) {
			System.err.println("The Book already exist");
		} 

		return resultBook;

	}

	/**
	 * adds a book in the user's basket
	 * @param serverAddress
	 * @param id
	 * @param book
	 * @return
	 * @throws IOException 
	 * 
	 */
	public String addToBasket(String serverAddress,String id,Book book) throws IOException  {

		return repository.post(serverAddress, id, "buy", book);
	}


	/**
	 * returns the user's basket
	 * @param serverAddress
	 * @param Item
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Book> getMyBasket(String serverAddress,String Item) throws Exception{
		String result=repository.get(serverAddress, Item);
		if(result.equals("Server Error"))
			throw new Exception("Server is Down or Maybe there is connection problem try again later !!!");

		return convertStringToBojectArray(result);


	}

	/**
	 * deletes a book from the user's basket
	 * @param serverAddress
	 * @param id
	 * @param book
	 * @return
	 * @throws IOException 
	 * 
	 */
	public String deleteFromBasket(String serverAddress,String id,Book book) throws IOException {

		return repository.post(serverAddress, id, "remove", book);

	}

	/**
	 * converts String To ArrayList<Book>
	 * @param result
	 * @return
	 */
	private static ArrayList<Book> convertStringToBojectArray(String result){

		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(result);
		} catch (ParseException e) {

			System.err.println("Error in Json Array");
		}
		ArrayList<Book> list=new ArrayList<Book>();
		for (Object object : array) {
			JSONObject obj2=(JSONObject) object;
			Book book=new Book((String)obj2.get("isbn"), (String)obj2.get("title"), (String)obj2.get("author"),(double) obj2.get("price"));
			long q=(long) obj2.get("quantity");
			book.setQuantity((int) q);
			list.add(book);

		}
		return list;
	}

}
