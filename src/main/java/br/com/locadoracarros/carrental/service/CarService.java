package br.com.locadoracarros.carrental.service;

import br.com.locadoracarros.carrental.repository.CarRepository;
import br.com.locadoracarros.carrental.vo.CarBrandVO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.locadoracarros.carrental.entities.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {


	// Service for car
	@Autowired
	CarRepository carRepository;


	public Car save(Car car) {

		return this.carRepository.save(car);
	}

	public Optional<Car> getCarBrands(int id) {
		List<CarBrandVO> carBrandVOList = new ArrayList<>();
		for (String s : carRepository.listBrandsDistint()) {
			carBrandVOList.add(new CarBrandVO(s));
		}

		return this.carRepository.findById(id);
	}

	public Optional<Car> getCar(int id) {

		return this.carRepository.findById(id);
	}


	public Page<Car> getAll(int page, int size, String sort, String q, String attribute) {

		Sort sortable = Sort.by(attribute).ascending();

		if (attribute.trim().isEmpty()) {
			attribute = "model";
		}

		if (sort.toLowerCase().contains("desc")){
			sortable = Sort.by(attribute).descending();
		}

		Pageable pageable = PageRequest.of(page, size, sortable);

		if (q.isEmpty()){

			return this.carRepository.findAll(pageable);
		}
		else{
			q = "%" + q.toLowerCase() + "%";
			return this.carRepository.findCars(q, pageable);
		}

	}

	public Car updateCar(Car car) throws NotFoundException{
		Optional<Car> optionalCar = this.carRepository.findById(car.getId());

		if (optionalCar.isPresent()) {

			return this.carRepository.save(car);
		} else {

			throw new NotFoundException("Objeto sendo editado inexistente");
		}
	}
}