package br.com.prova.livraria.modelo;

import javax.persistence.Entity;

@Entity
public class Autor extends BaseEntity {

	private String nome;
	private String email;

	@SuppressWarnings("unchecked")
	@Override
	public Autor createCopy() {
		Autor autor = new Autor();
		
		autor.id = this.id;
		autor.nome = this.nome;
		autor.email = this.email;
		
		return autor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



}
