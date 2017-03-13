package dataModels;

import java.math.BigDecimal;

public class Book {
	private String ISBN;
	private String title;
	private String author;
	private double price;
	int quantity;

	public Book(){
		
	}
	
	public Book(String ISBN,String title,String author,double price ){
		this.ISBN=ISBN;
		this.title=title;
		this.author=author;
		this.price=price;

		
	}
	
	
	
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", Title='" + title + '\'' +
                ", Auther='" + author + '\'' +
                 ", Price='" + price + '\'' +

                '}';
    }

	

}

