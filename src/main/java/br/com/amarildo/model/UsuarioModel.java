package br.com.amarildo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
 
@Entity	
@Table(name="usuario")
public class UsuarioModel implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue
	@Column(name="id_usuario")
	private Long codigo;
 
	@NotEmpty
	@Size(max = 20)
	@Column(name="usuario",length = 20, nullable = false)
	private String usuario;
 
	@NotEmpty
	@Size(max = 10)
	@Column(name="senha",length = 10, nullable = false)
	private String senha;
 
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
		UsuarioModel other = (UsuarioModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
}