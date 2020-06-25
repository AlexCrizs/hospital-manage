package com.bjpowernode.triage.buss.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.bjpowernode.triage.buss.entity.Dept;
import com.bjpowernode.triage.buss.entity.Patient;
import com.bjpowernode.triage.buss.entity.Triage;
import com.bjpowernode.triage.buss.service.PrescriptionService;
import com.bjpowernode.triage.buss.service.TriageService;
import com.bjpowernode.triage.common.controller.BaseController;
import com.bjpowernode.triage.common.persistence.Page;
import com.bjpowernode.triage.common.persistence.PropertyFilter;

/**
 * 分诊controller
 * @author bjpowernode
 * @date 2016年1月13日
 */
@Controller
@RequestMapping("buss/triage")
public class TriageController extends BaseController {

	@Autowired
	private TriageService triageService;
	
	@Autowired
	private PrescriptionService prescriptionService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "buss/triageList";
	}
	
	
	/**
	 * 获取分诊json
	 */
	@RequiresPermissions("buss:triage:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Triage> page = getPage(request);
		page.setOrderBy("urgent,createTime");
		page.setOrder("desc,asc");
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = triageService.search(page, filters);
		return getEasyUIData(page);
	}

//	/**
//	 * 添加分诊跳转
//	 * 
//	 * @param model
//	 */
//	@RequiresPermissions("buss:triage:add")
//	@RequestMapping(value = "create", method = RequestMethod.GET)
//	public String createForm(Model model) {
//		model.addAttribute("triage", new Dept());
//		model.addAttribute("action", "create");
//		return "buss/deptForm";
//	}

	/**
	 * 添加分诊
	 * 
	 * @param triage
	 * @param model
	 */
	@RequiresPermissions("buss:triage:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Triage triage,Integer patientId,Integer deptId, Model model) {
		triage.setStatus("1");
		Patient patient = new Patient(patientId);
		Dept dept = new Dept(deptId);
		triage.setDept(dept);
		triage.setPatient(patient);
		triage.setCreateTime(new Date());
		triageService.save(triage);
		return "success";
	}
	
	/**
	 * 叫号
	 * 
	 * @param model
	 */
	@RequestMapping(value = "callPatient", method = RequestMethod.GET)
	@ResponseBody
	public String callPatient(HttpServletRequest request,Model model) {
		Page<Triage> page = getPage(request);
		page.setOrderBy("urgent,createTime");
		page.setOrder("desc,asc");
		PropertyFilter pf = new PropertyFilter("EQS_status","1");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(pf);
		page = triageService.search(page, filters);
		if(page == null){
			return "目前没有候诊人员！";
		}
		if(page.getResult().size() == 0){
			return "目前没有候诊人员！";
		}
		Triage triage = page.getResult().get(0);
		triage.setStatus("0");
		triageService.save(triage);
		return "success";
	}
	
	/**
	 * 弹窗页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{patientId}/patientTriage/{patientName}/patientName")
	public String getUserRole(@PathVariable("patientId") Integer patientId, @PathVariable("patientName") String patientName,Model model) {
		model.addAttribute("patientId", patientId);
		model.addAttribute("patientName", patientName);
		model.addAttribute("action", "create");
		return "buss/triagePatientForm";
	}
	
	/**
	 * 修改triage跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:triage:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("triage", triageService.get(id));
		model.addAttribute("action", "update");
		return "buss/triageForm";
	}

	/**
	 * 修改分诊
	 * 
	 * @param dept
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:triage:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Triage triage,Integer patientId,Integer deptId,Model model) {
		Patient patient = new Patient(patientId);
		Dept dept = new Dept(deptId);
		triage.setDept(dept);
		triage.setPatient(patient);
		triageService.update(triage);
		return "success";
	}

	/**
	 * 删除分诊
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("buss:dept:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		triageService.delete(id);
		return "success";
	}


}
