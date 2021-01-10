package br.com.amarildo.controller;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.amarildo.model.LogradouroModel;
import br.com.amarildo.repository.LogradouroRepository;
import br.com.amarildo.util.jpa.Transactional;
import br.com.amarildo.util.mensagens.NegocioException;

public class LogradouroController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject 
	private LogradouroRepository logradouroRepository;

	@Transactional
	public LogradouroModel salvar(LogradouroModel logradouroModel) throws NegocioException {
		if (logradouroModel.getEndereco().isEmpty()) {
			throw new NegocioException("Não é Possivel Salvar Pessoa sem Endereço");
		}
		return this.logradouroRepository.guardar(logradouroModel);
	}

	@Transactional
	public void atualizar(LogradouroModel logradouroModel) throws NegocioException {
		if (logradouroModel.getEndereco().isEmpty()) {
			throw new NegocioException("Não é possível fazer a Alteração campo Endereço está vazio !");
		}
		this.logradouroRepository.alterar(logradouroModel);
	}

	@Transactional
	public void excluir(LogradouroModel logradouroModel) throws NegocioException {
		logradouroModel = this.logradouroRepository.porId(logradouroModel.getCodigo());
		if (logradouroModel.getEndereco()== null) {
			throw new NegocioException("Não é possível excluir uma Pessoa sem Endereço!");
		}
		this.logradouroRepository.remover(logradouroModel);
	}

}