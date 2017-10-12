package br.com.amarildo.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.amarildo.model.UsuarioModel;

public class UsuarioRepository extends Repository<UsuarioModel> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	public UsuarioRepository(EntityManager manager) {
		super(manager);
	}
	
	public UsuarioModel ValidaUsuario(String nomeUsuario,String senha){
		String Jpql = "Select u From UsuarioModel u Where u.usuario = :usuario And u.senha = :senha";
		try {
 			//Query que sera executada (Jpql) 	
			TypedQuery<UsuarioModel> query = super.manager.createQuery(Jpql, UsuarioModel.class);
		 	//Parâmetros da Query
			query.setParameter("usuario", nomeUsuario);
			query.setParameter("senha", senha);
 			//Retorna o Usuario se For Localizado
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

}
