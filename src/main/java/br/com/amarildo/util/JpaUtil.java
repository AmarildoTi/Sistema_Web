package br.com.amarildo.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("Conexao_Banco_de_Dados_MySql_Sistema_Web");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
