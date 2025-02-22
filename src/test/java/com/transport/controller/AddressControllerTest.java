package com.transport.controller;

import com.transport.api.dto.AddressDto;
import com.transport.api.mapper.AddressMapper;
import com.transport.service.impl.AddressServiceImpl;
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
public class AddressControllerTest {
    @InjectMocks
    private AddressController addressController;
    @Mock
    private AddressServiceImpl addressServiceImpl;
    @Mock
    private AddressMapper addressMapper;
    private static final Long ID = 1L;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setControllerAdvice()
                .build();
    }

    @Test
    @WithMockUser(roles = "FORWARDER")
    void testFindAddress() throws Exception {
        final AddressDto addressDto = addressMapper.convert(TestFixture.getAddress(ID));
        when(addressServiceImpl.findById(anyLong())).thenReturn(addressDto);

        this.mockMvc.perform(get("/api/addresses/{id}", ID))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "FORWARDER")
    public void testDeleteAddress() throws Exception {
        doNothing().when(addressServiceImpl).deleteAddress(anyLong());

        this.mockMvc.perform(delete("/api/addresses/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
