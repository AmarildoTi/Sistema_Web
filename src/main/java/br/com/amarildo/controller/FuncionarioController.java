package br.com.amarildo.controller;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.amarildo.model.FuncionarioModel;
import br.com.amarildo.repository.FuncionarioRepository;
import br.com.amarildo.util.NegocioException;
import br.com.amarildo.util.Transactional;

public class FuncionarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject 
	private FuncionarioRepository funcionarioRepository;

	@Transactional
	public FuncionarioModel salvar(FuncionarioModel funcionarioModel) throws NegocioException {
		if (funcionarioModel.getCargo().isEmpty()) {
			throw new NegocioException("Não é Possivel Salvar Funcionario sem Cargo");
		}
		return this.funcionarioRepository.guardar(funcionarioModel);
	}

	@Transactional
	public void atualizar(FuncionarioModel funcionarioModel) throws NegocioException {
		if (funcionarioModel.getCargo().isEmpty()) {
			throw new NegocioException("Não é possível fazer a Alteração campo cargo está vazio !");
		}
		this.funcionarioRepository.alterar(funcionarioModel);
	}

	@Transactional
	public void excluir(FuncionarioModel funcionarioModel) throws NegocioException {
		funcionarioModel = this.funcionarioRepository.porId(funcionarioModel.getCodigo());
		if (funcionarioModel.getCargo()== null) {
			throw new NegocioException("Não é possível excluir um Funcionario Demitido!");
		}
		this.funcionarioRepository.remover(funcionarioModel);
	}

}