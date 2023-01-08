package com.mypetclinic.services.map;

import com.mypetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(),new PetMapService());
        Owner SampleOwner = new Owner();
        SampleOwner.setId(1L);
        SampleOwner.setLastName("Smith");
        ownerMapService.save(SampleOwner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(1,ownerSet.size());

    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(1L));
        assertEquals(0,ownerMapService.findAll().size());

    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(1L);
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void saveExistId() {
        Long id = 2L;
        Owner owner = new Owner();
        owner.setId(id);
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id,savedOwner.getId());

    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(new Owner());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(1L);
        assertEquals(1L,owner.getId());

    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName("Smith");
        assertNotNull(smith);
        assertEquals(1L,smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = ownerMapService.findByLastName("foo");
        assertNull(smith);
    }

}