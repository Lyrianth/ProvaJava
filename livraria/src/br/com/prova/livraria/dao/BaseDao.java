package br.com.prova.livraria.dao;

import java.lang.reflect.Field;
import java.util.List;

import br.com.prova.livraria.bean.LivrariaBean;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.BaseEntity;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.modelo.Usuario;

public class BaseDao<T extends BaseEntity> {

	private final List<T> list;
	private int lastId;

	@SuppressWarnings("unchecked")
	public BaseDao(Class<T> entityClass) {
		if (entityClass == Usuario.class)
			this.list = (List<T>) LivrariaBean.ListaUsuarios;
		else if (entityClass == Autor.class)
			this.list = (List<T>) LivrariaBean.ListaAutores;
		else if (entityClass == Livro.class)
			this.list = (List<T>) LivrariaBean.ListaLivros;
		else
			this.list = null;

		if (this.list != null && this.list.size() > 0) {
			lastId = this.list.get(this.list.size() - 1).getId();
		}
	}

	public boolean persist(T entity) {
		entity.setId(++lastId);
		return list.add(entity);
	}

	public boolean update(T entity) {
		for (int i = 0; i < list.size(); i++) {
			T e = list.get(i);
			if (entity.getId().equals(e.getId())) {
				list.set(i, entity);
				return true;
			}
		}

		return false;
	}

	public boolean delete(T entity) {
		return list.remove(entity);
	}

	public void drop() {
		list.clear();
	}

	public List<T> listAll() {
		return list;
	}

	public T searchByID(Integer id) {
		for (T entity : list)
			if (id == entity.getId())
				return entity;

		return null;
	}

	// Won't be treated. This case is satisfied with a simple query to the database. Here, reflection needs to be used.
	// Não será tratado. Esse caso é satisfeito com uma simples query para a base de dados. Aqui, reflection se faz necessário.
	public T searchByField(Class<T> entityClass, String fieldName, String query) {
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			for (T entity : list) {
				try {
					if (fieldName.equals(field.getName())) {
						String value = field.get(entity).toString();
						if (query.equals(value))
							return entity;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
