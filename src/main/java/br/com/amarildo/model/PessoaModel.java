package br.com.amarildo.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "pessoa")
public class PessoaModel implements Serializable {
		private static final long serialVersionUID = 1L; 
 
	/* Atributos da tabela Pessoa */
	@Id
	@GeneratedValue
	@Column(name = "id_pessoa")
	private Long codigo;
	
	@NotEmpty
	@Size(max = 100)
	@Column(name="nome",length = 100, nullable = false)
	private String  nome;

	@NotEmpty
	@Size(max = 100)
	@Column(name="email",length = 100, nullable = false)
	private String email;

	@NotEmpty
	@Size(max = 1)
	@Column(name="origemcadastro",length = 1, nullable = false)
	private String  origemcadastro;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="id_logradouro")
	private LogradouroModel logradouroModel;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="id_funcionario")
	private FuncionarioModel funcionarioModel;

	@NotNull
	@OneToOne
	@JoinColumn(name="id_usuario")
	private UsuarioModel usuarioModel;
	
	/** Abaixo FOREIGN KEY. chave estrangeira da Tabela Logradouro*/
	public LogradouroModel getLogradouroModel() {
		return logradouroModel;
	}
	public void setLogradouroModel(LogradouroModel logradouroModel) {
		this.logradouroModel =  logradouroModel;
	}
	
	/** Abaixo FOREIGN KEY. chave estrangeira da Tabela Funcionario*/
	public FuncionarioModel getFuncionarioModel() {
		return funcionarioModel;
	}
	public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
		this.funcionarioModel =  funcionarioModel;
	}

	/** Abaixo FOREIGN KEY. chave estrangeira da Tabela Usuario*/
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel =  usuarioModel;
	}

 
	/* Metodos get e Set da classe Pessoa */
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrigemCadastro() {
		return origemcadastro;
	}
	public void setOrigemCadastro(String origemcadastro) {
		this.origemcadastro = origemcadastro;
	}

	
	/* Abaixo hashCode() e equals */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaModel other = (PessoaModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
