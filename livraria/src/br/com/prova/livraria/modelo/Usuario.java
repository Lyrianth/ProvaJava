package br.com.prova.livraria.modelo;

import javax.persistence.Entity;

@Entity
public class Usuario extends BaseEntity {

	private String email;
	private String senha;
	private boolean admin;

	@SuppressWarnings("unchecked")
	@Override
	public Usuario createCopy() {
		Usuario usuario = new Usuario();

		usuario.id = this.id;
		usuario.email = this.email;
		usuario.senha = this.senha;
		usuario.admin = this.admin;

		return usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
