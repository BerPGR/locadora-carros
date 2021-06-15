package br.com.locadoracarros.carrental.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.locadoracarros.carrental.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Integer> {

	// Repository for car
	Page<Car> findAll(Pageable pageable);

	Page<Car> findById(int id, Pageable pageable);

	@Override
	Optional<Car> findById(Integer integer);

	@Query(value = "SELECT * FROM car  "
			+ "WHERE lower(model) like :query ", nativeQuery = true)
	Page<Car> findCars(@Param("query") String query, Pageable pageable);

	@Query(value = "SELECT Distinct brand FROM car", nativeQuery = true)
	List<String> listBrandsDistint();

}