package com.transport.controller;

import com.transport.api.dto.CargoDto;
import com.transport.service.impl.CargoServiceImpl;
import com.transport.utils.TestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CargoControllerTest {
    @InjectMocks
    private CargoController cargoController;
    @Mock
    private CargoServiceImpl cargoServiceImpl;
    private static final Long ID = 1L;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cargoController).setControllerAdvice().build();
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void testDeleteCargo() throws Exception {
        doNothing().when(cargoServiceImpl).deleteCargo(ID);

        this.mockMvc.perform(delete("/api/cargos/{id}", ID)).andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void testFindCargoById() throws Exception {
        final CargoDto cargo = TestFixture.getCargoDto(ID);
        when(cargoServiceImpl.findById(ID)).thenReturn(cargo);

        this.mockMvc.perform(get("/api/cargos/{id}", ID)).andExpect(status().isOk());
    }

}
