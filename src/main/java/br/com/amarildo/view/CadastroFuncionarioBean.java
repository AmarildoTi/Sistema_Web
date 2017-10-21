package br.com.amarildo.view;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.amarildo.controller.FuncionarioController;
import br.com.amarildo.controller.LogradouroController;
import br.com.amarildo.controller.PessoaController;
import br.com.amarildo.controller.UsuarioController;
import br.com.amarildo.model.FuncionarioModel;
import br.com.amarildo.model.LogradouroModel;
import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.model.UsuarioModel;
import br.com.amarildo.repository.FuncionarioRepository;
import br.com.amarildo.repository.LogradouroRepository;
import br.com.amarildo.util.NegocioException;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteProxy;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;

@Named
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncionarioRepository funcionarioRepository;

	@Inject
	private LogradouroRepository logradouroRepository;
	
	@Inject
	private FuncionarioController funcionarioController;

	@Inject
	private LogradouroController logradouroController;
	
	@Inject
	private PessoaController pessoaController;

	@Inject
	private UsuarioController usuarioController;

	@Inject
	private FuncionarioModel funcionarioModel;

	@Inject
	private LogradouroModel logradouroModel;

	@Inject
	private PessoaModel pessoaModel;

	@Inject
	private UsuarioModel usuarioModel;
	
	@Inject
	private PhotoCamBean salvarImagem;
	
	private UploadedFile file;
	
	boolean faz = false;
	
	@PostConstruct
	public void inicializar() {
        /*
		utilizamos um método público e sem
		argumentos anotado com a anotação @PostConstruct, que é chamado pela CDI
		sempre que o objeto está pronto, ou seja, já teve todas suas dependências satisfeitas.
		Verifica se as entity PessoaModel,LogradouroModel,FuncionárioModel e UsuarioModel não está retornando null
		*/
		System.out.println("Passou Aqui no pessoaModel      !!! "+pessoaModel == null);
		System.out.println("Passou Aqui no logradouroModel  !!! "+logradouroModel == null);
		System.out.println("Passou Aqui no funcionarioModel !!! "+funcionarioModel == null );
		System.out.println("Passou Aqui no UsuarioModel     !!! "+usuarioModel == null );
	}
	
	//Salva Registro via Input
	public void salvar() {
		
		try {
			
			//Informa que o Cadastro foi Via Input e/ou Via Xml
			if (pessoaModel.getOrigemCadastro() == "X"){
				faz = false;
				pessoaModel.getOrigemCadastro();
			}else{
				faz = true; 
        		pessoaModel.setOrigemCadastro("I");
			}

			//Salvar Insert Dados de Usuário na Tabela Usuário e o Id do Objeto Usuário para Tabela Funcionário Id_Usuario
			usuarioModel = this.usuarioController.salvar(this.usuarioModel);
			this.funcionarioModel.setUsuarioModel(usuarioModel);
			this.usuarioModel = new UsuarioModel();
			
			//Salvar Insert Id do Objeto Usuário que está cadastrando o Funcionário para Tabela Pessoa Id_Usuario
			usuarioModel = this.usuarioController.addId(this.usuarioController.getCodigoId());
			this.pessoaModel.setUsuarioModel(usuarioModel);			
			this.usuarioModel = new UsuarioModel();

			//Salvar Insert Dados de Funcionário na Tabela Funcionário e o Id do Objeto Funcionário para Tabela Pessoa Id_Funcionario
			funcionarioModel = this.funcionarioController.salvar(this.funcionarioModel);
			this.pessoaModel.setFuncionarioModel(funcionarioModel);
			this.funcionarioModel = new FuncionarioModel();

			//Salvar Insert Dados de Endereco na Tabela Logradouro e o Id do Objeto Logradouro para Tabela Pessoa Id_Logradouro
			logradouroModel = this.logradouroController.salvar(this.logradouroModel);
			this.pessoaModel.setLogradouroModel(logradouroModel);
			this.logradouroModel = new LogradouroModel();
			
			//Salvar Insert Dados Tabela Pessoa e Foto que terá o Nome do Id da Pessoa  
			pessoaModel = this.pessoaController.salvar(this.pessoaModel);
			salvarImagem.renomearArquivo(pessoaModel.getCodigo().toString());
			this.pessoaModel = new PessoaModel();
			
			if (faz){
				NegocioException.MensagemInformacao("Funcionario salvo com sucesso!");
			}
			
		} catch (NegocioException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
	
	}

	
	// Salva Registro via Upload
	public void UploadRegistros() throws NegocioException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			if (this.file.getFileName().equals("")) {
				NegocioException.MensagemAtencao("Nenhum arquivo selecionado!");
				return;
			}

			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(this.file.getInputstream());

			Element element = doc.getDocumentElement();

			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element elementDados = (Element) node;

					// Pegando os Elementos da entidade em Xml
					String nome  = elementDados.getElementsByTagName("nome").item(0).getChildNodes().item(0).getNodeValue();
					String email  = elementDados.getElementsByTagName("email").item(0).getChildNodes().item(0).getNodeValue();
					String endereco = elementDados.getElementsByTagName("endereco").item(0).getChildNodes().item(0).getNodeValue();
					String cep = elementDados.getElementsByTagName("cep").item(0).getChildNodes().item(0).getNodeValue();
					String cargo = elementDados.getElementsByTagName("cargo").item(0).getChildNodes().item(0).getNodeValue();
					String usuario = elementDados.getElementsByTagName("usuario").item(0).getChildNodes().item(0).getNodeValue();
					String senha = elementDados.getElementsByTagName("senha").item(0).getChildNodes().item(0).getNodeValue();

					// Informa que o Cadastro foi Upload
					pessoaModel.setOrigemCadastro("X");

					// Seta os dados dos Elementos em Xml
					pessoaModel.setNome(nome);
					pessoaModel.setEmail(email);
					logradouroModel.setEndereco(endereco);
					logradouroModel.setCep(cep);
					funcionarioModel.setCargo(cargo);
					usuarioModel.setUsuario(usuario);
					usuarioModel.setSenha(senha);

					// Executa o metodo Salvar desenvolvido Acima.
					salvar();
				
				}
			}

			NegocioException.MensagemInformacao("Registros cadastrados com sucesso!");

		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} catch (SAXException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	public void encontraEndereco() {
		EnderecoERP logradouro;
		try {
			logradouro = new AtendeClienteProxy().consultaCEP(logradouroModel.getCep().replace("-", ""));
			logradouroModel.setEndereco(logradouro.getEnd());
		} catch (RemoteException e) {
			e.printStackTrace();
			NegocioException.MensagemAtencao(e+", Por Favor Preencha os Campos de Endereço!");
		}
	}	
	
	public List<String> pesquisarCargos(String cargos) {
		return this.funcionarioRepository.cargosQueContem(cargos);
	}

	public List<String> pesquisarEnderecos(String enderecos) {
		return this.logradouroRepository.enderecosQueContem(enderecos);
	}

	public FuncionarioModel getFuncionarioModel() {
		return funcionarioModel;
	}

	public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
		this.funcionarioModel = funcionarioModel;
	}

	public LogradouroModel getLogradouroModel() {
		return logradouroModel;
	}

	public void setLogradouroModel(LogradouroModel logradouroModel) {
		this.logradouroModel = logradouroModel;
	}
	
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
	
	public UploadedFile getFile() {
		return file;
	}
 
	public void setFile(UploadedFile file) {
		this.file = file;
	}

}