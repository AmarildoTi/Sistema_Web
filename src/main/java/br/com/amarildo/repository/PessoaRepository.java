package br.com.amarildo.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.amarildo.model.PessoaModel;

public class PessoaRepository extends Repository<PessoaModel> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	public PessoaRepository(EntityManager manager) {
		super(manager);
	}
		
	public List<String> nomesQueContem(String nome) {
		TypedQuery<String> query = super.manager.createQuery("select distinct nome from PessoaModel " + "where upper(nome) like upper(:nome)", String.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	// Retorna os Tipos Agrupados para o Grafico de pizza
	public Hashtable<String, Integer> GetOrigemPessoa() {

		String Jpql = "select p.origemcadastro, count(p.origemcadastro) as total FROM PessoaModel p GROUP By p.origemcadastro";
		
		Hashtable<String, Integer> hashtableRegistros = new Hashtable<String, Integer>();

		Query query = super.manager.createQuery(Jpql);

		@SuppressWarnings("unchecked")
		Collection<Object[]> collectionRegistros = (Collection<Object[]>) query.getResultList();

		for (Object[] objects : collectionRegistros) {

			String tipoPessoa = (String) objects[0];
			int totalDeRegistros = ((Number) objects[1]).intValue();

			if (tipoPessoa.equals("X"))
				tipoPessoa = "XML";
			else
				tipoPessoa = "INPUT";

			hashtableRegistros.put(tipoPessoa, totalDeRegistros);

		}

		return hashtableRegistros;

	}	
	
}