package myClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sound.sampled.LineEvent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dataModels.Book;
import serviceLayer.BookStoreServices;

/**
 * 
 * @author Mr Bahram
 *
 */
public class ClintForBookStore {

	private final static int GET_LIST_OF_THE_BOOK=1;
	private final static int SEARCH_A_BOOK=2;
	private final static int ADD_A_NEW_BOOK=3;
	private final static int ADD_TO_MY_BASKET=4;
	private final static int GET_MY_BASKET=5;
	private final static int REMOVE_FROM_MY_BASKET=6;
	private final static int EXIT=7;
	static String id="";
	static String serverAddress="";
	static Scanner line=new Scanner(System.in);
	private static BookStoreServices services=new BookStoreServices();

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		ClintForBookStore bookastore=new ClintForBookStore();
		System.out.println("Welcome commane Line for book story");

		bookastore.connectToServer();

		System.out.println("Client successfully connected to "+serverAddress);

		int number=bookastore.bookStoryMenu();

		while(number!=EXIT){// 7: Exit
			if(number==GET_LIST_OF_THE_BOOK){// 1 :Get list of THE books

				ArrayList<Book> result ;
				try {
					result = services.getAllBooks(serverAddress);
					if(result.size()==0){
						System.err.println("there is no book");

					}else{
						bookastore.printBookArray(result,false);
					}
				} catch (Exception e) {

					line.nextLine();
					bookastore.reConnect();

				}





			}else if(number==SEARCH_A_BOOK){// 2: Search a book
				System.err.println("Enter Search Item in Title or Author");
				line.nextLine();
				String searchItem=line.nextLine();
				ArrayList<Book> result;
				try {
					result = services.search(serverAddress,searchItem);
					if(result.size()==0){
						System.err.println("there is no result for "+searchItem);

					}else
						bookastore.printBookArray(result,false);
				} catch (Exception e) {

					bookastore.reConnect();
				}



			}else if(number==ADD_A_NEW_BOOK){// 3: Add a new book
				Book book=new Book();
				System.out.println("Please Enter ISBN of your book");
				line.nextLine();
				book.setISBN(line.nextLine());

				System.out.println("Please Enter Title of your book");

				book.setTitle(line.nextLine());

				System.out.println("Please Enter Author of your book");

				book.setAuthor(line.nextLine());

				System.out.println("Please Enter Price of your book");

				double price=bookastore.getValidDoubleNumber();

				book.setPrice(price);
				System.out.println("Please Enter quantity of your book");

				int quantity=bookastore.getValidIntNumber();

				book.setQuantity(quantity);



				Book result;
				try {
					result = services.addNewItem(serverAddress,book);
					if(result!=null)
						System.out.println("Added "+ result+" , Quantity='"+book.getQuantity()+"'");

				} catch (IOException e) {
					line.nextLine();
					bookastore.reConnect();
				}



			}else if(number==ADD_TO_MY_BASKET){// 4: Add to Basket
				Book book=new Book();
				System.err.println("Please Enter ISBN of your book to add in your basket");
				line.nextLine();
				book.setISBN(line.nextLine());



				String result;
				try {
					result = services.addToBasket(serverAddress,id,book);
					if(result!=null)
						if(result.contentEquals("0"))
							System.out.println("added the book in your basket");
						else if(result.contentEquals("1"))
							System.err.println("the book is not in stock sorry!!! ");
						else
							System.err.println("the book does not exist");
				} catch (IOException e) {

					bookastore.reConnect();
				}




			}else if(number==GET_MY_BASKET){// 5: Get Basket

				ArrayList<Book> result;
				try {
					result = services.getMyBasket(serverAddress, "basket/"+id);
					if(result.size()==0){
						System.err.println("there is no item in your basket");

					}else
						bookastore.printBookArray(result,true);
				} catch (Exception e) {
					line.nextLine();
					bookastore.reConnect();
				}



			}else if(number==REMOVE_FROM_MY_BASKET){// 6: Remove from Basket
				Book book=new Book();
				System.err.println("Please Enter ISBN of your book to remove from your basket");
				line.nextLine();
				book.setISBN(line.nextLine());


				String result;
				try {
					result = services.deleteFromBasket(serverAddress,id,book);
					if(result!=null)
						if(result.contentEquals("0"))
							System.out.println("removed the book in your basket");
						else if(result.contentEquals("1"))
							System.err.println("the book is not in stock sorry!!! ");
						else
							System.err.println("the book does not exist");
				} catch (IOException e) {
					bookastore.reConnect();
				}



			}else{// Exit
				line.close();
				System.exit(0);
			}
			//			Show menu again
			number=bookastore.bookStoryMenu();

		}

	}

	/**
	 * Make connection to server
	 */
	private void connectToServer(){
		do{

			System.out.println("please enter Spring server IP and port. Exm: localhost:8080 or 83.10.20.111:5070 ");

			String lineValue=line.nextLine();
			System.out.println("Please wait...");
			if(lineValue.equals("Exit")){
				System.out.println("See you later!!!\ngoodbye");
				System.exit(0);
			}else serverAddress=lineValue;


			id=services.getId(serverAddress,"id");
			if(id.equals("Server Error")){
				String errorMessage="Spring server address is not reachable please check the address and try again by\n"
						+ "- Type a new Server Addres and Port or\n- type 'Exit' for quit";
				System.err.println(errorMessage+"\n");
			}


		}while(id.equals("Server Error"));
	}

	/**
	 * calls when there is a fail in the connection   
	 */
	private void reConnect(){
		do{

			String errorMessage="\nServer is Down or Maybe there is connection problem try again by\n"
					+ "- Press Enter for re-connect\n- Type a new Server Addres and Port or\n- type 'Exit' for quit";

			System.err.println(errorMessage+"\n");

			String lineValue=line.nextLine();
			System.out.println("Please wait...");
			if(lineValue.equals("Exit")){
				System.out.println("See you later!!!\ngoodbye");
				System.exit(0);
			}else if(lineValue.isEmpty())
				System.out.println("Re-conecting please wait...");
			else {
				serverAddress=lineValue;
				System.out.println("Trying to connect "+serverAddress+" please wait...");
			}

			id=services.getId(serverAddress,"id");


		}while(id.equals("Server Error"));
	}



	/**
	 * shows option menu
	 * @return
	 */
	private int bookStoryMenu(){
		int number=0;

		boolean hasErrorInEnteredMenuNumber=false;
		do{
			System.out.println("\nOption menu\nPlease choose a number:\n"
					+ "1: Get list of THE books\n"
					+ "2: Search a book\n"
					+ "3: Add a new book\n"
					+ "4: Add to my Basket\n"
					+ "5: Get my Basket\n"
					+ "6: Remove from my Basket\n"
					+ "7: Exit");
			try{
				number=line.nextInt();
				hasErrorInEnteredMenuNumber=false;
				if(number<=0||number>7){
					System.err.println("Error please enter a number between 1...7");
					hasErrorInEnteredMenuNumber=true;
				}
			}catch(InputMismatchException error){
				hasErrorInEnteredMenuNumber=true;
				System.err.println("Error please enter a number!!!");
				line.nextLine();
			}
		}while(hasErrorInEnteredMenuNumber);

		return number;
	}

	/**
	 * force the user to enter a valid double number
	 * @return
	 */
	private double getValidDoubleNumber(){
		double price=0;
		boolean isError=false;
		do{
			try{
				price=line.nextDouble();
				isError=false;
			}catch(InputMismatchException error){
				System.err.println("Error please enter a number!!!");
				isError=true;
				line.nextLine();
			}
		}while(isError);
		return price;
	}

	/**
	 * force the user to enter a valid integer number
	 * @return
	 */
	private int getValidIntNumber(){
		int quantity=0;
		boolean isError=false;
		do{
			try{
				quantity=line.nextInt();
				isError=false;
			}catch(InputMismatchException error){
				System.err.println("Error please enter a number!!!");
				isError=true;
				line.nextLine();
			}
		}while(isError);
		return quantity;
	}

	/**
	 * Prints list of books on the screen
	 * @param books
	 * @param isQuantity
	 */
	private void printBookArray(ArrayList<Book> books,boolean isQuantity){

		int counter=1;

		for (Book book : books) {

			if(isQuantity)
				System.out.println(counter+"	"+ book+" , Quantity='"+book.getQuantity()+"'");
			else 
				System.out.println(counter+"	"+ book);
			counter++;


		}

	}
}
