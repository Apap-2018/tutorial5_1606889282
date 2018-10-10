package com.apap.tutorial5.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

@Controller
public class DealerController {
	@Autowired 
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value="/dealer/add", method=RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value="/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value="/dealer/view", method= RequestMethod.GET)
	private String viewDealer(@RequestParam("dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		List<CarModel> listCar = dealer.getListCar();
		Collections.sort(listCar, new sortCar());
		Long idAdd =dealerId;
		model.addAttribute("dealer",dealer);
		model.addAttribute("dealerId",idAdd);
		model.addAttribute("listCar",listCar);
		return "viewDealer";
	}
	@RequestMapping(value="/dealer/viewall", method=RequestMethod.GET)
	private String viewall(Model model) {
		List<DealerModel> listDealer = dealerService.getAllDealer();
		for(int i=0;i<listDealer.size();i++) {
			List<CarModel> listCar = listDealer.get(i).getListCar();
			Collections.sort(listCar, new sortCar());
		}
		model.addAttribute("listDealer",listDealer);
		return "viewall";	
	}
	
	@RequestMapping(value="dealer/delete/{dealerId}", method= RequestMethod.GET)
	private String delete(@PathVariable(value="dealerId") Long dealerId,Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealerService.deleteDealer(dealer);
		return "deleteDealer";
	}
	
	
	
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerModel newDealer = new DealerModel();
		newDealer.setId(dealer.getId());
		model.addAttribute("dealer",newDealer);
		model.addAttribute("oldDealer",dealer);
		return "UpdateDealerForm";
	}
	
	@RequestMapping(value = "/dealer/update", method = RequestMethod.POST)
	private String updateDealer(@ModelAttribute DealerModel dealer) {
		DealerModel submit = dealerService.getDealerDetailById(dealer.getId()).get();
		submit.setAlamat(dealer.getAlamat());
		submit.setNoTelp(dealer.getNoTelp());
		dealerService.addDealer(submit);
		return "update";
	}
	
	
	
}

class sortCar implements Comparator<CarModel>{
	public int compare(CarModel a, CarModel b) {
		return (int) (a.getPrice() -b.getPrice());
	}
}
