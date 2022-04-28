package org.fundacionjala.contacts.controllers;

import org.fundacionjala.contacts.JsonSerializerHelper;
import org.fundacionjala.contacts.db.entities.UserData;
import org.fundacionjala.contacts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldReturnOkStatusWhenRetrievingAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenPasswordIsEmpty() throws Exception {
        UserData user = new UserData("Marcelo Muñoz", "","doctorchebo");

        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(JsonSerializerHelper.toJson(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenUsernameIsEmpty() throws Exception {
        UserData user = new UserData("Marcelo Muñoz", "1234","");

        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(JsonSerializerHelper.toJson(user)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        r -> assertThat(r.getResolvedException().getMessage())
                                .contains("Field 'username' is required."));
    }

    @Test
    public void shouldReturnNotFoundWhenNonExistingContactIsRequested() throws Exception {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{code}", 15L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(
                        r -> assertThat(r.getResolvedException().getMessage())
                                .contains("Unable to find user with Id: 15"));
    }

    @Test
    public void shouldReturnOkWhenSavingUser() throws Exception {
        UserData user = new UserData("Marcelo Muñoz","1234", "doctorchebo");

        when(userRepository.save(any(UserData.class))).thenReturn(user);

        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(JsonSerializerHelper.toJson(user)))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
