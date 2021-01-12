package br.com.amarildo.view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.amarildo.model.FuncionarioModel;
import br.com.amarildo.model.LogradouroModel;
import br.com.amarildo.model.PessoaModel;
import br.com.amarildo.model.UsuarioModel;
import br.com.amarildo.util.email.Mailer;
import br.com.amarildo.util.mensagens.NegocioException;


	@Named
	@RequestScoped
	public class EnvioEmailBean implements Serializable {

		private static final long serialVersionUID = 1L;

		@Inject
		private FuncionarioModel funcionarioModel;

		@Inject
		private LogradouroModel logradouroModel;

		@Inject
		private UsuarioModel usuarioModel;

		
		@Inject
		private Mailer mailer;
		
		public void enviarEmail(PessoaModel pessoaModel) throws IOException {
			
			MailMessage message = mailer.novaMensagem();
			
			System.out.println("Passou No e-mail!!!"+pessoaModel.getEmail());
			
			message.to(pessoaModel.getEmail())
				.subject(" Assunto do E-mail, Teste de envio ")
				//.bodyHtml("<strong>Mensagem do Corpo do E-mail Teste de Envio de E-mail </strong> ")
				//.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/br/com/amarildo/mensagens/CorpoEmail.template")))
				.bodyHtml(new VelocityTemplate(new File("/br/com/amarildo/mensagens/CorpoEmail.template")))
				.put("pessoaModel", pessoaModel)
				.send();
		
			NegocioException.MensagemInformacao("e-mail enviado com sucesso!! para "+pessoaModel.getEmail());

		}
		
		
	}
	
	
