package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private List<Livro> livros;
	// private List<Autor> autores = new DAO<Autor>(Autor.class).listaTodos();
	private Integer autorId;

	
	
	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Integer getAutorId() {
		System.out.println("Id do livro: " + autorId);
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		//livro.setPreco(1.0);
		return livro;
	}

	public List<Autor> getAutores() {

		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public String gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("O livro deve conter ao menos um autor"));
		}else {
			if (this.livro.getId() == null) {
				try {
					new DAO<Livro>(Livro.class).adiciona(this.livro);
					this.livro = new Livro();
				} catch (Exception e) {
					System.out.println("Problema ao persistir o livro...");
				}
			}else
					
			try {
				new DAO<Livro>(Livro.class).atualiza(livro);
				this.livro = new Livro();
			} catch (Exception e) {
				System.out.println("Problema ao persistir o livro...");
			}
		}

		return "livro?faces-redirect=true";
	
	}

	public void gravarAutor() {
		System.out.println("Gravando autor...");
		System.out.println("Id do autor selecionado: " + this.autorId);
		
		
		Autor autorSelecionado = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);

		System.out.println();

		List<Autor> autores = (List<Autor>) livro.getAutores();

		if (autores.isEmpty()) {
			this.livro.adicionaAutor(autorSelecionado);
		} else {
			System.out.println("o livro já possui autores cadastrados...");
			this.livro.adicionaAutor(autorSelecionado);
		}
		
		
		
//		if (!autores.isEmpty()) {
//			
//			for (Autor autor : autores) {
//				if (autor.getId() == autorSelecionado.getId()) {
//					System.out.println("Esse autor já foi selecionado...");
//				} else {
//					System.out.println("Autor adicionado...");
//					this.livro.adicionaAutor(autorSelecionado);
//				}
//			}
//		}

	}

	public List<Autor> getautoresDoLivro() {
		List<Autor> autoresDoLivro = livro.getAutores();
		return autoresDoLivro;
	}

	public void excluirAutor() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String nomeAutorParaExcluir = params.get("autorParaExcluir");

		System.out.println("Nome do autor selecionado para exclusão: " + nomeAutorParaExcluir);

		List<Autor> autoresDoLivro = livro.getAutores();

		for (int i = 0; i < autoresDoLivro.size(); i++) {
			if (autoresDoLivro.get(i).getNome().equals(nomeAutorParaExcluir)) {
				livro.excluirAutor(autoresDoLivro.get(i));
				//System.out.println("Autor " + autoresDoLivro.get(i).getNome() + " excluído com sucesso!");
			}
		}

	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent c, Object isbn) throws ValidatorException {
		String isbnASerVerificado = isbn.toString();
		
		if(!isbnASerVerificado.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("O ISBN deve começar com o dígito 1"));
		}
		
	}
	
	public String formAutor() {
		System.out.println("Chamando form autor");
		return "autor?faces-redirect=true";
	}
	
	public void excluirLivro(Livro livro) {
		
		new DAO<Livro>(Livro.class).remove(livro);
		
	}
	public void carregarLivro(Livro livro) {
		this.livro = livro;
		
	}
	
	public void editarLivro() {
		
	}
}
