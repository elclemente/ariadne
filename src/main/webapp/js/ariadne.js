function showTagEditor() {
	$("#tagEditorTabSelector").addClass("active");
	$("#filterEditorTabSelector").removeClass("active");
	$("#toolTabSelector").removeClass("active");
	$("#filterEditor").addClass("hide");
	$("#tagEditor").removeClass("hide");
	
}

function showFilterEditor() {
	$("#filterEditorTabSelector").addClass("active");
	$("#tagEditorTabSelector").removeClass("active");
	$("#toolTabSelector").removeClass("active");
	$("#tagEditor").addClass("hide");
	$("#filterEditor").removeClass("hide");
}
