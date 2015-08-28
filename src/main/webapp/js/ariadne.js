
$(function()
{
	$("#activeScan").text("init")
	
	$.ajax({
		url : "http://localhost:8080/ariadne/service/scan", 
		type : "GET",
		success : function(data) {
			$("#activeScan").text("success: " + data.id);			
		}
	}); 
});

