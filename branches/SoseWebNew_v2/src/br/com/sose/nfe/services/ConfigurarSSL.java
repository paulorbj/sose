package br.com.sose.nfe.services;

import java.security.Security;

public class ConfigurarSSL {
	
	public void configurarSSL(String cert, String keystore, String senhaCert) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		System.setProperty("java.protocol.handler.pkgs",	"com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStore", cert);
		System.setProperty("javax.net.ssl.keyStorePassword", senhaCert);
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", "teste1234"); 
	}
	
}