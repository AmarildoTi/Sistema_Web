package br.com.amarildo.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.outjected.email.api.MailMessage;

import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.util.Mailer;
import br.com.amarildo.util.NegocioException;


	@Named
	@RequestScoped
	public class EnvioEmailBean implements Serializable {

		private static final long serialVersionUID = 1L;

		@Inject
		private Mailer mailer;
		
		public void enviarEmail(PessoaModel pessoaModel) {
			
			MailMessage message = mailer.novaMensagem();
			
			System.out.println("Passou No e-mail!!!"+pessoaModel.getEmail());
			
			message.to(pessoaModel.getEmail())
				.subject(" Assunto do E-mail, Teste de envio ")
				.bodyHtml("<strong>Mensagem dos Corpo do E-mail Teste de Envio de E-mail </strong> ")
				.send();
		
			NegocioException.MensagemInformacao("e-mail enviado com sucesso!! para "+pessoaModel.getEmail());

		}
		
		
	}
	
	
