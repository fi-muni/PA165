package cz.fi.muni.pa165.dao;

import org.springframework.data.repository.CrudRepository;

import cz.fi.muni.pa165.entity.Order;
import cz.fi.muni.pa165.entity.Price;

public interface PriceRepository extends CrudRepository<Price, Long>  {
}
