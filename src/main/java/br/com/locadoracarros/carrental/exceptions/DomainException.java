package br.com.locadoracarros.carrental.exceptions;

public class DomainException extends RuntimeException{
	public static final long serialVersionUID = 1L;

	public DomainException(String msg){
		super(msg);
	}
}
