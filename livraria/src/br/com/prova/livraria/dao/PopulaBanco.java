package br.com.prova.livraria.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.modelo.Usuario;

public abstract class PopulaBanco {

	public static List<Usuario> fillListaUsuarios() {
		final List<Usuario> usuarios = new ArrayList<Usuario>();

		Usuario adm = new Usuario();
		adm.setId(1);
		adm.setEmail("avanade@avanade.com");
		adm.setSenha("1234");
		adm.setAdmin(true);
		usuarios.add(adm);

		Usuario leo = new Usuario();
		leo.setId(2);
		leo.setEmail("leo@leo.com");
		leo.setSenha("1234");
		leo.setAdmin(false);
		usuarios.add(leo);

		return usuarios;
	}

	public static List<Autor> fillListaAutores() {
		int id = 0;
		
		// Do not mess with this order.
		// Não bagunce essa ordem.
		Autor assis = geraAutor("Machado de Assis", "machado@machado.com", ++id);
		Autor amado = geraAutor("Jorge Amado", "jorge@jorge.com", ++id);
		Autor coelho = geraAutor("Paulo Coelho", "paulo@paulo.com", ++id);
		Autor lobato = geraAutor("Monteiro Lobato", "monteiro@monteiro.com", ++id);
		Autor roth = geraAutor("Patrick Rothfuss", "patrick@patrick.com", ++id);
		Autor martin = geraAutor("George R. R. Martin", "george@george.com", ++id);
		Autor riordan = geraAutor("Rick Riordan", "rick@rick.com", ++id);
		Autor yusagi = geraAutor("Aneko Yusagi", "aneko@aneko.com", ++id);

		final List<Autor> autores = new ArrayList<Autor>();

		autores.add(assis);
		autores.add(amado);
		autores.add(coelho);
		autores.add(lobato);
		autores.add(roth);
		autores.add(martin);
		autores.add(riordan);
		autores.add(yusagi);

		return autores;
	}

	/**
	 * Method must be called <b>after</b> {@link #fillListaAutores()}. If can't find the entity {@link Autor} sought in the database, doesn't add the books and
	 * alert the user. </br> Método deve ser chamado <b>após</b> {@link #fillListaAutores()}. Se não encontrar a entidade {@link Autor} buscada na base de
	 * dados, não adiciona os livros na lista e avisa o usuário.
	 */
	@SuppressWarnings("unused")
	public static List<Livro> fillListaLivros() {
		BaseDao<Autor> dao = new BaseDao<Autor>(Autor.class);

		Autor assis = dao.searchByField(Autor.class, "nome", "Machado de Assis");
		Autor amado = dao.searchByField(Autor.class, "nome", "Jorge Amado");
		Autor coelho = dao.searchByField(Autor.class, "nome", "Paulo Coelho");
		Autor lobato = dao.searchByField(Autor.class, "nome", "Monteiro Lobato");
		Autor roth = dao.searchByField(Autor.class, "nome", "Patrick Rothfuss");
		Autor martin = dao.searchByField(Autor.class, "nome", "George R. R. Martin");
		Autor riordan = dao.searchByField(Autor.class, "nome", "Rick Riordan");
		Autor yusagi = dao.searchByField(Autor.class, "nome", "Aneko Yusagi");

		int id = 0;

		final List<Livro> livros = new ArrayList<Livro>();
		if (assis != null) {
			Livro casmurro = geraLivro("978-8-52-504464-8", "Dom Casmurro", "01/01/1899", new BigDecimal(24.90), assis, ++id);
			Livro memorias = geraLivro("978-8-50-815415-9", "Memorias Postumas de Bras Cubas", "01/01/1881", new BigDecimal(19.90), assis,
					++id);
			Livro quincas = geraLivro("978-8-50-804084-1", "Quincas Borba", "01/01/1891", new BigDecimal(16.90), assis, ++id);

			livros.add(casmurro);
			livros.add(memorias);
			livros.add(quincas);
		}

		if (coelho != null) {
			Livro alquimista = geraLivro("978-8-57-542758-3", "O Alquimista", "01/01/1988", new BigDecimal(19.90), coelho, ++id);
			Livro brida = geraLivro("978-8-50-567258-7", "Brida", "01/01/1990", new BigDecimal(12.90), coelho, ++id);
			Livro valkirias = geraLivro("978-8-52-812458-8", "As Valkirias", "01/01/1992", new BigDecimal(29.90), coelho, ++id);
			Livro mago = geraLivro("978-8-51-892238-9", "O Diario de um Mago", "01/01/1987", new BigDecimal(9.90), coelho, ++id);
			livros.add(alquimista);
			livros.add(brida);
			livros.add(valkirias);
			livros.add(mago);
		}

		if (amado != null) {
			Livro capitaes = geraLivro("978-8-50-831169-1", "Capitaes da Areia", "01/01/1937", new BigDecimal(6.90), amado, ++id);
			Livro flor = geraLivro("978-8-53-592569-9", "Dona Flor e Seus Dois Maridos", "01/01/1966", new BigDecimal(18.90), amado, ++id);
			livros.add(capitaes);
			livros.add(flor);
		}
		
		if (roth != null) {
			Livro vento = geraLivro("978-8-59-929649-3", "O Nome do Vento", "01/01/2009", new BigDecimal(24.90), roth, ++id);
			Livro sabio = geraLivro("978-8-58-041032-7", "O Temor do Sábio", "01/01/2011", new BigDecimal(26.89), roth, ++id);
			Livro silencio = geraLivro("978-8-58-041353-3", "A Música do Silêncio", "01/01/2015", new BigDecimal(9.90), roth, ++id);
			livros.add(vento);
			livros.add(sabio);
			livros.add(silencio);
		}
		
		if (martin != null) {
			Livro guerra = geraLivro("978-8-58-044626-5", "A Guerra dos Tronos", "01/01/2010", new BigDecimal(19.99), martin, ++id);
			Livro furia = geraLivro("978-8-58-044027-0", "A Fúria dos Reis", "01/01/2011", new BigDecimal(49.90), martin, ++id);
			Livro tormenta = geraLivro("978-8-58-044262-5", "A Tormenta de Espadas", "01/01/2011", new BigDecimal(17.52), martin, ++id);
			Livro festim = geraLivro("978-8-58-044376-9", "O Festim dos Corvos", "01/01/2011", new BigDecimal(21.76), martin, ++id);
			Livro danca = geraLivro("978-8-58-044481-0", "A Dança dos Dragões", "01/01/2012", new BigDecimal(38.20), martin, ++id);
			livros.add(guerra);
			livros.add(furia);
			livros.add(tormenta);
			livros.add(festim);
			livros.add(danca);
		}
		
		if (riordan != null) {
			Livro raios = geraLivro("978-8-59-807839-7", "O Ladrão de Raios", "01/01/2008", new BigDecimal(19.99), riordan, ++id);
			Livro monstros = geraLivro("978-8-59-807844-1", "O Mar de Monstros", "01/01/2009", new BigDecimal(26.76), riordan, ++id);
			Livro tita = geraLivro("978-8-59-807858-8", "A Maldição do Titã", "01/01/2009", new BigDecimal(29.90), riordan, ++id);
			Livro labirinto = geraLivro("978-8-59-807870-0", "A Batalha do Labirinto", "01/01/2010", new BigDecimal(9.90), riordan, ++id);
			Livro olimpiano = geraLivro("978-8-59-807890-8", "O Último Olimpiano", "01/01/2010", new BigDecimal(26.90), riordan, ++id);
			livros.add(raios);
			livros.add(monstros);
			livros.add(tita);
			livros.add(labirinto);
			livros.add(olimpiano);
		}
		
		if (yusagi != null) {
			Livro shieldA =  geraLivro("978-1-93-554872-0", "The Rising of the Shield Hero - Vol. 1", "01/01/2015", new BigDecimal(45.12), yusagi, ++id);
			Livro shieldB =  geraLivro("978-1-93-554878-2", "The Rising of the Shield Hero - Vol. 2", "01/01/2015", new BigDecimal(45.12), yusagi, ++id);
			Livro shieldC =  geraLivro("978-1-93-554866-9", "The Rising of the Shield Hero - Vol. 3", "01/01/2016", new BigDecimal(47.80), yusagi, ++id);
			livros.add(shieldA);
			livros.add(shieldB);
			livros.add(shieldC);
		}
		

		return livros;
	}

	// public static void fillManyToManyAutor(List<Livro> livros, List<Autor> autores) {
	// for (Autor autor : autores) {
	// for (Livro livro : livros) {
	// for (Autor a : livro.getAutores()) {
	// if (autor.getId().equals(a.getId())) {
	// autor.addLivro(livro);
	// break;
	// }
	// }
	// }
	// }
	// }

	private static Autor geraAutor(String nome, String email, int id) {
		Autor autor = new Autor();
		autor.setNome(nome);
		autor.setEmail(email);
		autor.setId(id);
		return autor;
	}

	private static Livro geraLivro(String isbn, String titulo, String data, BigDecimal preco, Autor autor, int id) {
		Livro livro = new Livro();
		livro.setIsbn(isbn);
		livro.setTitulo(titulo);
		livro.setDataLancamento(parseData(data));
		livro.setPreco(preco);
		livro.addAutor(autor);
		livro.setId(id);
		return livro;
	}

	private static Calendar parseData(String data) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
