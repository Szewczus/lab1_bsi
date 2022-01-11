package com.example.lab1_bsi.controllers;

import com.example.lab1_bsi.dto.UserDto;
import com.example.lab1_bsi.entities.User;
import com.example.lab1_bsi.serwisy.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    @Test
    public void testPostLogin() throws Exception{
        MockHttpSession session = new MockHttpSession();

        User user = new User();
        user.setLogin("Ewus");
        user.setPasswordHash("ewus123");
        UserService userService = new UserService();

        session.setAttribute("user", user);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("http://localhost:8080/")
                .session(session);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

    }
}