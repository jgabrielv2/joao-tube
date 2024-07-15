package br.com.joaotube.infra.exception;

public class CategoriaNotFoundException extends RuntimeException{

    public CategoriaNotFoundException(String message) {
        super(message);
    }
}
