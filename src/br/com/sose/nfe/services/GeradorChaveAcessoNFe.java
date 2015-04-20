package br.com.sose.nfe.services;

import org.apache.log4j.Logger;

public class GeradorChaveAcessoNFe {
	
	private final static Integer[] PESOS = {4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2};
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public String gerarChave(String codUF, String dtEmissao, String cnpjEmitente, String modelo, String serie, String nNFeletronica, String formaEmissao, String codNumerico){
		Integer[] chave = new Integer[44];
		if(codUF != null && codUF.length() != 2){
			return null;
		}else{
			try{
				chave[0] = Integer.parseInt(new Character(codUF.charAt(0)).toString());
				chave[1] = Integer.parseInt(new Character(codUF.charAt(1)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(dtEmissao.length() != 4){
			return null;
		}else{
			try{
				chave[2] = Integer.parseInt(new Character(dtEmissao.charAt(0)).toString());
				chave[3] = Integer.parseInt(new Character(dtEmissao.charAt(1)).toString());
				chave[4] = Integer.parseInt(new Character(dtEmissao.charAt(2)).toString());
				chave[5] = Integer.parseInt(new Character(dtEmissao.charAt(3)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(cnpjEmitente.length() != 14){
			return null;
		}else{
			try{
				chave[6] = Integer.parseInt(new Character(cnpjEmitente.charAt(0)).toString());
				chave[7] = Integer.parseInt(new Character(cnpjEmitente.charAt(1)).toString());
				chave[8] = Integer.parseInt(new Character(cnpjEmitente.charAt(2)).toString());
				chave[9] = Integer.parseInt(new Character(cnpjEmitente.charAt(3)).toString());
				chave[10] = Integer.parseInt(new Character(cnpjEmitente.charAt(4)).toString());
				chave[11] = Integer.parseInt(new Character(cnpjEmitente.charAt(5)).toString());
				chave[12] = Integer.parseInt(new Character(cnpjEmitente.charAt(6)).toString());
				chave[13] = Integer.parseInt(new Character(cnpjEmitente.charAt(7)).toString());
				chave[14] = Integer.parseInt(new Character(cnpjEmitente.charAt(8)).toString());
				chave[15] = Integer.parseInt(new Character(cnpjEmitente.charAt(9)).toString());
				chave[16] = Integer.parseInt(new Character(cnpjEmitente.charAt(10)).toString());
				chave[17] = Integer.parseInt(new Character(cnpjEmitente.charAt(11)).toString());
				chave[18] = Integer.parseInt(new Character(cnpjEmitente.charAt(12)).toString());
				chave[19] = Integer.parseInt(new Character(cnpjEmitente.charAt(13)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(modelo.length() != 2){
			return null;
		}else{
			try{
				chave[20] = Integer.parseInt(new Character(modelo.charAt(0)).toString());
				chave[21] = Integer.parseInt(new Character(modelo.charAt(1)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(serie.length() != 3){
			return null;
		}else{
			try{
				chave[22] = Integer.parseInt(new Character(serie.charAt(0)).toString());
				chave[23] = Integer.parseInt(new Character(serie.charAt(1)).toString());
				chave[24] = Integer.parseInt(new Character(serie.charAt(2)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(nNFeletronica.length() != 9){
			return null;
		}else{
			try{
				chave[25] = Integer.parseInt(new Character(nNFeletronica.charAt(0)).toString());
				chave[26] = Integer.parseInt(new Character(nNFeletronica.charAt(1)).toString());
				chave[27] = Integer.parseInt(new Character(nNFeletronica.charAt(2)).toString());
				chave[28] = Integer.parseInt(new Character(nNFeletronica.charAt(3)).toString());
				chave[29] = Integer.parseInt(new Character(nNFeletronica.charAt(4)).toString());
				chave[30] = Integer.parseInt(new Character(nNFeletronica.charAt(5)).toString());
				chave[31] = Integer.parseInt(new Character(nNFeletronica.charAt(6)).toString());
				chave[32] = Integer.parseInt(new Character(nNFeletronica.charAt(7)).toString());
				chave[33] = Integer.parseInt(new Character(nNFeletronica.charAt(8)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(formaEmissao.length() != 1){
			return null;
		}else{
			try{
				chave[34] = Integer.parseInt(formaEmissao);
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}
		if(codNumerico.length() != 8){
			return null;
		}else{
			try{
				chave[35] = Integer.parseInt(new Character(codNumerico.charAt(0)).toString());
				chave[36] = Integer.parseInt(new Character(codNumerico.charAt(1)).toString());
				chave[37] = Integer.parseInt(new Character(codNumerico.charAt(2)).toString());
				chave[38] = Integer.parseInt(new Character(codNumerico.charAt(3)).toString());
				chave[39] = Integer.parseInt(new Character(codNumerico.charAt(4)).toString());
				chave[40] = Integer.parseInt(new Character(codNumerico.charAt(5)).toString());
				chave[41] = Integer.parseInt(new Character(codNumerico.charAt(6)).toString());
				chave[42] = Integer.parseInt(new Character(codNumerico.charAt(7)).toString());
			}catch(Exception e){
				e.printStackTrace(); logger.error(e);
				return null;
			}
		}

		Integer somatorio = 0;
		for(int i=0;i<chave.length-1;i++){
			somatorio = somatorio + chave[i]*PESOS[i];
		}
		System.out.println(somatorio);
		Integer resto = somatorio%11;
		System.out.println(resto);
		Integer DV = 0;
		if(resto == 0 || resto == 1){
			chave[43] = DV;
		}else{
			chave[43] = 11 - resto;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<chave.length;i++){
			sb.append(chave[i]);
		}
		System.out.println(chave[43]);
		return sb.toString();
	}
	
	public static void main(String[] args){
		GeradorChaveAcessoNFe gc = new GeradorChaveAcessoNFe();
		gc.gerarChave("52", "0604", "33009911002506", "55", "012", "000000780", "0", "26730161");
	}

}
