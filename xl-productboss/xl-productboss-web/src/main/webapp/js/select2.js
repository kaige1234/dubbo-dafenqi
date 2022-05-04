//用于国内的出发国家/地区/城市============start=============
var fillOption	= function(el_id,items,selected_id) {
	
	var el	= $('#'+el_id); 
	var json	= items; 
	var selected_index	= "";
	if(typeof(selected_id) != "undefined"){
      el.empty();
	}
	if (json) {
		var index	= 1;
		$.each(json , function(k , v) {
			var option	= '<option value="'+k+'">'+v+'</option>';
			el.append(option);
			if (k == selected_id) {
				selected_index	= k;
			}
			index++;
		})
	}
	if($.inArray(",", selected_id)==-1){
       el.select2().val(selected_index).trigger("change");
	}else{
		var si=selected_id.split(",");
        el.select2().val(si).trigger("change");
	}
}


function showLocation(id1,id2,id3,v1,v2,v3) {

	var title	= ['请选择国家' , '请选择省/地区' , '请选择城市'];
	$.each(title , function(k , v) {
		title[k]	= '<option value="">'+v+'</option>';
	})
	
	$('#'+id1).append(title[0]);
	$('#'+id2).append(title[1]);
	$('#'+id3).append(title[2]);

    $('#'+id1).select2();
	$('#'+id2).select2();
	$('#'+id3).select2();

	$("#"+id1).on("change",function(e){
		if($("#"+id1).val()!=""){
			$('#'+id2).empty();
		    fillOption(id2, gnitems1[$('#'+id1).val()]);
		    $('#'+id2).change();
		}else{
            $("#"+id2).select2().val("").trigger("change");
		}
        
    });

    $("#"+id2).on("change",function(e){
		if($("#"+id2).val()!=""){
			$('#'+id3).empty();
		    fillOption(id3, gnitems2[$('#'+id1).val()+','+ $('#'+id2).val()]);
		}else{
			if($("#"+id3).attr("multiple")!='multiple'){
			  $("#"+id3).select2().val("").trigger("change");
			}
		}
        
    });


	$('#'+id3).on("change",function(e){
		$('input[name=location_id]').val($(this).val());
	})
	

    if (v1) {
		fillOption(id1, gnitems0, v1);
		if (v2) {
			fillOption(id2, gnitems1[v1] , v2);
			if (v3) {
				fillOption(id3, gnitems2[v1+','+v2] , v3);
			}
		}
		
	} else {
		fillOption(id1,gnitems0);
	}
}
//用于国内的出发国家/地区/城市=============stop===========

//用于国内的目的地国家/地区/城市=============start===========

function tognshowLocation(id1,id2,id3,v1,v2,v3) {
	var title	= ['请选择大区' , '请选择省份' , '请选择城市'];
	$.each(title , function(k , v) {
		title[k]	= '<option value="">'+v+'</option>';
	})
	
	$('#'+id1).append(title[0]);
	$('#'+id2).append(title[1]);
	$('#'+id3).append(title[2]);

    $('#'+id1).select2();
	$('#'+id2).select2();
	$('#'+id3).select2();

	$("#"+id1).on("change",function(e){
		if($("#"+id1).val()!=""){
			$('#'+id2).empty();
			fillOption(id2, gnitems1[$('#'+id1).val()]);
		    $('#'+id2).change();
		}else{
            $("#"+id2).select2().val("").trigger("change");
		}
        
    });

    $("#"+id2).on("change",function(e){
		if($("#"+id2).val()!=""){
			$('#'+id3).empty();
			fillOption(id3, gnitems2[$('#'+id1).val()+','+ $('#'+id2).val()],'');
		}else{
			if($("#"+id3).attr("multiple")!='multiple'){
			  $("#"+id3).select2().val("").trigger("change");
			}
		}
        
    });


	$('#'+id3).on("change",function(e){
		$('input[name=location_id]').val($(this).val());
	})
	

    if (v1) {
    	fillOption(id1, gnitems0, v1);
		if (v2) {
			fillOption(id2, gnitems1[v1] , v2);
			if (v3) {
				fillOption(id3, gnitems2[v1+','+v2] , v3);
			}
		}
		
	} else {
		fillOption(id1,gnitems0);
	}
}
//用于国内的目的地国家/地区/城市=============stop===========

//用于出境的出发国家/地区/城市=============start===========
function gjshowLocation(id1,id2,id3,v1,v2,v3,num) {
	var title	= ['请选择国家' , '请选择省/地区' , '请选择城市'];
	$.each(title , function(k , v) {
		title[k]	= '<option value="">'+v+'</option>';
	})
	
	$('#'+id1).append(title[0]);
	$('#'+id2).append(title[1]);
	$('#'+id3).append(title[2]);

    $('#'+id1).select2();
	$('#'+id2).select2();
	$('#'+id3).select2();

	$("#"+id1).on("change",function(e){
		if($("#"+id1).val()!=""){
			$('#'+id2).empty();
			fillOption(id2, gjitems4[$('#'+id1).val()]);
		    $('#'+id2).change();
		}else{
            $("#"+id2).select2().val("").trigger("change");
		}
        
    });

    $("#"+id2).on("change",function(e){
		if($("#"+id2).val()!=""){
			$('#'+id3).empty();
			fillOption(id3, gjitems5[$('#'+id1).val()+','+ $('#'+id2).val()]);
		}else{
			if($("#"+id3).attr("multiple")!='multiple'){
			  $("#"+id3).select2().val("").trigger("change");
			}
		}
        
    });


	$('#'+id3).on("change",function(e){
		$('input[name=location_id]').val($(this).val());
	})
	

    if (v1) {
    	fillOption(id1, gjitems3, v1);
		if (v2) {
			fillOption(id2, gjitems4[v1] , v2);
			if (v3) {
				fillOption(id3, gjitems5[v1+','+v2] , v3);
			}
		}
		
	} else {
		fillOption(id1,gjitems3);
	}
}





