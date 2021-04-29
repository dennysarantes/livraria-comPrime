package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private List<Autor> autores;
		
	public Autor getAutor() {
		return autor;
	}
	
	private Integer autorId;
	
	
	
	public Integer getAutorId() {
		return autorId;
	}





	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}


	public void carregarAutorPelaId() {
//		if (!(this.autorId == null)) {
			this.autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);	
//		}
	}
	
	public List<Autor> getAutores() {
		
		return new DAO<Autor>(Autor.class).listaTodos();
	}





	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		boolean empty = this.autor.getNome().isEmpty();
		
		if (this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		
		
//		if (!empty) {
//		new DAO<Autor>(Autor.class).adiciona(this.autor);
//		this.autor = new Autor();
//		} else {
//				System.out.println("Valor nulo");
//		}
//		
		
		
		
		return "autor?faces-redirect=true";
		
		
		//this.autor.setNome(null);
	}
	
	public void excluir(Autor autor) {
		System.out.println("Excluindo autor");
		try {
			new DAO<Autor>(Autor.class).remove(autor);
		} catch (Exception e) {
			System.out.println("Erro ao tentar remover autor");
		}
		
	}
	public void carregaAutor(Autor autor) {
		System.out.println("Editando autor");
		
		this.autor = autor;
		
	}
}
