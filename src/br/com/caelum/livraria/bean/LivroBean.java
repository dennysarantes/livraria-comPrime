package br.com.caelum.livraria.bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.PrimeFaces;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private List<Livro> livros;
	// private List<Autor> autores = new DAO<Autor>(Autor.class).listaTodos();
	private Integer autorId;
	private Autor autor = new Autor();
	private Usuario usuarioLogado = new Usuario();
	private List<String> categorias;
	private List<Livro> livrosFiltrados;
	
	
	public List<Livro> getLivrosFiltrados() {
		return livrosFiltrados;
	}

	public void setLivrosFiltrados(List<Livro> livrosFiltrados) {
		this.livrosFiltrados = livrosFiltrados;
	}

	public List<String> getCategorias() {
		List<String> categorias = new DAO<String>(String.class).buscaCategorias();		
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livros == null) {
			this.livros = dao.listaTodos();
		}
		
		return livros; 
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

	
	
	public void setLivro(Livro livro) {
		this.livro = livro;
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
		System.out.println("ISBN e preço do livro a ser gravado: " + this.livro.getIsbn() + " " + this.livro.getPreco());
		
		try {
			usuarioLogado = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			livro.setResponsavelCadastro(usuarioLogado.getNome());
			livro.setDataCadastro();
		} catch (Exception e) {
			System.out.println("WARN [" + LocalDateTime.now() + "] erro ao obter usuário");
		}
		
		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("O livro deve conter ao menos um autor"));
			return null;
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

	public void excluirAutor(Autor autor) {
		List<Autor> autoresDoLivro = livro.getAutores();

		for (int i = 0; i < autoresDoLivro.size(); i++) {
			if (autoresDoLivro.get(i).getNome().equals(autor.getNome())) {
				livro.excluirAutor(autoresDoLivro.get(i));
				//System.out.println("Autor " + autoresDoLivro.get(i).getNome() + " excluído com sucesso!");
			}
		}
	}
	
	
	public void excluirAutorOld() {
		
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
		
		System.out.println("Livro a ser removido " + livro.getTitulo());
		
		new DAO<Livro>(Livro.class).remove(livro);
		
	}
	public void carregarLivro(Livro livro) {
		this.livro = livro;
		
	}
	
	public void excluirLivroPergunta() {
		System.out.println("Entrou");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "confirmado", "Livro excluído");
        FacesContext.getCurrentInstance().addMessage(null, message);
		
		
		//PrimeFaces.current().executeScript("check()");
	}
	
	public void editarLivro() {
		
	}
}
