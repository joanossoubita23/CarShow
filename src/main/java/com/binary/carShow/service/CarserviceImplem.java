package com.binary.carShow.service;

import com.binary.carShow.entity.Car;
import com.binary.carShow.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CarserviceImplem implements  CarService{
    private  final CarRepository carRepository;

    public CarserviceImplem(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCars() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        } else {
            throw  new EntityNotFoundException("Car with id"+id+"id not found");

        }

    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void removeCarById(Long id) {
        carRepository.deleteById(id);

    }


}

    // Optionals :is container object used to represent a value the may or may not be present.

