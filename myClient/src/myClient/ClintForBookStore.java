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

public class ClintForBookStore {
	
	static String id="";
	static String serverAddress="";
	static Scanner line=new Scanner(System.in);
	private static BookStoreServices services=new BookStoreServices();
	
	public static void main(String[] args) {
		ClintForBookStore bookastore=new ClintForBookStore();
		System.out.println("Welcome commane Line for book story");
		
			bookastore.connectToServer();
		 
//		do{
//			System.out.println("please enter Spring server IP and port. Exm: localhost:8080 or 83.10.20.111:5070 ");
//			serverAddress=line.nextLine(); 
//			id=services.getId(serverAddress,"id");
//			if(id.equals("Server Error"))
//				System.err.println("Spring server address is not reachable please check the address and try again !!!\n");
//
//		}while(id.equals("Error"));
		System.out.println("Client successfully connected to "+serverAddress);

		int number=bookastore.bookStoryMenu();

		while(number!=7){
			if(number==1){

				ArrayList<Book> result ;
				try {
					result = services.getAllBooks(serverAddress);
					if(result.size()==0){
						System.err.println("there is no book");
						
					}else{
						bookastore.printBookArray(result,false);
					}
				} catch (Exception e) {
//					System.err.println("\nServer is Down or Maybe there is connection problem try again later !!!");
					line.nextLine();
					bookastore.reConnect();
					
				}


					


			}else if(number==2){
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
//					line.nextLine();
					bookastore.reConnect();
				}

					

			}else if(number==3){
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
				
				
				
			}else if(number==4){
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
//					line.nextLine();
					bookastore.reConnect();
				}
				
				
				
				
			}else if(number==5){

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

					

			}else if(number==6){
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
				
				
				
			}else{
				line.close();
				System.exit(0);
			}
			number=bookastore.bookStoryMenu();
			
		}

	}
		
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
			  
	private void reConnect(){
		do{
//			line.nextLine();
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
	
	
	
		
	private int bookStoryMenu(){
		int number=0;

		boolean hasErrorInEnteredMenuNumber=false;
		do{
			System.out.println("\nOption menu\nPlease choose a number:\n"
					+ "1: Get the list of books\n"
					+ "2: Search a book\n"
					+ "3: Add a new book\n"
					+ "4: Add to Basket\n"
					+ "5: Get Basket\n"
					+ "6: Remove from Basket\n"
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
