<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>H+ 后台主题UI框架 - 基本表单</title>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${ctx}/css/font-awesome.css?v=4.4.0">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<link href="${ctx}/css/style.min.css?v=4.1.0" rel="stylesheet">
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/content.min.js?v=1.0.0"></script>
<script src="${ctx}/js/layer/layer.js"></script>
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function () {
        $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green",});
    });
    
    function sub(){
    	layer.confirm('确认保存修改', function(index){
    		$("#form1").submit();
    	});
    }
</script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</head>
<body class="gray-bg">
		<div class="ibox-content">
			<form method="post" id="form1" class="form-horizontal" action="${ctx}/user/income/incomeActivity/edit.do">
				<div class="form-group">
					<label class="col-sm-2 control-label">主键：</label>
					<div class="col-sm-5">
						<input type="text" placeholder="请输入主键" value="${incomeactivity.lId}" class="form-control" name="lId" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">活动名称：</label>
					<div class="col-sm-5">
						<input type="text" placeholder="请输入活动名称" value="${incomeactivity.strName}" class="form-control" name="strName">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">满额(单位分)：</label>
					<div class="col-sm-5">
						<input type="text" placeholder="请输入满额(单位分)" value="${incomeactivity.lMoney}" class="form-control" name="lMoney" >
					</div>
				</div>
				<div class="form-group">
                    <label class="col-sm-2 control-label">应用范围 ：</label>
                    <div class="col-sm-5">
                        <select class="form-control m-b" name="strRange">
		                    <option value="1" <c:if test="${incomeactivity.nBizType=='1'}">selected</c:if>>扫码支付</option>
		                    <option value="3" <c:if test="${incomeactivity.nBizType=='2'}">selected</c:if>>团购</option>
                        </select>
                    </div>
                </div>
				<div class="form-group">
					<label class="col-sm-2 control-label">业务分类：</label>
					<div class="col-sm-5">
						<input type="text" placeholder="请输入业务分类" value="${incomeactivity.nBizType}" class="form-control" name="nBizType">
					</div>
				</div>
				<div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <button type="button" class="btn btn-primary" onclick="sub()" >保存修改</button>
                        <button type="button" class="btn btn-white" onclick="javascript:window.location.href='${ctx}/user/income/incomeActivity/list.do'">取消</button>
                    </div>
                </div>
			</form>
		</div>
</body>
</html>
