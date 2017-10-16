package cz.fi.muni.carshop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.fi.muni.carshop.entities.Car;
import cz.fi.muni.carshop.enums.CarTypes;

public class CarShopStorage {

	private static CarShopStorage instance;
	private Map<CarTypes, List<Car>> cars = new HashMap<>();

	public static CarShopStorage getInstancce() {
		if (instance == null) {
			synchronized (CarShopStorage.class) {
				if (instance == null) {
					instance = new CarShopStorage();
				}
			}
		}
		return instance;
	}

	public Map<CarTypes, List<Car>> getCars() {
		return cars;
	}

}
