package br.com.sose.service;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

@Service(value="testService")
@RemotingDestination(value="testService")
public class TestService {
	
	public TestService() {
		System.out.println("No TestService");
	}
	
	@RemotingInclude
	public String salvar(String str){
		System.out.println("Teste Teste");
		return "CHAMOU O SERVIçO: "+ str; 
	}

}
