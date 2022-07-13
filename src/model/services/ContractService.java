package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	public void processContract(Contract contract, Integer months, OnlinePaymentService onlinePaymentService) {
		Date contractDate = contract.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(contractDate);
		
		for (int i=1; i<=months; i++) {
			cal.add(Calendar.MONTH, 1);
			Date dueDate = cal.getTime();
			Double amount = contract.getTotalValue() / months;
			amount += onlinePaymentService.interest(amount, i);
			amount += onlinePaymentService.paymentFee(amount);
			
			contract.addInstallment(new Installment(dueDate, amount));			
		}
	}
}
