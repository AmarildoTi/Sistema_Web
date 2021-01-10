package br.com.amarildo.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.amarildo.controller.FuncionarioController;
import br.com.amarildo.controller.LogradouroController;
import br.com.amarildo.controller.PessoaController;
import br.com.amarildo.controller.UsuarioController;
import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.repository.PessoaRepository;
import br.com.amarildo.util.mensagens.NegocioException;

@Named
@ViewScoped
public class ConsultaFuncionariosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioController funcionarioController;

	@Inject
	private LogradouroController logradouroController;

	@Inject
	private PessoaController pessoaController;

	@Inject
	private UsuarioController usuarioController;

	@Inject transient
	private PessoaRepository pessoaRepository;
	
	@Produces
	private List<PessoaModel> pessoas;

	@Produces
	private List<PessoaModel> filtraPessoas;
	
	@Inject transient
	private PessoaModel pessoaModel;

	@Inject
	private PhotoCamBean excluirImagem;
	
	@PostConstruct
	public void consultar() {
		this.pessoas = pessoaRepository.todas();
	}
	
	public void Excluir(PessoaModel pessoaModel) throws NegocioException{
		excluirImagem.deleteArquivo(pessoaModel.getCodigo().toString());
		this.pessoaController.excluir(pessoaModel);
		this.funcionarioController.excluir(pessoaModel.getFuncionarioModel());
		this.logradouroController.excluir(pessoaModel.getLogradouroModel());
		this.usuarioController.excluir(pessoaModel.getFuncionarioModel().getUsuarioModel());
		//Atualiza o Registro na Tela Assim que For Excluído
		this.consultar();
	}
	
	//Carrega as Informações de Uma Pessoa para ser Editada.
	public void Editar(PessoaModel pessoaModel) throws NegocioException{
		this.pessoaModel = pessoaModel;
	}
	
	//Atualiza Registro Alterado
	public void AlterarRegistro() throws NegocioException{
		this.pessoaController.atualizar(pessoaModel);
		this.funcionarioController.atualizar(pessoaModel.getFuncionarioModel());
		this.logradouroController.atualizar(pessoaModel.getLogradouroModel());
	}

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}
	
	public List<PessoaModel> getFiltraPessoas() {
		return filtraPessoas;
	}

	public void setFiltraPessoas(List<PessoaModel> filtraPessoas) {
		this.filtraPessoas = filtraPessoas;
	}

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

}