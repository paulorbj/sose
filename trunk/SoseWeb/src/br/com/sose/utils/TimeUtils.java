package br.com.sose.utils;

import java.util.Date;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="timeUtils")
@RemotingDestination(value="timeUtils")
public class TimeUtils {

	@RemotingInclude
	@Transactional(readOnly=true)
	public Date dataAtual() {
		return new Date();
	}
	
}
