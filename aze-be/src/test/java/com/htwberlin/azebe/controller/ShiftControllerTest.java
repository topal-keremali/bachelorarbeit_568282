package com.htwberlin.azebe.controller;

import com.htwberlin.azebe.TestVariables;
import com.htwberlin.azebe.service.ShiftService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShiftControllerTest {

    @MockBean
    private ShiftService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
//        when(service.getAll(1,any(String.class))).thenReturn(TestVariables.shiftList);
    }

    @Test
    public void test_get_shift_by_id_with_admin_role() throws Exception {
        mockMvc.perform(
                get("/shift/get/all/")
                        .param("id", "1")
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_shift_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                get("/shift/get/all/")
                        .param("id", "2")
                        .header("Authorization", TestVariables.validEmployeeJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_begin_shift_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                put("/shift/begin")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

    @Test
    public void test_end_shift_by_id_with_employee_role() throws Exception {
        mockMvc.perform(
                        put("/shift/begin")
                                .param("id", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", TestVariables.validAdminJwt))
                .andExpect(status().isOk());
    }

}
