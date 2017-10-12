package br.com.amarildo.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.repository.PessoaRepository;

@Named
@RequestScoped
public class ExportarRegistrosXmlBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	transient PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	// Realiza o Download do Arquivo Xml.
	public StreamedContent getArquivoDownload() {

		this.DownlaodArquivoXmlPessoa();

		return arquivoDownload;
	}

	// Gerando Arquivo Xml Para Exportação.
	private File GerarXmlFuncionarios() {

		List<PessoaModel> pessoasModel = pessoaRepository.todas();

		// Nome do Elemento Raiz do Xml
		Element elementDados = new Element("funcionarios");

		Document documentoPessoas = new Document(elementDados);

		pessoasModel.forEach(pessoa -> {
			// Campos dos Xml com os Seus Valores
			Element elementPessoa = new Element("funcionario");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getFuncionarioModel().getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("Endereco").setText(pessoa.getLogradouroModel().getEndereco()));
			elementPessoa.addContent(new Element("Cep").setText(pessoa.getLogradouroModel().getCep()));
			elementPessoa.addContent(new Element("cargo").setText(pessoa.getFuncionarioModel().getCargo()));
			elementPessoa.addContent(new Element("usuario").setText(pessoa.getFuncionarioModel().getUsuarioModel().getUsuario()));
			elementPessoa.addContent(new Element("senha").setText(pessoa.getFuncionarioModel().getUsuarioModel().getSenha()));
			elementDados.addContent(elementPessoa);
		});

		XMLOutputter xmlGerado = new XMLOutputter();
		xmlGerado.setFormat(Format.getPrettyFormat().setEncoding("ISO-8859-1"));

		try {

			// Gera o Nome do Arquivo
			String nomeArquivo = "funcionarios_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");

			// Caminho que o Arquivo Sera Salvo
			File arquivo = new File("C:/Amarildo/Sistema_Web/".concat(nomeArquivo));

			FileWriter fileWriter = new FileWriter(arquivo);

			xmlGerado.output(documentoPessoas, fileWriter);

			return arquivo;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	// Prepara o Arquivo Para Download
	public void DownlaodArquivoXmlPessoa() {

		File arquivoXml = this.GerarXmlFuncionarios();

		InputStream inputStream;

		try {

			inputStream = new FileInputStream(arquivoXml.getPath());

			arquivoDownload = new DefaultStreamedContent(inputStream, "application/xml", arquivoXml.getName());

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}
}
