package br.com.amarildo.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.amarildo.model.UsuarioModel;
import br.com.amarildo.repository.UsuarioRepository;
import br.com.amarildo.util.jpa.Transactional;
import br.com.amarildo.util.mensagens.NegocioException;

@Named
@SessionScoped
public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;	
	
	private String nome;
	private Date dataLogin;
	private Long codigoId;
	
	public boolean isLogado() {
		return nome != null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigoId() {
		return codigoId;
	}

	public void setCodigoId(Long codigoId) {
		this.codigoId = codigoId;
	}
	
	public Date getDataLogin() {
		return dataLogin;
	}

	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
	}

	@Transactional
	public UsuarioModel addId(Long CodUsuario) throws NegocioException {
 		if (CodUsuario == null) {
			throw new NegocioException("Não foi Encontrado o Usuário que Ira Cadastar o Funcionario!!");
		}
		return this.usuarioRepository.porId(CodUsuario);
	}
	
	@Transactional
	public UsuarioModel salvar(UsuarioModel usuarioModel) throws NegocioException {
		if (usuarioModel.getUsuario().isEmpty()) {
			throw new NegocioException("Login Não pode ser vazio ");
		}
		return this.usuarioRepository.guardar(usuarioModel);
	}

	@Transactional
	public void atualizar(UsuarioModel usuarioModel) throws NegocioException {
		if (usuarioModel.getUsuario().isEmpty()) {
			throw new NegocioException("Não é possível fazer a Alteração campo Usuário está vazio !");
		}
		this.usuarioRepository.alterar(usuarioModel);
	}

	@Transactional
	public void excluir(UsuarioModel usuarioModel) throws NegocioException {
		usuarioModel = this.usuarioRepository.porId(usuarioModel.getCodigo());
		if (usuarioModel.getUsuario() == null) {
			throw new NegocioException("Não é possível excluir um Funcionario Demitido!");
		}
		this.usuarioRepository.remover(usuarioModel);
	}

}
