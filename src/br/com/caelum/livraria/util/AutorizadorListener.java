package br.com.caelum.livraria.util;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.bean.UsuarioBean;
import br.com.caelum.livraria.modelo.Usuario;


public class AutorizadorListener implements PhaseListener{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioLogado;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			usuarioLogado = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			try {
				if (usuarioLogado.getEmail().isEmpty()) {
					try {redirect(event);}catch (IOException e) {}
				}
			} catch (Exception e) {redirect(event);}
		} catch (Exception e) {} 
	}
	

	@Override
	public void beforePhase(PhaseEvent event) {}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public void redirect (PhaseEvent event) throws IOException {
		
		String paginaAtual = event.getFacesContext().getViewRoot().getViewId();
		
		if (!paginaAtual.contains("login")) {
			event.getFacesContext().getExternalContext().redirect("login.xhtml");
		}
		
		
	}
	
}
