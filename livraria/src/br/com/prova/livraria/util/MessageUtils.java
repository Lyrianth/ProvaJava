package br.com.prova.livraria.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class MessageUtils {

	public static void alert(String clientID, String summary) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(summary));
	}

	public static void alert(String clientID, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(summary, detail));
	}

	public static void customMsg(String clientID, String summary, String detail, Severity severityInfo) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(severityInfo, summary, detail));
	}

	public static void success(String clientID, String summary) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
	}

	public static void error(String clientID, String summary) {
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
	}

}
