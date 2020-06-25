package com.bjpowernode.triage.buss.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjpowernode.triage.buss.entity.Patient;
import com.bjpowernode.triage.buss.service.PatientService;
import com.bjpowernode.triage.common.controller.BaseController;
import com.bjpowernode.triage.common.persistence.Page;
import com.bjpowernode.triage.common.persistence.PropertyFilter;

/**
 * 用户controller
 * @author bjpowernode
 * @date 2016年1月13日
 */
@Controller
@RequestMapping("buss/patient")
public class PatientController extends BaseController {

	@Autowired
	private PatientService patientService;


	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "buss/patientList";
	}

	/**
	 * 获取json
	 */
	@RequiresPermissions("buss:patient:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Patient> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = patientService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加科室跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("buss:patient:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("action", "create");
		return "buss/patientForm";
	}

	/**
	 * 添加科室
	 * 
	 * @param patient
	 * @param model
	 */
	@RequiresPermissions("buss:patient:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Patient patient, Model model) {
		patientService.save(patient);
		return "success";
	}

	/**
	 * 修改科室跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:patient:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("patient", patientService.get(id));
		model.addAttribute("action", "update");
		return "buss/patientForm";
	}

	/**
	 * 修改科室
	 * 
	 * @param patient
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:patient:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Patient patient,Model model) {
		patientService.update(patient);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("buss:patient:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		patientService.delete(id);
		return "success";
	}


}
