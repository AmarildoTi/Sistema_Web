package br.com.amarildo.util.mensagens;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

        //MOSTRAR throw new NegocioException("throw Mensagem de exceção");
	    public NegocioException(String msg) {
		   super(msg);
	    }

	    //MOSTRAR MENSAGEM ALERTA
		public static void MensagemAlerta(String mensagem){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("Alerta",mensagem));
		}
	 
		//MOSTRAR MENSAGEM ATENÇÃO
 		public static void MensagemAtencao(String mensagem){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
		}
	 
		//MOSTRAR MENSAGEM INFORMAÇÃO
		public static void MensagemInformacao(String mensagem){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", mensagem));
		}

		//MOSTRAR MENSAGEM ERRO
		public static void MensagemErro(String mensagem){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagem));
		}
		
}
