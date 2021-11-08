package com.example.lab1_bsi.controllers;

import com.example.lab1_bsi.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser()
class CreateUserCotrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveUser() throws Exception {
        mockMvc.perform(post("/create/user")
                .contentType("application/json")
                .content("{\n" +
                        "    \"isHMAC\": true,\n" +
                        "    \"login\": \"Ewus6\",\n" +
                        "    \"passwordHash\": \"ewus123\",\n" +
                        "    \"salt\": \"3ed31\",\n" +
                        "    \"keyToHMAC\": \"1234\"\n" +
                        "}")
        ).andExpect(status().isOk());
    }

    @Test
    void redirectWithUsingRedirectView() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}