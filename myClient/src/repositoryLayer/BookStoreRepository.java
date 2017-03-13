package repositoryLayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import dataModels.Book;
/**
 * 
 * @author Mr Bahram
 * 
 * Provides Get and Post REST API services 
 *
 */

public class BookStoreRepository {
	static HttpURLConnection conn;
	static DataOutputStream wr;

	/**
	 * sends GET request to web API server and returns response
	 * @param Ip
	 * @param searchItem
	 * @return
	 */
	public  String get(String Ip,String searchItem){
		String output="";
		String serverAddress="http://"+Ip+"/rest/book/";
		if(!searchItem.isEmpty())
			serverAddress+=searchItem;
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(serverAddress.replace(" ", "%20"));

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			output = response.getEntity(String.class);

		} catch (Exception e) {

			output="Server Error";

		}
		return output;
	}

	/**
	 * sends POST request to web API server and returns response
	 * @param Ip
	 * @param id
	 * @param controller
	 * @param book
	 * @return
	 * @throws IOException
	 */
	public  String post(String Ip,String id,String controller,Book book) throws IOException{
		String result="";
		String urlParameters;
		if(controller.equals("add")){
			urlParameters="{\"title\":\""+book.getTitle()+"\",\"author\":\""+book.getAuthor()+
					"\",\"price\":"+book.getPrice()+",\"isbn\":\""+book.getISBN()+"\",\"quantity\":"+book.getQuantity()+"}";
		}else
			urlParameters="{\"isbn\":\""+book.getISBN()+"\",\"id\":\""+id+"\"}";

		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://"+Ip+"/rest/book/"+controller;
		URL    url            = new URL( request );
		conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty( "Content-Type", "application/json"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setUseCaches( false );
		wr = new DataOutputStream( conn.getOutputStream()) ;
		wr.write( postData );

		int responseCode = conn.getResponseCode();

		if(responseCode==200){
			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();


			result=response.toString();
		}else result="The book "+book.getISBN()+" already exists";

		return result;
	}


}
