package com.mypetclinic.controller;

import com.mypetclinic.model.Owner;
import com.mypetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController ownerController;
    Set<Owner> owners;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(2L);
        owners.add(owner1);
        owners.add(owner2);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

//    @Test
//    void listOwners() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//        mockMvc.perform(get("/owners"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners",hasSize(2)));
//
//    }

//    @Test
//    void listOwnersByIndex() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//        mockMvc.perform(get("/owners/index"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners",hasSize(2)));
//
//    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);

    }

    @Test
    void processFindFormReturnOne() throws Exception {
        List<Owner> owners = new ArrayList<>();
        Owner owner = new Owner();
        owner.setId(1L);
        owners.add(owner);
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(List.of(new Owner(), new Owner()));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(1L)).thenReturn(owners.stream().findFirst().get());
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"));
    }

}