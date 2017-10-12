package br.com.amarildo.view;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.repository.PessoaRepository;
import br.com.amarildo.util.Utilidades;


@Named
@RequestScoped 
public class CriarPdfBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Utilidades Util;

	@Inject
	transient PessoaRepository pessoaRepository;

	private StreamedContent downloadPdf;
	
	private StreamedContent visualizarPdf;

	
	// Realiza o Download do Arquivo PDF.
	public StreamedContent getArquivoDownload() throws DocumentException, IOException {

		this.GeraPdf();

		return downloadPdf;
	}

	
	// Realiza o Visualizar do Arquivo PDF.
	public StreamedContent getExibiPdf() throws DocumentException, IOException {

		this.Visualizar_Pdf();

		return visualizarPdf;
	}

	
	
	private List<String> addVariaveis ()  {
		List<String> conteudo_f = new ArrayList<String>();
		try{
			
			List<PessoaModel> pessoasModel = pessoaRepository.todas();
		
		pessoasModel.forEach(pessoa -> {
			// Caracter  alt + 179 = │ tabela Acsii para poder separa e identificar Fonte│Tamanho│Negrito
			conteudo_f.add("Arial│9│1│codigo  "+Util.Space(01)+"#Arial│9│0│"+pessoa.getFuncionarioModel().getCodigo().toString());
			conteudo_f.add("Arial│9│1│Nome    "+Util.Space(01)+"#Arial│9│0│"+pessoa.getNome());
			conteudo_f.add("Arial│9│1│Endereço"+Util.Space(01)+"#Arial│9│0│"+pessoa.getLogradouroModel().getEndereco());
			conteudo_f.add("Arial│9│1│Cep     "+Util.Space(01)+"#Arial│9│0│"+pessoa.getLogradouroModel().getCep());
			conteudo_f.add("Arial│9│1│cargo   "+Util.Space(01)+"#Arial│9│0│"+pessoa.getFuncionarioModel().getCargo());
			conteudo_f.add("Arial│9│1│usuário "+Util.Space(01)+"#Arial│9│0│"+pessoa.getFuncionarioModel().getUsuarioModel().getUsuario());
			conteudo_f.add("Arial│9│1│senha   "+Util.Space(01)+"#Arial│9│0│"+pessoa.getFuncionarioModel().getUsuarioModel().getSenha());
		});
		
			}
			catch (Exception e){
				System.out.println(e.getMessage());
			}
			return conteudo_f;
	}
	
	public void PosicionaVariaveisPdf(PdfWriter escritor, String imagem, PdfContentByte cb, List<String> variaveis, Document documento) throws IOException, DocumentException {

		double rotacao =  0.0;

		// Começo  Adiciona imagem ou Zebrado ao Abrir o Pdf
		if (!imagem.isEmpty()) {
			String diretorioPDF = "C://Amarildo//Sistema_Web//pdflib//";
			PdfReader img = new PdfReader(diretorioPDF + imagem);
			PdfImportedPage pagina = escritor.getImportedPage(img, 1);
			cb.addTemplate(pagina, 1, 0, 0, 1, 0, 0);
		}

		Util.paginaZebrada(cb, 0, 0, 210, 3, BaseColor.GRAY, BaseColor.GRAY);
 		// Final Adiciona imagem ou Zebrado ao Abrir o Pdf
		
		double y = 11.5;
		
		for (int i = 0; i < variaveis.size(); i++) {
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 3;
			i = i + 1;
			Util.WritePdf(cb, 10, y, 195.0, 1.0, 5.0, Element.ALIGN_LEFT, 0, rotacao, variaveis.get(i),BaseColor.BLACK);
			y = y + 6;
	
		
			// Começo Cria nova pagina e Adiciona imagem ou Zebrado
			int Linhas = (i + 1);
			boolean NovaPagina = Linhas % 84 == 0;
			if(NovaPagina){
			  y = 11.5;
			  documento.newPage();
			  Util.paginaZebrada(cb, 0, 0, 210, 3, BaseColor.GRAY, BaseColor.GRAY);		
				if (!imagem.isEmpty()) {
			      String diretorioPDF = "C://Amarildo//Sistema_Web//pdflib//";
				  PdfReader img = new PdfReader(diretorioPDF + imagem);
				  PdfImportedPage pagina = escritor.getImportedPage(img, 1);
				  cb.addTemplate(pagina, 1, 0, 0, 1, 0, 0);
				}
			}
     		// Final Cria nova pagina e Adiciona imagem ou Zebrado
		
		}
	}

	public String GeraPdf() throws DocumentException, IOException {

		String imagem = "LISTRAS.pdf";

		String nomearquivosaida = "Teste.pdf";

		PdfWriter escritor = null;
		
		String arquivo = new String("C:/Amarildo/Sistema_Web/".concat(nomearquivosaida));

		Document documento = new Document(PageSize.A4);

		FileOutputStream fs = new FileOutputStream(arquivo);

		escritor = PdfWriter.getInstance(documento, fs);

		documento.open();

		PdfContentByte cb = escritor.getDirectContent();
		cb.setColorFill(BaseColor.BLACK);

		FontFactory.register("C://Windows//Fonts//arial.ttf");
		FontFactory.register("C://Windows//Fonts//arialbd.ttf");
		FontFactory.register("C://Windows//Fonts//COUR.TTF");

		List<String> variaveis= addVariaveis();
		PosicionaVariaveisPdf(escritor, "", cb,variaveis, documento); // Sem Imagem
		//PosicionaVariaveisPdf(escritor, imagem, cb,variaveis, documento); // Com Imagem Imagem
		//documento.newPage();

		documento.close();
		escritor.close();

		return arquivo;

	}
	
	public void Visualizar_Pdf() {
		try {

			String imagem = "LISTRAS.pdf";

			String nomearquivosaida = "Teste.pdf";

			PdfWriter escritor = null;

			Document documento = new Document(PageSize.A4);
			
			OutputStream out = new ByteArrayOutputStream();
			
			escritor = PdfWriter.getInstance(documento, out);

			documento.open();
			
			PdfContentByte cb = escritor.getDirectContent();
			cb.setColorFill(BaseColor.BLACK);

			FontFactory.register("C://Windows//Fonts//arial.ttf");
			FontFactory.register("C://Windows//Fonts//arialbd.ttf");
			FontFactory.register("C://Windows//Fonts//COUR.TTF");

			List<String> variaveis= addVariaveis();
			PosicionaVariaveisPdf(escritor, "", cb,variaveis, documento); // Sem Imagem
			//PosicionaVariaveisPdf(escritor, imagem, cb,variaveis, documento); // Com Imagem Imagem
			//documento.newPage();
			
			documento.close();
			escritor.close();
			
			InputStream in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());

			visualizarPdf = new DefaultStreamedContent(in, "application/pdf");

			Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			byte[] b = (byte[]) session.get("reportBytes");
			if (b != null) {
				visualizarPdf = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf");
			}

		} catch (Exception e) {

		}
	}	   
	   
}
