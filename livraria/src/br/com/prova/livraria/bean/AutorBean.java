package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.prova.livraria.dao.BaseDao;
import br.com.prova.livraria.lazymodel.EntityLazyDataModel;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.util.MessageUtils;

@ManagedBean
@ViewScoped
public class AutorBean extends TabViewBean<Autor> implements Serializable {

	private Integer autorId;
	private BaseDao<Livro> daoLivro;

	@PostConstruct
	public void init() {
		daoEntity = new BaseDao<Autor>(Autor.class);
		daoLivro = new BaseDao<Livro>(Livro.class);
		lazyModel = new EntityLazyDataModel<Autor>(daoEntity.listAll(), Autor.class);
		setEntity(new Autor());
		entityName = "Autor";
	}

	public void loadByID() {
		this.setEntity(daoEntity.searchByID(autorId));
	}
	
	public void update(Autor autor) {
		this.setAutor(autor);
		
		tabIndex = 1;
	}

	public void gravar() {
		boolean success = this.baseCreateOrUpdate();

		if (success)
			MessageUtils.success(entityName, "Autor gravado com sucesso.");
		else
			MessageUtils.error(entityName, "Não foi possível gravar o Autor.");
	}

	public void remover() {
		remover(getEntity());
	}

	public void remover(Autor autor) {
		boolean success = this.baseDelete(autor);

		if (success) {
			for (Livro livro : daoLivro.listAll())
				livro.removeAutor(autor);
			MessageUtils.success(entityName, "Autor removido com sucesso.");
		} else {
			MessageUtils.error(entityName, "Não foi possível remover o Autor.");
		}
	}

	public int getQntdLivros(Autor autor) {
		int qntd = 0;
		for (Livro livro : daoLivro.listAll()) {
			for (Autor a : livro.getAutores()) {
				if (autor.getId().equals(a.getId())) {
					qntd++;
					break;
				}
			}
		}
		return qntd;
	}

	/* Validators */
	@SuppressWarnings("unused")
	public void checkAutorNameExists(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();

		for (Autor autor : daoEntity.listAll()) {
			if (autor.getNome().equalsIgnoreCase(valor)) {
				// If new or ID is not the same, then we have two unique Autores with the same value.
				// Se o Autor está sendo criado, ou se já existe mas os IDs são diferentes, então temos dois Autores únicos com o mesmo valor.
				if (this.getEntity().isNew() || !this.getEntity().getId().equals(autor.getId())) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Já existe um autor com este nome cadastrado."));
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public void checkEmail(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();

		// Test email pattern
		// Verifica os padrões de email
		Pattern r = Pattern.compile(".+@.+");
		Matcher m = r.matcher(valor);
		if (!m.matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email inválido."));
		}

		for (Autor autor : daoEntity.listAll()) {
			if (autor.getEmail().equalsIgnoreCase(valor)) {
				// If new or ID is not the same, then we have two unique Autores with the same value.
				// Se o Autor está sendo criado, ou se já existe mas os IDs são diferentes, então temos dois Autores únicos com o mesmo valor.
				if (this.getEntity().isNew() || !this.getEntity().getId().equals(autor.getId())) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Já existe um autor com este email cadastrado."));
				}
			}
		}
	}

	/* Action Permitters */
	@Override
	public boolean createDisabled() {
		// TODO Auto-generated method stub
		return super.createDisabled();
	}

	@Override
	public boolean updateDisabled() {
		// TODO Auto-generated method stub
		return super.updateDisabled();
	}

	@Override
	public boolean deleteDisabled() {
		// TODO Auto-generated method stub
		return super.deleteDisabled();
	}

	/* Getters and Setters */
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return getEntity();
	}

	public void setAutor(Autor autor) {
		this.setEntity(autor);
	}

}
