package com.binary.carShow.repository;

import com.binary.carShow.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository  extends CrudRepository<Car,Long> {
    //CRUD

}
