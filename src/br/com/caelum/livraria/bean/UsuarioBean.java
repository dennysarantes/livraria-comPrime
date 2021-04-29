package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private Integer usuarioId;
		
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Usuario carregarAutorPelaId(Integer id){
//		if (!(this.autorId == null)) {
			return new DAO<Usuario>(Usuario.class).buscaPorId(this.usuarioId);	
//		}
	}

	 
	
	public String login() {
		
		Usuario usuarioLogin = verificaCredenciaisInformadas();
		
		System.out.println("Nome usuario: " + usuarioLogin.getEmail());
		
		if (usuarioLogin.getId() == null) {
			//Login mal sucedido
			adicionarMsgErroLogin();
			return null;
		}else {
			//Login com sucesso
			adicionarUsuarioLogado(usuarioLogin);
			
			return "livro?faces-redirect=true";
		}
		
		//return null;
		
	}

	private Usuario verificaCredenciaisInformadas() {
		Usuario usuarioLogin = new DAO<Usuario>(Usuario.class).buscaPorNomeSenha(this.usuario.getEmail(),this.usuario.getSenha());
		return usuarioLogin;
	}

	private void adicionarUsuarioLogado(Usuario usuarioLogin) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioLogin);
	}

	private void adicionarMsgErroLogin() {
		FacesContext.getCurrentInstance().addMessage("login", new FacesMessage("Usuário ou senha inválidos"));
	}
	
	public String logout() {
		System.out.println("Chamando logout");
		Usuario usuarioLogout = new Usuario();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().replace("usuario", usuarioLogout);
		return "login?faces-redirect=true";
	}
}
