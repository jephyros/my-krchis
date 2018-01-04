$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e){	
	e.preventDefault();   // 이벤트 맊기
	//console.log("click_me");
	
	var queryString =$(".answer-write").serialize();
	console.log("query :" + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url : " + url);
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess});	
}

function onError(){
	
}

function onSuccess(data,status){
	console.log(data);
	var answertemplate = $("#answerTemplate").html();
	var template = answertemplate.format(data.writer.userId,data.createDateFormat,data.contents,data.id,data.question.id);
	$(".qna-comment-slipp-articles").prepend(template);
	$(".answer-write textarea").val("");
}
//삭제
$(".link-delete-article").click(function(e){
	e.preventDefault();   // 이벤트 맊기
	var deletebtn = $(this);
	var url =deletebtn.attr("href")
	console.log("queryurl : " +  url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr,status){
			console.log('error');
			
		},
		success : function (data,status){
			console.log(data);
			if (data.valid){
				deletebtn.closest("article").remove();
			}else{
				alert(data.errorMessage);
			}
		}
	});
	
});




String.prototype.format = function() {
	  var args = arguments;
	  return this.replace(/{(\d+)}/g, function(match, number) {
	    return typeof args[number] != 'undefined'
	        ? args[number]
	        : match
	        ;
	  });
	};