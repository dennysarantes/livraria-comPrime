package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.modelo.Tema;

@ManagedBean
@SessionScoped
public class TemaBean {

	//private String tema ="saga";
	private Tema tema = new Tema();
	private List<String> temas;

	
	public Tema getTema() {
		
		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"); 
		
		if (object == null) {
			tema.setNome("saga");
			return tema;
		}
		tema.setNome("saga");
		return tema;
	}


	public void setTema(Tema tema) {
		this.tema = tema;
	}



	public List<String> getTemas() {
		
		temas.add("a1");
		temas.add("teste");
			
		return temas;
	}



	public void setTemas(List<String> temas) {
		this.temas = temas;
	}


	public List<String> pegarListaTemas(){
		
		List<String> listaTemas = null;
		listaTemas.add("a1");
		listaTemas.add("teste");
		//listaTemas.add("saga");
				
		return listaTemas;
	}
	
	public void defineTema() {
		System.out.println("definindo tema..." );
		
		
		
	}
	
	
}
