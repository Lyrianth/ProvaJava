package br.com.prova.livraria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.prova.livraria.dao.BaseDao;
import br.com.prova.livraria.lazymodel.EntityLazyDataModel;
import br.com.prova.livraria.modelo.Usuario;
import br.com.prova.livraria.util.MessageUtils;

@ManagedBean
@ViewScoped
public class UsuarioViewBean extends CRUDBean<Usuario> implements Serializable {

	@PostConstruct
	public void init() {
		daoEntity = new BaseDao<Usuario>(Usuario.class);
		lazyModel = new EntityLazyDataModel<Usuario>(daoEntity.listAll(), Usuario.class);
		setEntity(new Usuario());
		entityName = "Usuario";
	}

	public void gravar() {
		boolean success = this.baseCreateOrUpdate();

		if (success)
			MessageUtils.success(entityName, "Usuário gravado com sucesso.");
		else
			MessageUtils.error(entityName, "Não foi possível gravar o Usuário.");
	}

	public void remover() {
		remover(getEntity());
	}

	public void remover(Usuario usuario) {
		boolean success = this.baseDelete(usuario);

		if (success)
			MessageUtils.success(entityName, "Usuário removido com sucesso.");
		else
			MessageUtils.error(entityName, "Não foi possível remover o Usuário.");
	}

	@Override
	public boolean createDisabled() {
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
		if (usuarioLogado.isAdmin())
			return false;
		return true;
	}

	@Override
	public boolean updateDisabled() {
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
		if (usuarioLogado.isAdmin())
			return false;
		return true;
	}

	@Override
	public boolean deleteDisabled() {
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
		if (usuarioLogado.isAdmin())
			return false;
		return true;
	}
}
