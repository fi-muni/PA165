package cz.fi.muni.carshop.services;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;
import cz.fi.muni.carshop.exceptions.RequestedCarNotFoundException;

public interface CarShopStorageService {

	/**
	 * Adds car to storage, creates type section if not existing before,
	 * exception should be thrown if car price is less than 0
	 * 
	 * @param car
	 *            car to be added
	 */
	void addCarToStorage(Car car);

	/**
	 * Checks storage for a car of given color and type.
	 * 
	 * @param color
	 *            requested car color
	 * @param type
	 *            requested car type
	 * @return {@link Optional} of {@link Car}, empty if car doesn't exist
	 */
	Optional<Car> isCarAvailable(Color color, CarTypes type);

	/**
	 * Retrieves all cars of the same type and construction year as requested
	 * car.
	 * 
	 * @param car
	 *            reference car
	 * @return {@link List} of {@link Car}s satisfying the condition
	 */
	List<Car> getCheaperCarsOfSameTypeAndYear(Car car);

	/**
	 * Attempts to find a specified car in the storage and remove it from
	 * storage.
	 * 
	 * @param car
	 *            car to be found in storage
	 * @throw RequestedCarNotFoundException in case car doesn't exist in the
	 *        storage
	 */
	void sellCar(Car car) throws RequestedCarNotFoundException;

}
