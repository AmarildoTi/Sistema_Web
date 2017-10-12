package br.com.amarildo.controller;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.repository.PessoaRepository;
import br.com.amarildo.util.NegocioException;
import br.com.amarildo.util.Transactional;

public class PessoaController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaRepository pessoaRepository;

	@Transactional
	public PessoaModel salvar(PessoaModel pessoaModel) throws NegocioException {
		if (pessoaModel.getNome().isEmpty()) {
			throw new NegocioException("Nome da Pessoa Não pode ser vazio ");
		}
		return this.pessoaRepository.guardar(pessoaModel);
	}

	@Transactional
	public void atualizar(PessoaModel pessoaModel) throws NegocioException {
		if (pessoaModel.getNome().isEmpty()) {
			throw new NegocioException("Não é possível fazer a Alteração Campo Nome está vazio !");
		}
		this.pessoaRepository.alterar(pessoaModel);
	}

	@Transactional
	public void excluir(PessoaModel pessoaModel) throws NegocioException {
		pessoaModel = this.pessoaRepository.porId(pessoaModel.getCodigo());
		if (pessoaModel.getNome() == null) {
			throw new NegocioException("Não é possível excluir um Funcionario Demitido!");
		}
		this.pessoaRepository.remover(pessoaModel);
	}

}
