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
@Table(name = "funcionario")
public class FuncionarioModel implements Serializable {
		private static final long serialVersionUID = 1L; 
 
	/* Atributos da tabela Pessoa */
	@Id
	@GeneratedValue
	@Column(name = "id_funcionario")
	private Long codigo;

	@NotEmpty
	@Size(max = 100)
	@Column(name="cargo",length = 100, nullable = false)
	private String  cargo;

	@NotNull
	@OneToOne
	@JoinColumn(name="id_usuario")
	private UsuarioModel usuarioModel;

	
	/** Metodos get e Set da classe Pessoa */
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	/** Abaixo FOREIGN KEY. chave estrangeira da Tabela Usuario*/
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel =  usuarioModel;
	}
	
	/** Abaixo hashCode() e equals */
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
		FuncionarioModel other = (FuncionarioModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
