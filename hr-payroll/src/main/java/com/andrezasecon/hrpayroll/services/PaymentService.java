package com.andrezasecon.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.andrezasecon.hrpayroll.entities.Payment;
import com.andrezasecon.hrpayroll.entities.Worker;
import com.andrezasecon.hrpayroll.feignClients.WorkerFeignClient;

@Service
public class PaymentService {
	
//	@Value("${hr-worker.host}")
//	private String workerHost;
	
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Autowired
	private WorkerFeignClient WorkerFeignClient;
	
//	public Payment getPayment(Long workerId, int days) {
//		Map<String, String> uriVaraibles = new HashMap<>();
//		uriVaraibles.put("id", ""+ workerId);
//		Worker worker = restTemplate.getForObject(workerHost + "/workers/{id}", Worker.class, uriVaraibles);
//		return new Payment(worker.getName(),worker.getDailyIncome(), days);
//	}
	
	public Payment getPayment(Long workerId, int days) {
		Worker worker = WorkerFeignClient.findById(workerId).getBody();
		return new Payment(worker.getName(), worker.getDailyIncome(), days);

	}
}
