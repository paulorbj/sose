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

import br.com.sose.service.ArquivoUploadService;
import br.com.sose.utils.ArquivoUpload;

public class UploadServlet extends HttpServlet {

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
		String tipoObjeto = null;
		String identificadorObjeto = null;
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
					if(name.equals("tipoObjeto")){
						tipoObjeto = item.getString();
					}
					if(name.equals("identificadorObjeto")){
						identificadorObjeto = item.getString();
					}

				} else {
					try {
						
						String caminhoParaSalvar = caminho + tipoObjeto + "\\" + identificadorObjeto ;
						File localParaSalvar = new File(caminhoParaSalvar);
						
						if(!localParaSalvar.exists()){
							localParaSalvar.mkdirs();
						}
						
						Date nomeGerado = new Date();
						
						//Gerar ou melhor, recuperar a extensao
						String caminhoDefinitivo = caminhoParaSalvar + "\\" + new Long(nomeGerado.getTime()).toString();
						File caminhoDefinitivoFile = new File(caminhoDefinitivo);

						item.write(caminhoDefinitivoFile);
						
						ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
						ArquivoUploadService aus = (ArquivoUploadService)  context.getBean("arquivoUploadService");
						
						ArquivoUpload au = new ArquivoUpload();
						
						au.setCaminho(caminhoDefinitivo);
						au.setDataUpload(nomeGerado);
						au.setNomeOriginal(item.getName());
						au.setIdentificadorEntidade(identificadorObjeto);
						au.setTipoEntidade(tipoObjeto);
						au.setNome(new Long(nomeGerado.getTime()).toString());
						au.setTipoArquivo(item.getContentType());
						
						au = aus.salvarArquivoUpload(au);
						
					} catch (Exception e) {
						logger.error(e);
						e.printStackTrace();
					}
				}
			}
			response.getWriter().write("sucesso");
		}
	}

}