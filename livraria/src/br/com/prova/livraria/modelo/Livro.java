package br.com.prova.livraria.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Livro extends BaseEntity {

	private String titulo;
	private String isbn;
	private BigDecimal preco;
	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autor> autores = new ArrayList<Autor>();

	@SuppressWarnings("unchecked")
	@Override
	public Livro createCopy() {
		Livro livro = new Livro();

		livro.id = this.id;
		livro.titulo = this.titulo;
		livro.isbn = this.isbn;
		livro.preco = this.preco;
		livro.dataLancamento = this.dataLancamento;

		livro.autores.clear();
		for (Autor autor : this.autores)
			livro.autores.add(autor.createCopy());

		return livro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void addAutor(Autor autor) {
		this.autores.add(autor);
	}

	public void removeAutor(Autor autor) {
		this.autores.remove(autor);
	}

}