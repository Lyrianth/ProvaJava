package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import br.com.prova.livraria.dao.BaseDao;
import br.com.prova.livraria.lazymodel.EntityLazyDataModel;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.util.MessageUtils;

@ManagedBean
@ViewScoped
public class LivroBean extends TabViewBean<Livro> implements Serializable {

	private Integer autorId;

	private BaseDao<Autor> daoAutor;

	private List<SelectItem> autores;

	@PostConstruct
	public void init() {
		System.out.println(this.getClass().toString() + " created.");

		this.daoEntity = new BaseDao<Livro>(Livro.class);
		this.daoAutor = new BaseDao<Autor>(Autor.class);
		this.lazyModel = new EntityLazyDataModel<Livro>(daoEntity.listAll(), Livro.class);
		this.setEntity(new Livro());
		this.entityName = "Livro";
		this.tabIndex = 0;

		this.autores = new ArrayList<SelectItem>();
		for (Autor autor : daoAutor.listAll())
			autores.add(new SelectItem(autor.getId(), autor.getNome()));
	}

	private void disableAutorItem(Autor autor) {
		for (SelectItem item : autores) {
			System.out.println(autor.getNome() + " - " + autor.getId().equals(item.getValue()));
			if (autor.getId().equals(item.getValue())) {
				item.setDisabled(true);
				break;
			}
		}
	}

	private void enableAutorItem(Autor autor) {
		for (SelectItem item : autores) {
			if (autor.getId().equals(item.getValue())) {
				item.setDisabled(false);
				break;
			}
		}
	}

	private void reenableAllAutorItems() {
		for (SelectItem item : autores)
			item.setDisabled(false);
	}

	@PreDestroy
	public void destroy() {
		System.out.println(this.getClass().toString() + " destroyed.");
	}

	public void carregarLivroPelaId() {
		this.setEntity(daoEntity.searchByID(this.getLivro().getId()));

		reenableAllAutorItems();
		for (Autor autor : this.getLivro().getAutores()) {
			disableAutorItem(autor);
		}
	}

	public void gravarAutorDoLivro() {
		if (this.autorId == null)
			return;
		
		Autor autor = daoAutor.searchByID(this.autorId);
		this.getLivro().addAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
		
		disableAutorItem(autor);
		
		this.autorId = null;
	}

	public void removerAutorDoLivro(Autor autor) {
		this.getLivro().removeAutor(autor);
		enableAutorItem(autor);
	}
	
	public void update(Livro livro) {
		this.setLivro(livro);
		
		reenableAllAutorItems();
		for (Autor autor : this.getLivro().getAutores()) {
			disableAutorItem(autor);
		}
		
		tabIndex = 1;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.getLivro().getTitulo());

		if (getLivro().getAutores().isEmpty()) {
			MessageUtils.alert("autor", "Livro deve ter pelo menos um Autor.");
			return;
		}

		boolean success = this.baseCreateOrUpdate();

		if (success)
			MessageUtils.success(entityName, "Livro gravado com sucesso.");
		else
			MessageUtils.error(entityName, "Não foi possível gravar o livro.");
	}

	public void remover(Livro livro) {
		boolean success = this.baseDelete(livro);

		if (success)
			MessageUtils.success(entityName, "Livro removido com sucesso.");
		else
			MessageUtils.error(entityName, "Não foi possível remover o livro.");
	}

	public String formAutor() {
		System.out.println("Chamada do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	@SuppressWarnings("unused")
	public void comecaComISBN13(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("978")) {
			throw new ValidatorException(new FacesMessage("ISBN-13 deveria começar com 978"));
		}

	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return getEntity();
	}

	public void setLivro(Livro livro) {
		this.setEntity(livro);
	}

	public List<Autor> getAutoresDoLivro() {
		return this.getEntity().getAutores();
	}

	public List<SelectItem> getAutores() {
		return autores;
	}

	public void setAutores(List<SelectItem> autores) {
		this.autores = autores;
	}
}
