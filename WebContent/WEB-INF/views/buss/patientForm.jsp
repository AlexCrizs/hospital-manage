<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/buss/patient/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>患者姓名：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:true" value="${patient.name }"> 
				</td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td><input name="idCard" type="text" value="${patient.idCard }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,20]'"/></td>
			</tr>
			<tr>
				<td>电话：</td>
				<td><input type="text" name="phone" value="${patient.phone }" class="easyui-numberbox"  data-options="width: 150"/></td>
			</tr>
			<tr>
				<td>紧急联系人电话：</td>
				<td><input type="text" name="urgentPhone" value="${patient.urgentPhone }" class="easyui-numberbox"  data-options="width: 150"/></td>
			</tr>
			<tr>
				<td>住址：</td>
				<td><input type="text" name="address" value="${patient.address }" class="easyui-validatebox"  data-options="width: 150"/></td>
			</tr>
			<tr>
				<td>出生日期：</td>
				<td><input name="birthday" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${patient.birthday}"/>"/></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
				<input type="radio" id="man" name="sex" value="1"/><label for="man">男</label>
				<input type="radio" id="woman" name="sex" value="0"/><label for="woman">女</label>
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
var action="${action}";
//用户 添加修改区分
if(action=='create'){
	$("input[name='sex'][value=1]").attr("checked",true); 
}else if(action=='update'){
	$("input[name='sex'][value=${patient.sex}]").attr("checked",true);
}
//提交表单
$('#mainform').form({    
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