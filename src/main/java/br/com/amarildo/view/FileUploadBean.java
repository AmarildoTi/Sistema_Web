package br.com.amarildo.view;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import br.com.amarildo.util.mensagens.NegocioException;


@Named
public class FileUploadBean {

	public void handleFileUpload(FileUploadEvent event) {

		try {  
            UploadedFile arq = event.getFile();  
            InputStream in = new BufferedInputStream(arq.getInputStream());  

            File file = new File("C:/Amarildo/Sistema_Web/" + arq.getFileName());//o diretório onde será guardado o arquivo  
            FileOutputStream linhas = new FileOutputStream(file);  
            while (in.available() != 0) {  
            	linhas.write(in.read());  
            }  

            linhas.close();  
            NegocioException.MensagemInformacao(" Arquivo " + arq.getFileName() + " Upaload com sucesso! ");
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } 
		
		int Contador = 0;
		String Input;
		String Arquivo_Entrada = event.getFile().getFileName().toString();
		
		try {
			File file = new File("C:/Amarildo/Sistema_Web/" + Arquivo_Entrada);//o diretório onde será guardado o arquivo  
            FileReader reader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(reader);

			while ((Input = bufReader.readLine()) != null) {
				Contador++;
				System.out.println("Linha "+Contador+" "+Input);
			}
			bufReader.close();
			reader.close();
		} catch (IOException erro) {
			erro.getMessage();
		}

		NegocioException.MensagemInformacao(" Arquivo " + Arquivo_Entrada + " salvo com sucesso! ");

	}

}
