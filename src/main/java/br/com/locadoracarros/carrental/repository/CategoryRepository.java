package br.com.locadoracarros.carrental.repository;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {


	// Repository for category
	Page<Category> findAll(Pageable pageable);

	Page<Category> findById(int id, Pageable pageable);

	Optional<Category> findById(Integer integer);

	@Query(value = "SELECT * FROM category  "
			+ "WHERE lower(carType) like :query ", nativeQuery = true)
	public Page<Category> findCategories(@Param("query") String query, Pageable pageable);
}
