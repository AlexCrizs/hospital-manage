<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/buss/dept/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>科室名称：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:true" value="${dept.name }"> 
				</td>
			</tr>
			<tr>
				<td>所在区域：</td>
				<td><input name="area" type="text" value="${dept.area }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,20]'"/></td>
			</tr>
			<tr>
				<td>电话：</td>
				<td><input type="text" name="phone" value="${dept.phone }" class="easyui-numberbox"  data-options="width: 150"/></td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
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