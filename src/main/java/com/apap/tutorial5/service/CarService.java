package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.CarModel;


public interface CarService {
	Optional<CarModel> getCarDetailById(Long id);
	void addCar(CarModel car);
	void deleteCar(CarModel car);
	List<CarModel> getAllCar();
	
}
