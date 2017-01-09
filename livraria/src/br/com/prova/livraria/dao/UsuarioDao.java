package br.com.prova.livraria.dao;

import br.com.prova.livraria.bean.LivrariaBean;
import br.com.prova.livraria.modelo.Usuario;

public class UsuarioDao {

	public boolean exists(Usuario usuario) {
		for (Usuario u : LivrariaBean.ListaUsuarios) {
			if (usuario.getEmail().equals(u.getEmail()) && usuario.getSenha().equals(u.getSenha())) {
				usuario = u;
				return true;
			}
		}

		return false;
	}
}
