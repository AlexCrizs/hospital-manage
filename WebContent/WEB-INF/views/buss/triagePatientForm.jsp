<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="triageform" action="${ctx }/buss/triage/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>患者姓名：</td>
				<td>
					<input type="hidden" name="patientId" value="${patientId }"/>
					<input id="patientName" name="patientName" readonly="readonly" class="easyui-validatebox" data-options="width: 150" value="${patientName }"> 
				</td>
			</tr>
			<tr>
				<td>科室：</td>
				<td><input id="deptId" name="deptId" value="${user.dept.id }"/></td>
			</tr>
			<tr>
				<td>急诊：</td>
				<td>
				<input type="radio" id="no" name="urgent" value="0"/><label for="no">否</label>
				<input type="radio" id="yes" name="urgent" value="1"/><label for="yes">是</label>
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
var action="${action}";
//用户 添加修改区分
if(action=='create'){
	$("input[name='urgent'][value=0]").attr("checked",true); 
}else if(action=='update'){
	$("input[name='urgent'][value=${triage.urgent}]").attr("checked",true);
}

//
$('#deptId').combobox({
	width:150,
	method:'GET',
    url: '${ctx}/buss/dept/allDept/json',
    valueField : 'id',
    textField : 'name'
});  

//提交表单
$('#triageform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	successTip(data,dg,d);
    }    
});    
</script>
</body>
</html>