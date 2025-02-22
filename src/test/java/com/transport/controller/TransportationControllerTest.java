package com.transport.controller;

import com.transport.api.dto.TransportationDto;
import com.transport.api.mapper.TransportationMapper;
import com.transport.model.Transportation;
import com.transport.service.impl.TransportationServiceImpl;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransportationControllerTest {
    @InjectMocks
    private TransportationController transportationController;
    @Mock
    private TransportationServiceImpl transportationServiceImpl;
    @Mock
    private TransportationMapper transportationMapper;
    private static final Long ID = 1L;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transportationController)
                .setControllerAdvice()
                .build();
    }

    @WithMockUser(roles = "FORWARDER")
    @Test
    public void testDeleteTransportation() throws Exception {
        doNothing().when(transportationServiceImpl).deleteTransportation(anyLong());

        this.mockMvc.perform(delete("/api/transportations/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "FORWARDER")
    @Test
    public void testFindTransportationById() throws Exception {
        final Transportation transportation = TestFixture.getTransportation(ID);
        TransportationDto transportationDto = transportationMapper.convert(transportation);
        when(transportationServiceImpl.findById(anyLong())).thenReturn(transportationDto);

        this.mockMvc.perform(get("/api/transportations/{id}", ID))
                .andExpect(status().isOk());
    }
}
