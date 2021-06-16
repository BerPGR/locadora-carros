package br.com.locadoracarros.carrental.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarBrandVO {

	private String brandName;

	public CarBrandVO(String brandName) {
		this.brandName = brandName;
	}
}