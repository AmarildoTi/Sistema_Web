package br.com.amarildo.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.amarildo.model.LogradouroModel;


public class LogradouroRepository extends Repository<LogradouroModel> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	public LogradouroRepository(EntityManager manager) {
		super(manager);
	}
	
	public List<String> enderecosQueContem(String endereco) {
		TypedQuery<String> query = super.manager.createQuery("select distinct endereco from LogradouroModel " + "where upper(cargo) like upper(:endereco)", String.class);
		query.setParameter("endereco", "%" + endereco + "%");
		return query.getResultList();
	}

}
