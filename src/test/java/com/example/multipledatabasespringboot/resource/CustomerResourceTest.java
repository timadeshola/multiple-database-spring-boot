package com.example.multipledatabasespringboot.resource;

import com.example.multipledatabasespringboot.core.AppUtils;
import com.example.multipledatabasespringboot.model.request.CustomerRequest;
import com.example.multipledatabasespringboot.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.multipledatabasespringboot.core.AppConstant.SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 10:15 AM
 */
@WebMvcTest(CustomerResource.class)
class CustomerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerRequest request;

    @BeforeEach
    public void setUp() {
        request = CustomerRequest.builder().build();
    }

    @SneakyThrows
    @Test
    @DisplayName("Create User Endpoint - Test")
    void testCreateUserEndpoint() {
        request = CustomerRequest.builder()
                .username("timadeshola")
                .build();

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(AppUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch User by ID Endpoint - Test")
    void testFetchUserByIdEndpoint() {
        mockMvc.perform(get("/customers/fetchById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));

    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch User by Username Endpoint - Test")
    void testFetchUserByUsernameEndpoint() {
        mockMvc.perform(get("/customers/fetchByUsername/{username}", "timadeshola")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));
    }

    @SneakyThrows
    @Test
    @DisplayName(value = "Fetch All User Endpoint - Test")
    void testFetchUserByUsersEndpoint() {
        mockMvc.perform(get("/customers/viewAll")
                        .queryParam("start", "0")
                        .queryParam("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS));
    }

}