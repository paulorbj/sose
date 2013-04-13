package br.com.sose.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import br.com.sose.entity.admistrativo.Usuario;



public class PasswordUtil {

	// Minimum length 
	public static final int MIN_LENGTH = 10;

	// Random generation
	private static java.util.Random random = new java.util.Random();
	
	// The Logger.
	private static Logger logger = Logger.getLogger(PasswordUtil.class);

	// Set of characters that is valid.
	protected static char[] validChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
		'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
		'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '@'};

	
	// Returns the random password and send the e-mail with it.
	public static String managePassword(Usuario user) throws Exception {
		
		// Generate first access password
		String password = generatePassword();
		
		try {
			// Send the password 
//			EmailUtil.mailPassword(password, user);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			logger.error("Error trying to send email.", e);
			throw new Exception(e);
		}
		
		// Password should be encrypted to be saved into DB
		return encryptPassword(password);
	}
	
	// Generate password 
	public static String generatePassword() {
		StringBuffer newPass = new StringBuffer();
		for (int i = 0; i < MIN_LENGTH; i++) {
			newPass.append(validChar[random.nextInt(validChar.length)]);
		}
		return newPass.toString();
	}

	// Encrypt the password
	public static String encryptPassword (String password) {   
		try {   
			MessageDigest digest = MessageDigest.getInstance("MD5");   
			digest.update(password.getBytes());   
			BASE64Encoder encoder = new BASE64Encoder ();
			//return new String(Base64Coder.encode(digest.digest()));
			return encoder.encode (digest.digest ());   
		} catch (NoSuchAlgorithmException ns) {   
			ns.printStackTrace ();   
			return password;   
		}   
	}
	
	// Encrypt the password
	public static String encryptPassword2 (String password) {   
		try {   
			return new String(Base64Coder.encodeString(password));  
		} catch (Exception ns) {   
			ns.printStackTrace ();   
			return password;   
		}   
	}
	
	// Encrypt the password
    //retorna o seu texto original descriptografando ele!  
    public static String decryptPassword(String password) {  
        String ret = "";  
        try {  
            ret = Base64Coder.decodeString(password);
            //digest.
        } catch ( Exception e ) {  
            e.printStackTrace(); logger.error(e);  
        }  
        return ret;  
    } 
    
    public static void main(String[] args){
    	String senha = "1";
    	String senhaEncriptada = "";
    	String senhaDecriptada = "";
    	System.out.println("Senha: "+senha);
    	senhaEncriptada = PasswordUtil.encryptPassword2(senha);
    	System.out.println("Senha encriptada: " + senhaEncriptada);
    	senhaDecriptada = PasswordUtil.decryptPassword("QjI0MDMxNVM=");
    	System.out.println("Senha decriptada: " + senhaDecriptada);
    	
    	
    	
    }
}
