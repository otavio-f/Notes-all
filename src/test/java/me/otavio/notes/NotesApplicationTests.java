package me.otavio.notes;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;



@SpringBootTest
@AutoConfigureMockMvc
class NotesApplicationTests {

	private final String apiEndpoint = "/api/v1";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() throws Exception {
		assertNotNull(mockMvc);
	}
	
	@Test
	void getRootEmptyList() throws Exception {
		this.mockMvc.perform(get("/api/v1/"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("[]")));
	}
	
	@Test
	void postOneResultsInCreated() throws Exception {
		String entity = "{\"title\":\"TestingUAAAAAAA\",\"content\":\"uoah mama\",\"createdAt\": \"2022-05-02T18:48:02.231250700Z\",\"lastModified\": \"2022-05-02T18:48:02.231250700Z\"}";
		this.mockMvc.perform(post("/api/v1/").contentType(MediaType.APPLICATION_JSON).content(entity))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/1"));
	}
	
	@Test
	void postWrongDataResultsInBadRequest() throws Exception {
		String entity = "{\"this field\":\"is not sufficient\"}";
		this.mockMvc.perform(post("/api/v1/").contentType(MediaType.APPLICATION_JSON).content(entity))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	void putOneResultsInNoContent() throws Exception {
		//Problema eh que nao tem como manter o mesmo mockmvc do setup pro teste
		String entity = "{\"title\":\"TestingUAAAAAAA\",\"content\":\"uoah mama\",\"createdAt\": \"2022-05-02T18:48:02.231250700Z\",\"lastModified\": \"2022-05-02T18:48:02.231250700Z\"}";
		MvcResult result = this.mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		entity = "{\"title\":\"Testing\",\"content\":\"very tasty\",\"createdAt\": \"2022-05-02T18:48:02.231250700Z\",\"lastModified\": \"2022-05-02T18:48:02.231250700Z\"}";
		this.mockMvc.perform(put("/api/v1/1").contentType(MediaType.APPLICATION_JSON).content(entity))
			.andExpect(status().isNoContent());
	}
	
	@Test
	void putWrongDataResultsInBadRequest() throws Exception {
		//Problema eh que nao tem como manter o mesmo mockmvc do setup pro teste
		String entity = "{\"title\":\"TestingUAAAAAAA\",\"content\":\"uoah mama\",\"createdAt\": \"2022-05-02T18:48:02.231250700Z\",\"lastModified\": \"2022-05-02T18:48:02.231250700Z\"}";
		this.mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON));
		
		entity = "{\"there is\":\"no entity\"}";
		this.mockMvc.perform(put("/api/v1/1").contentType(MediaType.APPLICATION_JSON).content(entity))
			.andExpect(status().isBadRequest());
	}
}
