package com.mypetclinic.repositories;

import com.mypetclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialitiesRepository extends CrudRepository<Speciality, Long> {
}
