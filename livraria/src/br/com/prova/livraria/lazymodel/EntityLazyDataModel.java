package br.com.prova.livraria.lazymodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.prova.livraria.modelo.BaseEntity;

public class EntityLazyDataModel<T extends BaseEntity> extends LazyDataModel<T> {

	private List<T> datasource;
	private Class<T> classType;

	public EntityLazyDataModel(List<T> datasource, Class<T> classType) {
		this.datasource = datasource;
		this.classType = classType;
	}

	@Override
	public T getRowData(String rowKey) {
		for (T entity : datasource)
			if (entity.getId().equals(rowKey) && classType.isInstance(entity))
				return entity;

		return null;
	}

	@Override
	public Object getRowKey(T entity) {
		return entity.getId();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> data = new ArrayList<T>();

		// filter
		for (T entity : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						Field field = entity.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String fieldValue = String.valueOf(field.get(entity));
						System.out.println("Filter Property: " + filterProperty);
						System.out.println("Filter Value: " + filterValue);
						System.out.println("Field Value: " + fieldValue);

						if (filterValue == null || fieldValue.toLowerCase().startsWith(filterValue.toString().toLowerCase())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(entity);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new LazySorter<T>(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}
}
