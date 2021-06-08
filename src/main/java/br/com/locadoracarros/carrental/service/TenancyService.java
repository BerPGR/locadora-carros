package br.com.locadoracarros.carrental.service;

import br.com.locadoracarros.carrental.entities.Client;
import br.com.locadoracarros.carrental.entities.Tenancy;
import br.com.locadoracarros.carrental.repository.TenancyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenancyService {

	// Service for tenancy
	@Autowired
	TenancyRepository tenancyRepository;

	public Tenancy save(Tenancy tenancy){
		return this.tenancyRepository.save(tenancy);
	}

	public Optional<Tenancy> getTenancy(int id){
		return this.tenancyRepository.findById(id);
	}

	public Page<Tenancy> getAll(int page, int size, String sort, String q, String attribute) {

		Sort sortable = Sort.by(attribute).ascending();

		if (attribute.trim().isEmpty()) {
			attribute = "client";
		}

		if (sort.toLowerCase().contains("desc")){
			sortable = Sort.by(attribute).descending();
		}

		Pageable pageable = PageRequest.of(page, size, sortable);

		if (q.isEmpty()){

			return this.tenancyRepository.findAll(pageable);
		}
		else{
			q = "%" + q.toLowerCase() + "%";
			return this.tenancyRepository.findTenancies(q, pageable);
		}

	}

	public Tenancy updateTenancy(Tenancy tenancy) throws NotFoundException{
		Optional<Tenancy> optionalTenancy = this.tenancyRepository.findById(tenancy.getId());

		if (optionalTenancy.isPresent()){
			return this.tenancyRepository.save(tenancy);
		}
		else {

			throw new NotFoundException("Objeto sendo editado inexistente.");
		}
	}

	/*public void processTenancy(Tenancy tenancy){
		long t1 = tenancy.getFirstDate().getTime();
		long t2 = tenancy.getLastDate().getTime();
		int days = (int)(t2-t1) / 1000 / 60 / 60 / 24;

		double basicPayment = tenancy.getCar().getCategory().getPricePerDay() * days;
	}*/
}
