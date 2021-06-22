package br.com.locadoracarros.carrental.service;

import br.com.locadoracarros.carrental.entities.Car;
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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TenancyService {

	// Service for tenancy
	@Autowired
	TenancyRepository tenancyRepository;

	@Autowired
	CarService carService;

	@Autowired
	ClientService clientService;

	public Tenancy save(Tenancy tenancy) throws NullPointerException{
		if (tenancy.getCar() != null && tenancy.getClient() != null){
			if (tenancy.getCar().getId() > 0 && tenancy.getClient().getId() > 0){
				Optional<Car> optionalCar = this.carService.getCar(tenancy.getCar().getId());
				Optional<Client> optionalClient = this.clientService.getClient(tenancy.getClient().getId());

				if (optionalCar.isPresent() && optionalClient.isPresent()){
					tenancy.setCar(optionalCar.get());
					tenancy.setClient(optionalClient.get());
					return tenancyRepository.save(tenancy);
				}
			} else {
				throw new NullPointerException("Algum integrante da Tenancy não estava presente no banco de dados!");
			}
		} else {
			throw new NullPointerException("Algum integrante da Tenancy não estava presente!");
		}

		return null;
	}

	public Optional<Tenancy> getTenancy(int id){
		return this.tenancyRepository.findById(id);
	}

	//That's just a test for TenancyController
	public List<Tenancy> findByClient (int id){
		List<Tenancy> findClientTenancies = new ArrayList<>();
		for (Tenancy t : tenancyRepository.findAll()){
			if(t.getClient().getId() == id) {
				findClientTenancies.add(t);
			}
		}
		return findClientTenancies;
	}

	public List<Tenancy> findByCar (int id){
		List<Tenancy> findCarTenancies = new ArrayList<>();
		for(Tenancy t : tenancyRepository.findAll()){
			if (t.getCar().getId() == id){
				findCarTenancies.add(t);
			}
		}
		return findCarTenancies;
	}

	public List<Tenancy> getAll(){
		return (List<Tenancy>) this.tenancyRepository.findAll();
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

	/*
	Operation to calculate the totalPayment
	Validations will be done later
	 */
	public Tenancy processTenancy(Tenancy tenancy){
		long last = 0;
		long first = 0;
		final long DAY_IN_MS = 86400000L;

		if (tenancy.getFirstDate().getTime() > 0) {

			first = tenancy.getFirstDate().getTime();
		}

		if (tenancy.getLastDate().getTime() > 0) {

			last = tenancy.getLastDate().getTime();
		}

		int days = (int)((last - first) / DAY_IN_MS);

		int diariaEmHoras = (int) (((last - first) - (days * DAY_IN_MS)) / 3600000L) + (days * 24);

		if (diariaEmHoras < 24){

			diariaEmHoras = 24;
		}

		double basicPayment = 0;

		if (tenancy.getCar() != null){
			if (tenancy.getCar().getCategory() != null){
				if (tenancy.getCar().getCategory().getPricePerDay() > 0){

					basicPayment = tenancy.getCar().getCategory().getPricePerDay() / 24 * diariaEmHoras;
				}
			}
		}
		NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		String currencyString =  df.format(basicPayment).replaceAll("\\.", "").replaceAll(",", ".").substring(3);
		basicPayment = Double.parseDouble(currencyString);
		tenancy.setPayment(new BigDecimal(basicPayment));
		return tenancy;
	}
}
