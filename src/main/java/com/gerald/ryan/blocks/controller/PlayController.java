package com.gerald.ryan.blocks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gerald.ryan.blocks.utilities.StringUtils;

@Controller
@RequestMapping("/play")
public class PlayController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getPlay(Model model) {
		model.addAttribute("heres", "something");
		StringUtils.mapKeyValue(model.asMap());
		return "play";
	}

}
