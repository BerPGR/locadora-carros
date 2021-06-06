package br.com.locadoracarros.carrental.repository;

import br.com.locadoracarros.carrental.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {


	// Repository for client
	Page<Client> findAll (Pageable pageable);

	Page<Client> findById (int id, Pageable pageable);

	@Override
	Optional<Client> findById (Integer integer);

	@Query(value = "SELECT * FROM client  "
			+ "WHERE lower(name) like :query ", nativeQuery = true)
	public Page<Client> findClients(@Param("query") String query, Pageable pageable);

}
