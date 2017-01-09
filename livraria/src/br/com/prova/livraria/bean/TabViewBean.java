package br.com.prova.livraria.bean;

import br.com.prova.livraria.modelo.BaseEntity;

public abstract class TabViewBean<T extends BaseEntity> extends CRUDBean<T> {

	protected int tabIndex;

	public int getTabIndex() {
		return this.tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
}
