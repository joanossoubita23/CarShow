package com.binary.carShow;


import com.binary.carShow.entity.Car;
import com.binary.carShow.entity.Owner;
import com.binary.carShow.repository.CarRepository;
import com.binary.carShow.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//
//@EnableAutoConfiguration
//@ComponentScan //this enables the spring boot component scan to find all the components in the application
//@Configuration // this annotation is used to define a configuration class that bean to Spring application
@SpringBootApplication
public class CarShowApplication implements CommandLineRunner {
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	private  static final Logger logger= LoggerFactory.getLogger(CarShowApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(CarShowApplication.class, args);
		logger.info("Application start");
		//ORM (Object Relational Mapping) : is a technique that allows you to fetch from manipulate a database
		//by using OOP paradigm --> Hibernate
		//JPA Java persistence API : Is a technique that allows to data bse to easy developer job
		//class Book (id,title, author,price)->Table Book(id,title,author,price)
		// in JPA an entity is a table
	}

	@Override
	public void run(String... args) throws Exception {
		Owner owner= new Owner("John","Doe");
		Owner owner1=new Owner("Dastan","ossou");
		ownerRepository.save(owner);
		ownerRepository.save(owner1);

		List<Car> cars = Arrays.asList(
				new Car("Ford","Lighting","Gray","FL-234",2023,75000,owner),
				new Car("Nissan","Leaf","Green","BFG-345",2022,40000,owner1),
				new Car("Toyota","Sienna","Silver","CDF-233",2024,60000,owner),
				new Car("Honda","Accord","White","HW-345",2024,57000,owner1)
		);
		carRepository.saveAll(cars);
		carRepository.
				findAll().forEach(car -> logger.info(car.getMake()+" "+ car.getModel()));
		ownerRepository.findAll().forEach(ow -> logger.info(ow.getFirstName()));

	}
}
