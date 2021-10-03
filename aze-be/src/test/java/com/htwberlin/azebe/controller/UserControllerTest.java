package com.htwberlin.azebe.controller;

import com.htwberlin.azebe.TestVariables;
import com.htwberlin.azebe.dto.UserDto;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserService service;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @BeforeEach
    void beforeEach() {
        when(service.getUserById(1)).thenReturn(TestVariables.one);
    }

    @Test
    public void test_get_user_by_id_with_admin_role() throws Exception {
        mockMvc.perform(
                get("/users/get/id")
                        .param("id", "1")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_user_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                get("/users/get/id")
                        .param("id", "1")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_update_user_by_id_with_admin_role() throws Exception {
        mockMvc.perform(
                put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"test\":\"testUserDetails\"}")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_user_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"test\":\"testUserDetails\"}")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_get_user_by_name_with_admin_role() throws Exception {
        mockMvc.perform(
                get("/users/get/name")
                        .param("name", "test")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_user_by_name_with_employee_role() throws Exception {
        mockMvc.perform(
                get("/users/get/name")
                        .param("name", "test")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_delete_user_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                delete("/users/delete/id")
                        .param("id", "1")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_delete_user_by_name_with_employee_role() throws Exception {
        mockMvc.perform(
                delete("/users/delete/name")
                        .param("name", "test")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_delete_user_by_id_with_admin_role() throws Exception {
        mockMvc.perform(
                delete("/users/delete/id")
                        .param("id", "1")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_delete_user_by_name_with_admin_role() throws Exception {
        mockMvc.perform(
                delete("/users/delete/name")
                        .param("name", "test")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_sign_up_user_with_admin_role() throws Exception {
        mockMvc.perform(
                post("/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"test\":\"testUserDetails\"}")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_sign_up_user_with_employee_role() throws Exception {
        mockMvc.perform(
                post("/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"testUserPassword\"}")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_dto_transfer() {
        UserDto userDto = modelMapper.map(TestVariables.one, UserDto.class);
        User user = controller.convertToEntity(userDto);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getRole(), user.getRole());
        assertEquals(userDto.getPassword(), user.getPassword());
    }


}
