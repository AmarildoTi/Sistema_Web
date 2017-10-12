package br.com.amarildo.view;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.amarildo.controller.UsuarioController;
import br.com.amarildo.model.UsuarioModel;
import br.com.amarildo.repository.UsuarioRepository;
import br.com.amarildo.util.NegocioException;

@Named
@RequestScoped
public class LoginBean {
	@Inject
	private UsuarioController usuarioController;

	@Inject
	private UsuarioRepository usuarioRepository;

	private UsuarioModel usuarioModel;
	private String nomeUsuario;
	private String numSenha;

	public String login() {
		
		usuarioModel = usuarioRepository.ValidaUsuario(this.nomeUsuario,this.numSenha);
		
		if (usuarioModel != null) {
			this.usuarioController.setNome(this.nomeUsuario);
			this.usuarioController.setDataLogin(new Date());
			this.usuarioController.setCodigoId(usuarioModel.getCodigo());
			return "sistema/home?faces-redirect=true";
		} else if ((this.nomeUsuario).isEmpty()) {
			NegocioException.MensagemAlerta("Por Favor Informar o Login!");
		} else if ((this.numSenha).isEmpty()) {
			NegocioException.MensagemAlerta("Por Favor Informar a Senha!");
		} else {
			NegocioException.MensagemErro("Usuário/Senha Inválidos!");
		}
	
	    return null;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return numSenha;
	}

	public void setSenha(String numSenha) {
		this.numSenha = numSenha;
	}

	public String Logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
	
}
