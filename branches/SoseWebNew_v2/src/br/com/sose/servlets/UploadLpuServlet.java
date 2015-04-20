package br.com.sose.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.sose.entity.lpu.Lpu;
import br.com.sose.service.ArquivoUploadService;
import br.com.sose.service.lpu.LpuService;
import br.com.sose.utils.ArquivoUpload;

public class UploadLpuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	private static Map<String, String> fileMap = new HashMap<String, String>();

	//    private static String caminho = "C:\\Program Files (x86)\\Apache Group\\Apache2\\htdocs\\videos\\";
	private static String caminho = "C:\\arquivo_servilogi\\";

	public static String getSavedFileName(String originalFileName) {
		return fileMap.containsKey(originalFileName)?fileMap.get(originalFileName): originalFileName;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Long idLpu = null;
		Lpu lpu = null;
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ArquivoUploadService arquivoUploadService = (ArquivoUploadService)  context.getBean("arquivoUploadService");
		LpuService lpuService = (LpuService)  context.getBean("lpuService");
		
		logger.debug("request: "+request);
		if (!isMultipart) {
			logger.warn("File Not Uploaded");
		} else {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = null;

			try {
				items = upload.parseRequest(request);
				logger.debug("items: "+items);
			} catch (FileUploadException e) {
				e.printStackTrace();
				logger.error(e);
			}
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()){
					String name = item.getFieldName();
					if(name.equals("idLpu")){
						idLpu = Long.valueOf(item.getString());
						try{
							lpu = lpuService.buscarId(idLpu);	
						}catch(Exception e){
							e.printStackTrace();
						}						
					}


				} else {
					try {
						
						String caminhoParaSalvar = caminho + "lpu\\" + idLpu + "\\" +  getTipoArquivo(getExtensaoArquivo(item.getName()));
						File localParaSalvar = new File(caminhoParaSalvar);
						
						if(!localParaSalvar.exists()){
							localParaSalvar.mkdirs();
						}
						
						Date nomeGerado = new Date();
						
						//Gerar ou melhor, recuperar a extensao
						String caminhoDefinitivo = caminhoParaSalvar + "\\" + new Long(nomeGerado.getTime()).toString() + "." + getExtensaoArquivo(item.getName());
						File caminhoDefinitivoFile = new File(caminhoDefinitivo);

						item.write(caminhoDefinitivoFile);
						
						ArquivoUpload au = arquivoUploadService.buscarArquivoUploadLpu(lpu.getId());
						if(au == null){
							au = new ArquivoUpload();
						}else{
							File localParaDeletar = new File(caminhoParaSalvar + "\\" + au.getNome());
							localParaDeletar.delete();
						}
						
						//au.setCaminho(caminhoDefinitivo);
						au.setDataUpload(nomeGerado);
						au.setNomeOriginal(item.getName());
						au.setIdentificadorEntidade(idLpu);
						au.setTipoEntidade("LPU");
						au.setNome(new Long(nomeGerado.getTime()).toString() + "." + getExtensaoArquivo(item.getName()));
						au.setTipoArquivo(getTipoArquivo(getExtensaoArquivo(item.getName())));
						
						au = arquivoUploadService.salvarArquivoUpload(au);
						
					} catch (Exception e) {
						logger.error(e);
						e.printStackTrace();
					}
				}
			}
			response.getWriter().write("sucesso");
		}
	}
	
	private String getExtensaoArquivo(String nomeArquivo){
		if(nomeArquivo != null){
			int indexPonto = nomeArquivo.lastIndexOf(".");
			return nomeArquivo.substring(indexPonto+1).trim();	
		}else{
			return null;
		}
	}
	
	private String getTipoArquivo(String extensao){
		if(extensao != null){
			if(extensao.equalsIgnoreCase("pdf")){
				return "PDF";
			}
			if(extensao.equalsIgnoreCase("jpg") || extensao.equalsIgnoreCase("gif") || extensao.equalsIgnoreCase("png") || extensao.equalsIgnoreCase("jpeg")){
				return "IMAGEM";
			}
			if(extensao.equalsIgnoreCase("xls") || extensao.equalsIgnoreCase("xlsx")){
				return "PLANILHA";
			}
			return null;
		}else{
			return null;
		}
	}

}