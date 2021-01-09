package br.com.amarildo.view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.FacesException;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Named;

import org.primefaces.event.CaptureEvent;
import org.primefaces.model.CroppedImage;

import br.com.amarildo.util.mensagens.NegocioException;

@Named
@ViewScoped
public class PhotoCamBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CroppedImage croppedImage;

	private String filename;

	private String newImageName;

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public String getNewImageName() {
		return newImageName;
	}

	public void setNewImageName(String newImageName) {
		this.newImageName = newImageName;
	}

	private String getRandomImageName() {
		int i = (int) (Math.random() * 10000000);

		return String.valueOf(i);
	}

	public String getFilename() {
		return filename;
	}

	public void oncapture(CaptureEvent captureEvent) {
		filename = getRandomImageName();
		byte[] data = captureEvent.getData();

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String newFileName = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + filename + ".png";

		System.out.println("oncapture = "+newFileName);
		
		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (IOException e) {
			throw new FacesException("Erro ao Captuar Imagem !!!", e);
		}
	}

	public void crop() {
		if (croppedImage == null) {
			return;
		}

		System.out.println("crop = " + filename);
		
		setNewImageName(filename);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String newFileName = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + filename + ".png";

		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));
			imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
			imageOutput.close();
		} catch (Exception e) {
			NegocioException.MensagemErro("Falha ao Recortar Imagem !!!");
			return;
		}
			NegocioException.MensagemInformacao("Imagem Recortada com sucesso !!!");
	}

	public void renomearArquivo(String NewImageName) {
		setNewImageName(filename);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String nomeEntrada = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + filename + ".png";
		String nomeSaida = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + NewImageName + ".png";
		System.out.println("Nome de Entrada = " + nomeEntrada);
		System.out.println("Nome de Saida   = " + nomeSaida);
		new File(nomeEntrada).renameTo(new File(nomeSaida));
	}
	
	public void deleteArquivo(String file) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String nomeSaida = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + file + ".png";
		File Arqs = new File(nomeSaida);
		boolean success = Arqs.delete();
		if (!success) {
			 System.out.println(" Deletion failed."+Arqs.toString()+" Arquivo(s) Não Encontrados");
		} else {
			 System.out.println( "File deleted."+Arqs.toString());
		}
	}

	public boolean existeArquivo(String file) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String nomeSaida = externalContext.getRealPath("") + "resources" + File.separator + "Fotos" + File.separator + file + ".png";
		File Arqs = new File(nomeSaida);
		boolean success = Arqs.exists();
		if (!success) {
			 System.out.println(Arqs.toString()+" Arquivo(s) Não Encontrados");
		} else {
			 System.out.println( "Arquivo(s) Encontrados."+Arqs.toString());
		}
		return success;
	}
	
}