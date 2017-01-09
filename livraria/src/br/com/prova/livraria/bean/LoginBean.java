package br.com.prova.livraria.bean;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import br.com.prova.livraria.dao.UsuarioDao;
import br.com.prova.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario;
	private final List<String> emails = new ArrayList<String>();
	private String cookieName;

	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		cookieName = "validEmails";

		Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(cookieName);
		if (cookie != null && !cookie.getValue().equals("")) {
			String decodedValue = decodeValueUTF8(cookie.getValue());
			String[] emails = decodedValue.split(";");
			for (String item : emails)
				this.emails.add(item);
		}
		
	}

	public String efetuaLogin() {

		// PopulaBanco pb = new PopulaBanco();
		// pb.fillLista();

		System.out.println("fazendo login do usuario " + this.usuario.getEmail());

		FacesContext context = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDao().exists(this.usuario);
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);

			// Get cookie
			Cookie cookie = (Cookie) context.getExternalContext().getRequestCookieMap().get(cookieName);
			String decodedValue = "";

			boolean emailExists = false;
			// The second condition is more of a fail-safe. It's not needed because it will never be saved as blank.
			if (cookie != null && !cookie.getValue().equals("")) {
				decodedValue = decodeValueUTF8(cookie.getValue());
				System.out.println("Decoded value: " + decodedValue);
				String[] emails = decodedValue.split(";");

				for (String email : emails) {
					if (email.equalsIgnoreCase(this.usuario.getEmail())) {
						emailExists = true;
						break;
					}
				}
			} else if (cookie == null) {
				cookie = new Cookie(cookieName, "");
			}

			if (!emailExists) {
				String value = (decodedValue.equals("") ? "" : decodedValue + ";") + this.usuario.getEmail();
				cookie.setValue(encodeValueUTF8(value));
				System.out.println("Encoded value: " + cookie.getValue());
				cookie.setMaxAge(31536000);

				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.addCookie(cookie);
			}

			return "livro?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usu�rio n�o encontrado"));

		// pb.dropLista();

		return "login?faces-redirect=true";
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

	public ArrayList<String> completeText(String query) {
		ArrayList<String> results = new ArrayList<String>();
		for (String email : emails)
			if (email.startsWith(query))
				results.add(email);

		return results;
	}

	public String encodeValueUTF8(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			System.out.println("Encode exception: " + e);
		}
		return "";
	}

	public String decodeValueUTF8(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			System.out.println("Decode exception: " + e);
		}
		return "";
	}
}
