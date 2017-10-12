package br.com.amarildo.repository;

import java.util.List;

public interface IRepository<T> {

	public T porId(Long id);

	public T guardar(T entity);

	public void alterar(T entity);

	public void remover(T entity);

	public List<T> todos();

	public List<T> todas();

}
