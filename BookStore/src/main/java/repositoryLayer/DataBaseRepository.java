package repositoryLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import dataModels.Book;

/**
 * 
 * @author Mr Bahram
 * This is database class
 */

@Component
public class DataBaseRepository implements IDatabase {
	private static Statement s;
	private static Connection conn;
	private final int OK=0,	NOT_IN_STOCK=1,DOES_NOT_EXIST=2;

	/**
	 * Default Constructor creates connection
	 */
	public DataBaseRepository() {
		
		try {
			conn = DriverManager.getConnection(
			        "jdbc:ucanaccess://Database.accdb");
			
			 s = conn.createStatement();
			 ResultSet resultSelect=s.executeQuery("SELECT * FROM book ");
				if(!resultSelect.next()){
					initDatabase();
				}
			 
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	/**
	 * adds new book into book table
	 */

	
	@Override
	public  boolean addToBookStore(Book data){
		
		boolean result=false;
		try {
					
				ResultSet resultSelect=s.executeQuery("SELECT * FROM book where ISBN='"+data.getISBN()+"'");
				if(!resultSelect.next()){
					String query="INSERT INTO book (ISBN,title,author,price,quantity) VALUES (?,?,?,?,?)";
					PreparedStatement myPsmt = conn.prepareStatement(query);

					myPsmt.setString(1, data.getISBN());
					myPsmt.setString(2,data.getTitle());
					myPsmt.setString(3, data.getAuthor());
					myPsmt.setDouble(4, data.getPrice());
					myPsmt.setInt(5,data.getQuantity());

					result=!myPsmt.execute();
				 
				}
			
		} catch (SQLException e) {
			
			
			result=false;
		}
		
		return result;
	}
	

	/***
	 * adds book into basket table. if the book already exist just add more in quantity
	 */
	@Override
	public int addToBasket(String ISBN, String sessionId){
			
			int result=OK;
			try {
						
				ResultSet resultSelectBook=s.executeQuery("SELECT * FROM book where ISBN='"+ISBN+"'");
				if(resultSelectBook.next()){//if the book has been founded 
					if(resultSelectBook.getInt("quantity")>0){// if there is book in stock
						ResultSet resultSelectBasket=s.executeQuery("SELECT * FROM basket where ISBN='"+ISBN+"' AND sessionId='"+sessionId+"'");
						if(resultSelectBasket.next()){//if the user already added the book then just the quantity will be updated
							//be megdare quantity ye done ezafe mishe 
							String query="UPDATE basket SET quantity= ? WHERE ISBN='"+ISBN+"' AND sessionId='"+sessionId+"'";
							PreparedStatement myPsmt = conn.prepareStatement(query);
							myPsmt.setInt(1, resultSelectBasket.getInt("quantity")+1);
							myPsmt.execute();
							 result=OK;
						}else{// this is new item then will be added in to basket
							//be table basket in item ezafe mishe
							String query="INSERT INTO basket (sessionId,ISBN,quantity) VALUES (?,?,?)";
							PreparedStatement myPsmt = conn.prepareStatement(query);
							myPsmt.setString(1,sessionId);
							myPsmt.setString(2,ISBN);
							myPsmt.setInt(3, 1);
							myPsmt.execute();
							result=OK;
						}
						
						String query="UPDATE book SET quantity= ? WHERE ISBN='"+ISBN+"'";
						PreparedStatement myPsmt = conn.prepareStatement(query);
						myPsmt.setInt(1, resultSelectBook.getInt("quantity")-1);
						myPsmt.execute();
						 result=OK;
						
					}else{//there is no book in stock
						result = NOT_IN_STOCK;
					}
					
				}else // the book has not been founded 
					result =DOES_NOT_EXIST;
				
			} catch (SQLException e) {
				
				
				result=DOES_NOT_EXIST;
			}
			
			return result;
		}


	/**
	 * removes book from the user basket 
	 */
	@Override
	public int removeFromBasket(String ISBN, String sessionId){
		
		int result=OK;
		try {
					
				ResultSet resultSelectBook=s.executeQuery("SELECT * FROM book where ISBN='"+ISBN+"'");
				if(resultSelectBook.next()){//if the book has been founded 
						ResultSet resultSelectBasket=s.executeQuery("SELECT * FROM basket where ISBN='"+ISBN+"' AND sessionId='"+sessionId+"'");
						if(resultSelectBasket.next()){//there is the book in the basket
								String query="UPDATE basket SET quantity= ? WHERE ISBN='"+ISBN+"' AND sessionId='"+sessionId+"'";
								PreparedStatement myPsmt = conn.prepareStatement(query);
								int finalValueInBasket=resultSelectBasket.getInt("quantity")-1;
								myPsmt.setInt(1, finalValueInBasket);
								myPsmt.execute();
								 result=OK;
								 
								  query="UPDATE book SET quantity= ? WHERE ISBN='"+ISBN+"'";
									 myPsmt = conn.prepareStatement(query);
									myPsmt.setInt(1, resultSelectBook.getInt("quantity")+1);
									myPsmt.execute();
									 result=OK;
									 
								if(finalValueInBasket==0){
									 query="DELETE FROM basket WHERE ISBN='"+ISBN+"' AND sessionId='"+sessionId+"'";
									 myPsmt = conn.prepareStatement(query);
									 myPsmt.execute();
								}								 

						}else{// this is no item in the basket

							
							result=DOES_NOT_EXIST;
						}

				}else // the book has not been founded 
					result =DOES_NOT_EXIST;
					
			
		} catch (SQLException e) {
			
			
			result=DOES_NOT_EXIST;
		}
		
		return result;
	}

	
	/**
	 * returns list of all books
	 */
	@Override
	public ArrayList<Book> getAllData(){
		ArrayList<Book> result=new ArrayList<Book>();
		try {
			ResultSet resultSelect=s.executeQuery("SELECT * FROM book");
			while(resultSelect.next()){
				Book obj=new Book();
				obj.setISBN(resultSelect.getString("ISBN"));
				obj.setTitle(resultSelect.getString("title"));
				obj.setAuthor(resultSelect.getString("author"));
				obj.setPrice(resultSelect.getDouble("price"));
				result.add(obj);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * search item in Author column
	 */
	@Override
	public ArrayList<Book> getAuthor(String searchItem){
		
		ArrayList<Book> result=new ArrayList<Book>();
		try {
			ResultSet resultSelect=s.executeQuery("SELECT * FROM book where author='"+searchItem.trim()+"'");
				while(resultSelect.next()){
					Book obj=new Book();
					obj.setISBN(resultSelect.getString("ISBN"));
					obj.setTitle(resultSelect.getString("title"));
					obj.setAuthor(resultSelect.getString("author"));
					obj.setPrice(resultSelect.getDouble("price"));
					result.add(obj);
				}

			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * search item in title column
	 */
	@Override
	public ArrayList<Book> getTitle(String searchItem){
			
			ArrayList<Book> result=new ArrayList<Book>();
			try {
	
				ResultSet resultSelect=s.executeQuery("SELECT * FROM book where title='"+searchItem.trim()+"'");				
						
						while(resultSelect.next()){
							Book obj=new Book();
							obj.setISBN(resultSelect.getString("ISBN"));
							obj.setTitle(resultSelect.getString("title"));
							obj.setAuthor(resultSelect.getString("author"));
							obj.setPrice(resultSelect.getDouble("price"));
							result.add(obj);
						}
	
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			return result;
		}
	
	/**
	 * returns the user basket
	 */
	@Override
	public ArrayList<Book> getMyBasket(String id){
		ArrayList<Book> result=new ArrayList<Book>();
		try {

			ResultSet resultSelect=s.executeQuery("SELECT * FROM book,basket where book.ISBN=basket.ISBN and basket.sessionId='"+id+"'");				
					
					while(resultSelect.next()){
						Book obj=new Book();
						obj.setISBN(resultSelect.getString("ISBN"));
						obj.setTitle(resultSelect.getString("title"));
						obj.setAuthor(resultSelect.getString("author"));
						obj.setPrice(resultSelect.getDouble("price"));
						obj.setQuantity(resultSelect.getInt("basket.quantity"));
						result.add(obj);
					}

			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * initial database with default values
	 */
	private static void initDatabase(){
		ArrayList<Book> list=new ArrayList<Book>();
		Book b1=new Book("1-1-1-1", "Mastering åäö", "Average Swede", 762.00);
		b1.setQuantity(15);
		list.add(b1);
		Book b2=new Book("2-2-2-2", "How To Spend Money", "Rich Bloke", 1000000.00);
		b2.setQuantity(1);
		list.add(b2);
		
		Book b3=new Book("3-3-3-3", "Generic Title", "First Authore", 185.50);
		b3.setQuantity(5);
		list.add(b3);
		
		Book b4=new Book("4-4-4-4", "Generic Title", "Second Authore", 1748.00);
		b4.setQuantity(3);
		list.add(b4);
		
		Book b5=new Book("5-5-5-5", "Random Sales", "Cunning Bastard", 999.00);
		b5.setQuantity(20);
		list.add(b5);
		
		Book b6=new Book("6-6-6-6", "Random Sales", "Cunning Bastard", 499.500);
		b6.setQuantity(3);
		list.add(b6);
		
		Book b7=new Book("7-7-7-7", "How To Spend Money", "Rich Bloke", 564.50);
		b7.setQuantity(0);
		list.add(b7);
		for (Book book : list) {
			
		
			try {
				
				ResultSet resultSelect=s.executeQuery("SELECT * FROM book where ISBN='"+book.getISBN()+"'");
				if(!resultSelect.next()){
					String query="INSERT INTO book (ISBN,title,author,price,quantity) VALUES (?,?,?,?,?)";
					PreparedStatement myPsmt = conn.prepareStatement(query);
	
					myPsmt.setString(1, book.getISBN());
					myPsmt.setString(2,book.getTitle());
					myPsmt.setString(3, book.getAuthor());
					myPsmt.setDouble(4, book.getPrice());
					myPsmt.setInt(5,book.getQuantity());
	
					myPsmt.execute();
				 
				}
					
				} catch (SQLException e) {
					
					
					
				}
			
		}
		
		
	}
}
