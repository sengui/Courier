/** 
 * 加载form插件，
 * 可以将json数据赋值给form，
 * 
 * **/
(function($){  
    $.fn.extend({  
    	//传入要赋值的json对象
        initForm:function(options){  
        	options = options||{};
            //设置参数  
            var form = this;  
            var jsonValue;  
            var beforeKey='';
            
            //存在key
            if(options.key != undefined){
            	jsonValue = options.data;
            	beforeKey = options.key + '.';
            }else{
            	jsonValue = options;  
            }
            
            //如果传入的json字符串，将转为json对象  
            if($.type(jsonValue) === "string"){  
                jsonValue = $.parseJSON(jsonValue);  
            }  
            //如果传入的json对象为空，则不做任何操作  
            if(!$.isEmptyObject(jsonValue)){  
                var debugInfo = "";  
                $.each(jsonValue,function(key,value){ 
                	var nowKey = beforeKey+key;
                    var formField = form.find("[name='"+nowKey+"']");  
                    if($.type(formField[0]) != "undefined"){  
                        var fieldTagName = formField[0].tagName.toLowerCase();  
                        if(fieldTagName == "input"){  
                            if(formField.attr("type") == "radio"){  
                                $("input:radio[name='"+nowKey+"'][value='"+value+"']").prop("checked","checked");  
                            }else if(formField.attr("type") == "checkbox"){
                            	if ($.type(value) === "string") {
                            		var arr = value.split(',');
                            		for(var i=0;i<arr.length;i++){
                            			$("input:checkbox[name='"+nowKey+"'][value='"+arr[i]+"']").prop("checked","checked");
                            		}
                            	}else if ($.type(value) == 'array') {
                            		for(var i=0;i<value.length;i++){
                            			$("input:checkbox[name='"+nowKey+"'][value='"+value[i]+"']").prop("checked","checked");
                            		}
                            	}
                            }else {  
                                formField.val(value);  
                            }  
                        } else if(fieldTagName == "select"){  
                            //do something special  
                            formField.val(value);  
                        } else if(fieldTagName == "textarea"){  
                            //do something special  
                            formField.val(value);
                        } else {  
                            formField.val(value);  
                        }  

                    }else if($.type(value)!='string'){
                    	var data={
                    		key:nowKey,
                    		data:value
                    	};
                    	$(form).initForm(data);
                    }
                }); 
            }  
            return form;    //返回对象，提供链式操作  
        },  
        //导入json,通过设置url,data参数动态从后台获取数据并赋值给form
    	loadForm:function(options){
    		options = options||{};
    		
    		//设置参数
    		var url = options.url||'';
    		var data = options.data||{};
    		var form = this;
    		//获取数据
    		/*$.post(url,data,function(json){
    			$(form).initForm(json);
    		});*/
    		$.ajax({
    			url:url,
    			type:'post',
    			data:data,
    			dataType:'json',
    			async: false,
    			success:function(json){
    				$(form).initForm(json.data);
    			}
    		});
    	}
    });
})(jQuery);