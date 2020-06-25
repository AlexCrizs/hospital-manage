<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
        <div>
	       	<shiro:hasPermission name="buss:triage:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="buss:triage:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="buss:triage:view">
        		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true" onclick="callPatient()">叫号</a>
        	</shiro:hasPermission>
        </div> 
        
  </div>
<table id="dg"></table> 
<div id="dlg"></div>  
<script type="text/javascript">
var dg;
var d;
$(function(){   
	dg=$('#dg').datagrid({    
	method: "get",
    url:'${ctx}/buss/triage/json', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'id',
	striped:true,
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
        {field:'id',title:'id',hidden:true},    
        {field:'patient',title:'患者姓名',sortable:true,
        	formatter : function(value, row, index) {
       			return value.name;
        	}
        },
        {field:'urgent',title:'急诊',sortable:true,
        	formatter : function(value, row, index) {
       			return value==1?'是':'否';
        	}
        },
        {field:'dept',title:'科室',sortable:true,
        	formatter : function(value, row, index) {
       			return value.name;
        	}
        },
        {field:'status',title:'状态',sortable:true,
        	formatter : function(value, row, index) {
       			return value==1?'等待诊断':'诊断结束';
        	}
        }
    ]],
    headerContextMenu: [
        {
            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
        },
        {
            text: "取消冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
        }
    ],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar:'#tb'
	});
});


//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/buss/triage/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 340,    
	    href:'${ctx}/buss/triage/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#triageform').submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

function callPatient(){
	$.ajax({
		type:'get',
		url:"${ctx}/buss/triage/callPatient",
		success: function(data){
			if(data=='success'){
				if(dg!=null)
					dg.datagrid('reload');
				if(d!=null)
					d.panel('close');
				parent.$.messager.show({ title : "提示",msg: "叫号成功！", position: "bottomRight" });
				return true;
			}else{
				parent.$.messager.alert(data);
				return false;
			}  
		}
	});
}
//分诊弹窗
function triageDialog(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	d=$("#dlg").dialog({   
	    title: '挂号管理',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/buss/triage/'+row.id+'/userRole',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
			}
		}]
	});
}

//查看
function look(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '修改用户',    
	    width: 380,    
	    height: 340,    
	    href:'${ctx}/system/user/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}


</script>
</body>
</html>