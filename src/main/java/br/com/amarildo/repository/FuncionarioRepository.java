package br.com.amarildo.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.amarildo.model.FuncionarioModel;


public class FuncionarioRepository extends Repository<FuncionarioModel> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	public FuncionarioRepository(EntityManager manager) {
		super(manager);
	}
	
	public List<String> cargosQueContem(String cargo) {
		TypedQuery<String> query = super.manager.createQuery("select distinct cargo from FuncionarioModel " + "where upper(cargo) like upper(:cargo)", String.class);
		query.setParameter("cargo", "%" + cargo + "%");
		return query.getResultList();
	}

}
