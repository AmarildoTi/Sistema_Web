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
@Table(name = "logradouro")
public class LogradouroModel implements Serializable {
		private static final long serialVersionUID = 1L; 
 
	/* Atributos da tabela Logradouro */
	@Id
	@GeneratedValue
	@Column(name = "id_logradouro")
	private Long codigo;
 
	@NotEmpty
	@Size(max = 110)
	@Column(name="endereco",length = 110, nullable = false)
	private String  endereco;

	@NotEmpty
	@Size(max = 9)
	@Column(name="cep",length = 9, nullable = false)
	private String  cep;

	
	/* Metodos get e Set da classe Logradouro */
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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
		LogradouroModel other = (LogradouroModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
