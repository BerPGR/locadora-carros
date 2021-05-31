package br.com.locadoracarros.carrental.service;

import br.com.locadoracarros.carrental.entities.Car;
import br.com.locadoracarros.carrental.entities.Category;
import br.com.locadoracarros.carrental.repository.CategoryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {


	@Autowired
	CategoryRepository categoryRepository;

	public Category save(Category category) {

		return this.categoryRepository.save(category);
	}

	public Optional<Category> getCategory(int id) {

		return this.categoryRepository.findById(id);
	}


	public Page<Category> getAll(int page, int size, String sort, String q, String attribute) {

		Sort sortable = Sort.by(attribute).ascending();

		if (attribute.trim().isEmpty()) {
			attribute = "carType";
		}

		if (sort.toLowerCase().contains("desc")){
			sortable = Sort.by(attribute).descending();
		}

		Pageable pageable = PageRequest.of(page, size, sortable);

		if (q.isEmpty()){

			return this.categoryRepository.findAll(pageable);
		}
		else{
			q = "%" + q.toLowerCase() + "%";
			return this.categoryRepository.findCategories(q, pageable);
		}

	}

	public Category updateCategory(Category category) throws NotFoundException {
		Optional<Category> optionalCar = this.categoryRepository.findById(category.getId());

		if (optionalCar.isPresent()) {

			return this.categoryRepository.save(category);
		} else {

			throw new NotFoundException("Objeto sendo editado inexistente");
		}
	}
}
