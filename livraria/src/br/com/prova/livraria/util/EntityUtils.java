package br.com.prova.livraria.util;

import java.util.List;

import br.com.prova.livraria.modelo.BaseEntity;

public abstract class EntityUtils {

	public static <T extends BaseEntity> T getById(List<T> entities, Class<T> entityClass, int entityId) {
		for (T entity : entities)
			if (entity.getId() == entityId && entityClass.isInstance(entity))
				return entity;
		
		return null;
	}
}
