package br.com.prova.livraria.bean;

import br.com.prova.livraria.dao.BaseDao;
import br.com.prova.livraria.lazymodel.EntityLazyDataModel;
import br.com.prova.livraria.modelo.BaseEntity;

public abstract class CRUDBean<T extends BaseEntity> {
	
	protected EntityLazyDataModel<T> lazyModel;
	private T entity;
	
	protected String entityName;
	protected BaseDao<T> daoEntity;

	public boolean baseCreateOrUpdate() {
		if (getEntity().isNew()) {
			System.out.println("Criando " + entityName);
			return daoEntity.persist(getEntity());
		} else {
			System.out.println("Atualizando " + entityName);
			return daoEntity.update(getEntity());
		}
	}

	public boolean baseDelete() {
		return baseDelete(this.getEntity());
	}

	public boolean baseDelete(T entity) {
		System.out.println("Removendo " + entityName);
		return daoEntity.delete(entity);
	}

	public boolean createDisabled() {
		return false;
	}

	public boolean updateDisabled() {
		return false;
	}

	public boolean deleteDisabled() {
		return false;
	}

	public EntityLazyDataModel<T> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(EntityLazyDataModel<T> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public T getEntity() {
		return entity;
	}
	
	protected void setEntity(T entity) {
		this.entity = entity.createCopy();
	}

}
