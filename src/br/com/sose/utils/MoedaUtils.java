package br.com.sose.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class MoedaUtils {

	public static String formatarValorString(String str)
	{
		str = str.replace("R$","");
		str = str.replace(" ","");
		String comHtml = "retirando <a href=zzz>código html</a>";
		Pattern p = Pattern.compile("\\.");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");

		
		String formatted="";
		
		String inteiro = "";
		String inteiroFormatado = "";
		//Pega os centavos se esses existirem
		String centavos = "";
		String centavosFormatado = "";
		if(str !=null && str.length() > 0){
			int indexVirgula = str.lastIndexOf(",");
			//possui virgula?
			if(indexVirgula != -1){
				centavos = str.substring(indexVirgula+1);
				inteiro = str.substring(0,indexVirgula);
			}else{
				inteiro = str;
			}
		}
		
		//Formata os centavos
		if(centavos.length() > 0){
			if(centavos.length() == 1){
				centavosFormatado = centavos.charAt(0)+"0";
			}
			if(centavos.length() > 2){
				Character caracter = centavos.charAt(2);
				int numero = Integer.parseInt(caracter.toString());
				if(numero >= 5){
					Character char1 = centavos.charAt(1);
					Integer numero1 = Integer.parseInt(char1.toString());
					numero1++;
					centavosFormatado = centavos.charAt(0) + numero1.toString(); 
				}else{
					Character c1 = centavos.charAt(0);
					Character c2 = centavos.charAt(1);
					centavosFormatado = c1.toString() + c2.toString();
				}
			}else{
				centavosFormatado = centavos;
			}
		}else{
			centavosFormatado = "00";
		}
		
		//Formatar valor inteiro
		int separador = 0;
		Character char2 = null;
		for(int i=inteiro.length()-1;i!=-1;i--){
			char2 = inteiro.charAt(i);
			inteiroFormatado = char2 + inteiroFormatado;
			separador++;
			
			if(separador%3 == 0 && i != 0){
				inteiroFormatado = "." + inteiroFormatado;
			}
		}
		
		formatted = inteiroFormatado + "," + centavosFormatado;
		
		return formatted;
	}
	
	public static String formatarValorNumber(BigDecimal num)
	{
		String str = num.toString();
		String strRetorno = "";
		str = str.replace(".",",");
		strRetorno = formatarValorString(str);
		if(strRetorno.indexOf("R$") == -1)
			strRetorno = "R$ " + strRetorno;
		return strRetorno;
	}
}
