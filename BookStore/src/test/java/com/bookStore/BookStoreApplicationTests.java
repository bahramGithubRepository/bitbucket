package com.bookStore;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;


import dataModels.Book;

import serviceLayer.IServiceLayer;

import static org.assertj.core.api.Assertions.*;

/**
 * 
 * @author Mr Bahram
 *This is just some sample test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookStoreApplicationTests {

	
	
	private IServiceLayer service;
	private BookAPI  bookApi;
	@Autowired
    private WebApplicationContext webApplicationContext;
	

	private MockMvc mockMvc;
    @Before
    public void setup() {
    	service=new MockServiceLayer();
    	bookApi=new BookAPI(service);
    	
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    	

    }
    
    @Test
    public void get_All_should_return_an_array() throws Exception {
    	
//    	assertThat(bookApi.getAllBooks().size()>0);
    	
			mockMvc.perform(get("/rest/book/"))
			.andExpect(status().isOk())
			.andExpect(
			        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$").isArray());
		
    }
    @Test
    public void search_item_get_result() {
    	
    	assertNotNull(bookApi.searchByItem("item"));
    	assertThat(bookApi.searchByItem("item"));
    	
//    	try {
//			mockMvc.perform(get("/rest/book/bahram"))
//			.andExpect(status().isOk())
//			.andExpect(
//			        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//			.andExpect(jsonPath("$").isArray());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    @Test
    public void add_new_itam() throws Exception{
    	
    	Book expect=new Book("10-00", "expect", "expect", 0);
    	expect.setQuantity(1);
    	assertEquals(expect, bookApi.add(expect));
    	
    }
    
    
    @Test
    public void get_id_return_String() throws Exception{
    	assertNotNull(bookApi.getID());

    }


}
