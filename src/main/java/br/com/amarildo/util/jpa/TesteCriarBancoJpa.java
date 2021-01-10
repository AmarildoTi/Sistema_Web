package br.com.amarildo.util.jpa;

import javax.persistence.Persistence;

public class TesteCriarBancoJpa {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("Conexao_Banco_de_Dados_MySql_Sistema_Web");
	}
}
