package br.com.prova.livraria.dao;

import br.com.prova.livraria.modelo.BaseEntity;

public abstract class EntityFactory {

	public static <T extends BaseEntity> T createNewInstance(Class<T> entityClass) {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
