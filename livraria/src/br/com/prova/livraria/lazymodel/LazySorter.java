package br.com.prova.livraria.lazymodel;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.prova.livraria.modelo.BaseEntity;

public class LazySorter<T extends BaseEntity> implements Comparator<T> {

	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@Override
	public int compare(T t1, T t2) {
		try {
			Field field1 = t1.getClass().getDeclaredField(this.sortField);
			Field field2 = t2.getClass().getDeclaredField(this.sortField);
			field1.setAccessible(true);
			field2.setAccessible(true);
			Object value1 = field1.get(t1);
			Object value2 = field2.get(t2);

			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}