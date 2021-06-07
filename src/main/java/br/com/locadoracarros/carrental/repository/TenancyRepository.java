package br.com.locadoracarros.carrental.repository;

import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.entities.Tenancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenancyRepository extends PagingAndSortingRepository<Tenancy, Integer> {

	//Repository for tenancy

	double processTenancy();

	Page<Tenancy> findAll (Pageable pageable);

	Page<Tenancy> findById (int id, Pageable pageable );

	Optional<Tenancy> findById (Integer integer);

	@Query(value = "SELECT * FROM tenancy  "
			+ "WHERE lower(client) like :query ", nativeQuery = true)
	public Page<Tenancy> findTenancies(@Param("query") String query, Pageable pageable);
}
