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

import com.bjpowernode.triage.buss.entity.Dept;
import com.bjpowernode.triage.buss.service.DeptService;
import com.bjpowernode.triage.common.controller.BaseController;
import com.bjpowernode.triage.common.persistence.Page;
import com.bjpowernode.triage.common.persistence.PropertyFilter;
import com.bjpowernode.triage.system.entity.Permission;

/**
 * 用户controller
 * @author bjpowernode
 * @date 2016年1月13日
 */
@Controller
@RequestMapping("buss/dept")
public class DeptController extends BaseController {

	@Autowired
	private DeptService deptService;


	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "buss/deptList";
	}
	
	/**
	 * 部门集合(JSON)
	 */
	@RequestMapping(value="allDept/json",method = RequestMethod.GET)
	@ResponseBody
	public List<Dept>  allDept(){
		List<Dept> deptList=deptService.getAll();
		return deptList;
	}
	
	/**
	 * 获取科室json
	 */
	@RequiresPermissions("buss:dept:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Dept> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = deptService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加科室跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("buss:dept:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("dept", new Dept());
		model.addAttribute("action", "create");
		return "buss/deptForm";
	}

	/**
	 * 添加科室
	 * 
	 * @param dept
	 * @param model
	 */
	@RequiresPermissions("buss:dept:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Dept dept, Model model) {
		deptService.save(dept);
		return "success";
	}

	/**
	 * 修改科室跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:dept:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("dept", deptService.get(id));
		model.addAttribute("action", "update");
		return "buss/deptForm";
	}

	/**
	 * 修改科室
	 * 
	 * @param dept
	 * @param model
	 * @return
	 */
	@RequiresPermissions("buss:dept:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Dept dept,Model model) {
		deptService.update(dept);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("buss:dept:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		deptService.delete(id);
		return "success";
	}


}
