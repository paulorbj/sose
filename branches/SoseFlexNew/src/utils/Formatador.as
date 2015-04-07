package utils
{
	import flashx.textLayout.utils.CharacterUtil;
	
	public class Formatador
	{
		public function Formatador()
		{
		}
		
		/**
		 // skip everything that isn't a number
		 // and skip left 0
		 var isNumber = /[0-9]/;
		 for (var i=0;i<(str.length);i++) {
		 char = str.substr(i,1);
		 if (formatted.length==0 && char==0) char = false;
		 if (char && char.match(isNumber)) formatted = formatted+char;
		 }
		 
		 // format to fill with zeros when < 100
		 while (formatted.length<3) formatted = '0'+formatted;
		 var centsVal = formatted.substr(formatted.length-2,2);
		 var integerVal = formatted.substr(0,formatted.length-2);
		 
		 // apply cents pontuation
		 formatted = integerVal+centsSeparator+centsVal;	
		 
		 // apply thousands pontuation
		 if (thousandsSeparator) {
		 var thousandsCount = 0;
		 for (var j=integerVal.length;j>0;j--) {
		 char = integerVal.substr(j-1,1);
		 thousandsCount++;
		 if (thousandsCount%3==0) char = thousandsSeparator+char;
		 thousandsFormatted = char+thousandsFormatted;
		 }
		 if (thousandsFormatted.substr(0,1)==thousandsSeparator) thousandsFormatted = thousandsFormatted.substring(1,thousandsFormatted.length);
		 formatted = thousandsFormatted+centsSeparator+centsVal;
		 }
		 
		 // apply the prefix
		 if (prefix) formatted = prefix+formatted;
		 
		 // replace the value
		 obj.val(formatted);				  	
		 */
		
		public static function formatarValorString(str:String):String
		{
			str = str.replace("R$","");
			str = str.replace(" ","");
			var myPattern:RegExp = /\./g;
			str = str.replace(myPattern,"");
			
			var formatted:String="";
			
			var inteiro:String = "";
			var inteiroFormatado:String = "";
			//Pega os centavos se esses existirem
			var centavos:String = "";
			var centavosFormatado:String = "";
			if(str !=null && str.length > 0){
				var indexVirgula:int = str.lastIndexOf(",");
				//possui virgula?
				if(indexVirgula != -1){
					centavos = str.substring(indexVirgula+1);
					inteiro = str.substring(0,indexVirgula);
				}else{
					inteiro = str;
				}
			}
			
			//Formata os centavos
			if(centavos.length > 0){
				if(centavos.length == 1){
					centavosFormatado = centavos.charAt(0)+"0";
				}
				if(centavos.length > 2){
					var char:String = centavos.charAt(2);
					var numero:int = parseInt(char);
					if(numero >= 5){
						var char1:String = centavos.charAt(1)
						var numero1:int = parseInt(char1);
						centavosFormatado = centavos.charAt(0) + (numero1+1).toString(); 
					}else{
						centavosFormatado = centavos.charAt(0)+centavos.charAt(1);
					}
				}else{
					centavosFormatado = centavos;
				}
			}else{
				centavosFormatado = "00";
			}
			
			//Formatar valor inteiro
			var separador:int = 0;
			var char2:String = "";
			for(var i:int=inteiro.length-1;i!=-1;i--){
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
		
		public static function formatarValorNumber(num:Number):String
		{
			if(num == 0 || isNaN(num)){
				return "R$ 0,00";
			}
			var str:String = num.toString();
			var strRetorno:String = "";
			str = str.replace(".",",");
			strRetorno = formatarValorString(str);
			if(strRetorno.indexOf("R$") == -1)
				strRetorno = "R$ " + strRetorno;
			return strRetorno;
		}
		
		public static function formatarValorDolarNumber(num:Number):String
		{
			if(num == 0 || isNaN(num)){
				return "US$ 0,00";
			}
			var str:String = num.toString();
			var strRetorno:String = "";
			str = str.replace(".",",");
			strRetorno = formatarValorString(str);
			if(strRetorno.indexOf("US$") == -1)
				strRetorno = "US$ " + strRetorno;
			return strRetorno;
		}
	}
}