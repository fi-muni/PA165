package cz.fi.muni.carshop.services;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cz.fi.muni.carshop.CarShopStorage;
import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;

public class CarShopStorageServiceImpl implements CarShopStorageService {

	@Override
	public Optional<Car> isCarAvailable(Color color, CarTypes type) {
		Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
		List<Car> carsOfSameType = allCars.get(type);
		return carsOfSameType.stream().filter(car -> car.getColor().equals(color)).findAny();
	}

	@Override
	public List<Car> getCheaperCarsOfSameTypeAndYear(Car referenceCar) {
		Map<CarTypes, List<Car>> allCars = CarShopStorage.getInstancce().getCars();
		List<Car> carsOfSameType = allCars.get(referenceCar.getType());
		return carsOfSameType.stream().filter(car -> referenceCar.getConstructionYear() == car.getConstructionYear()
				&& car.getPrice() < referenceCar.getPrice()).collect(Collectors.toList());
	}

	@Override
	public void addCarToStorage(Car car) {
		CarShopStorage.getInstancce().getCars().computeIfAbsent(car.getType(), x -> new ArrayList<>()).add(car);
	}

}
