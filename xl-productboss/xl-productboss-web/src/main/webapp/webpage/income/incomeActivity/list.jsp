<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.css?v=4.4.0">
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/css/plugins/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="${ctx}/css/style.min.css?v=4.1.0">
<style>
.table_t{
	width:98.6%;
	margin:3px 0.5% 5px 0.5%;
}
.table_t td{
	width:20%;
	height:24px;
	line-height:24px;
	padding:4px 0;
}
</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
					<form id="queryform">
						<input type="hidden" name="pageSize" id="pageSize" value=""/>
						<input type="hidden" name="pageNumber" id="pageNumber" value=""/>
						<table cellpadding="0" cellspacing="0" class="table_t">
							<tr>
								<td><label class="col-sm-3 control-label">主键：</label>
									<div class="col-sm-3">
										<input type="text" value="" id="lId" name="lId"/>
									</div>
								</td>
								<td><label class="col-sm-3 control-label">活动名称：</label>
									<div class="col-sm-3">
										<input type="text" value="" id="strName" name="strName"/>
									</div>
								</td>
								<td><label class="col-sm-4 control-label">满额(单位分)：</label>
									<div class="col-sm-3">
										<input type="text" value="" id="lMoney" name="lMoney"/>
									</div>
								</td>
							<tr>
							<tr>
								<td>
									<label class="col-sm-3 control-label">应用范围 :</label>
									<div class="col-sm-4">
										<select  class='form-control' name="strRange" id="strRange">
				                        	<option value="">全部</option>
						                    <option value="1">扫码支付</option>
						                    <option value="3">团购</option>
				                        </select>
			                        </div>
								</td>
								<td>
									<label class="col-sm-3 control-label">业务分类 ：</label>
									<div class="col-sm-4">
										<select class='form-control' name="nBizType" id="nBizType">
										<option value="">全部</option>
					                    <option value="1">通用活动</option>
					                    <option value="2">收息商家活动</option>
					                    <option value="3">收息商品活动</option>
			                        </select>
			                        </div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td align="center">
									<input type="button" onclick="query()" class="btn btn-primary" value="查 询"  />
									<input type="reset" value="重置" class="btn btn-primary"/>
								</td>
							</tr>
						</table>
					</form>
					<div class="">
                        <a onclick="javascript:window.location.href='${ctx}/webpage/income/incomeActivity/add.jsp'" class="btn btn-primary">添加</a>
                        <!-- <a onclick="getid()" class="btn btn-primary ">获取ID</a> -->
                    </div>
                    <table id="date-table" class="table table-striped table-bordered table-hover"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${ctx}/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="${ctx}/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="${ctx}/js/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/js/plugins/bootstrap-table/bootstrap-table-export.min.js"></script>
<script src="${ctx}/js/content.min.js?v=1.0.0"></script>
<script src="${ctx}/js/layer/layer.js"></script>
<script>
    $(document).ready(function () {
    	if('${result}' !=null && '${result}' !=''){
			layer.msg('${result}');
		}
		$('#date-table').bootstrapTable({
    		pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
    		method:'POST',						//请求方式（*）
    		sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
    	    dataType:'json',					//请求后端数据 json格式
    	    contentType:"application/x-www-form-urlencoded; charset=UTF-8", //定义网络文件的类型和网页的编码
    	    pagination:true,					//是否显示分页（*）
			url:'${ctx}/user/income/incomeActivity/listData.do', //请求后台的URL（*）
			queryParams:function(params){ 		//设置查询时候的参数 可发送给服务端的参数：limit->pageSize,offset->pageNumber,search->searchText,sort->sortName(字段)  
				$("#queryform").find("#pageSize").val(this.pageSize);     //表单隐藏的数据赋值默认1
				$("#queryform").find("#pageNumber").val(this.pageNumber); //表单隐藏的页数赋值默认20
				var queryParam=$("#queryform").serialize(); //表单序列化
			    return queryParam; 							//返回表单
			},
			responseHandler: function(res) {	//在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！		
                return {
                    "total": res.data.pageInfo.total, //总页数
                    "rows": res.data.pageInfo.list    //数据
                 };
            },
			columns:[{
				 field :'lId',  //域值
		         title :'主键'	//内容
			},{
				 field :'strName',
		         title :'活动名称'
			},{
				 field :'lMoney',
		         title :'满额(单位分)',
			},{
				 field :'strRange',
		         title :'应用范围 ',
		         formatter:function(value,row,index){//通过formatter可以自定义列显示的内容  alue：当前field的值，即id row：当前行的数据
                     if(value==1){
                    	 return '扫码支付';
                     }
                     if(value==3){
                    	 return '团购';
                     }
                 } 
			},{
				 field :'nBizType',
		         title :'业务分类'
			},{ 
				field:'#', 
				title: '操作', 
				align:'center', 
				formatter:function(value,row,index){ 
					var button = "<a href='javascript:;' onclick='edit(\""+row.lId+"\")'>修改</a> ";
						button+= "<a href='javascript:;' onclick='drop(\""+row.lId+"\")'>删除</a> ";
						button+= "<a href='javascript:;' onclick='view(\""+row.lId+"\")'>查看</a>";
					return  button; 
				} 
			}]
    	 });
    });
   
    //跳转到修改页面
    function edit(lId){
    	window.location.href='${ctx}/user/income/incomeActivity/getEdit.do?lId='+lId;
    }
    
    //弹窗到查看页面
	function view(lId) {
		layer.open({
			type : 2,
			title : '修改',
			shadeClose : false,
			skin : 'layui-layer-demo',
			shade : 0.2,
			area : [ '550px', '600px' ],
			content : '${ctx}/user/income/incomeActivity/view.do?lId='+lId,
			btn: ['确认'],
			yes: function(index){
				layer.close(index);
			},
		});
	}
	
    //删除功能
	function drop(lId){
		layer.confirm('确认删除', function(index){
			$.ajax({
				type: "post",
				url: "${ctx}/user/income/incomeActivity/drop.do",
				data: {lId:lId},
				success: function(dat) {
					if(dat.code==0){
						$('#date-table').bootstrapTable('refresh');
						layer.msg("删除成功");
					}else{
						layer.msg("删除失败");
					}
				}
			});
		});
	}

    //根据条件查询功能
	function query(){
		$('#date-table').bootstrapTable('refresh'); //刷新页面
	}
</script>
</body>
</html>
