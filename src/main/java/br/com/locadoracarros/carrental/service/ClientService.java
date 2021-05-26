package br.com.locadoracarros.carrental.service;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.repository.ClientRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public Client save(Client client){
		return this.clientRepository.save(client);
	}

	public Optional<Client> getClient(int id){
		return this.clientRepository.findById(id);
	}

	public Page<Client> getAll(int page, int size, String sort, String q, String attribute) {

		Sort sortable = Sort.by(attribute).ascending();

		if (attribute.trim().isEmpty()) {
			attribute = "name";
		}

		if (sort.toLowerCase().contains("desc")){
			sortable = Sort.by(attribute).descending();
		}

		Pageable pageable = PageRequest.of(page, size, sortable);

		if (q.isEmpty()){

			return this.clientRepository.findAll(pageable);
		}
		else{
			q = "%" + q.toLowerCase() + "%";
			return this.clientRepository.findClients(q, pageable);
		}

	}

	public Client updateClient(Client client) throws NotFoundException{
		Optional<Client> optionalClient = this.clientRepository.findById(client.getId());

		if (optionalClient.isPresent()){

			return this.clientRepository.save(client);
		}
		else{

			throw new NotFoundException("Objeto sendo editado inexistente");
		}
	}

}
