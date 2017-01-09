package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.prova.livraria.dao.PopulaBanco;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.modelo.Usuario;

@ManagedBean(eager = true)
@ApplicationScoped
public class LivrariaBean implements Serializable {

	public final static List<Usuario> ListaUsuarios = new ArrayList<Usuario>();
	public final static List<Autor> ListaAutores = new ArrayList<Autor>();
	public final static List<Livro> ListaLivros = new ArrayList<Livro>();

	@PostConstruct
	public void init() {
		// Load database
		// Carrega base de dados
		ListaUsuarios.addAll(PopulaBanco.fillListaUsuarios());
		ListaAutores.addAll(PopulaBanco.fillListaAutores());
		ListaLivros.addAll(PopulaBanco.fillListaLivros());
	}
}
