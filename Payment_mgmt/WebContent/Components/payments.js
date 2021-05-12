$(document).ready(function(){
	if($("#alertSuccess").text().trim() == "")
		{
			$("#alertSuccess").hide();
		}
	$("#alertError").hide();
});

//SAVE 
$(document).on("click", "#btnSave", function(event) 
{
			$("#alertSuccess").text("");
			$("#alertSuccess").hide("");
			$("#alertError").text("");
			$("#alertError").hide("");
		
			//form validation
			var status = validatePaymentForm();
			if (status != true)
				{
					$("#alertError").text(status);
					$("#alertError").show();
					return;
				}

			$("#formPayment").submit();
			
			var type = ($("#hidPaymentIdSave").val() == "") ? "POST" : "PUT"; 
			$.ajax({  
					url : "PaymentAPI",  
					type : "post",  
					data : $("#formPayment").serialize(),  
					dataType : "text",  
					complete : function(response, status)  
					{   
						onPaymentSaveComplete(response.responseText, status);
					} 
				});
			
			
			
});

function onPaymentSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divPaymentGrid").html(resultSet.data);   
			} 
		else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} 
	else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
		} else  
		{   
			$("#alertError").text("Unknown error while saving..");   
			$("#alertError").show();  
		} 
	
	$("#hidPaymentIdSave").val(""); 
	$("#formPayment")[0].reset();

 
}

//Update
$(document).on("click", ".btnUpdate", function(event) {
	 $("#hidPaymentIdSave").val($(this).closest("tr").find('#hidPaymentIdUpdate').val());
	 $("#userID").val($(this).closest("tr").find('td:eq(1)').text()); 
	 $("#projectID").val($(this).closest("tr").find('td:eq(2)').text()); 
	 $("#method").val($(this).closest("tr").find('td:eq(3)').text()); 
	 $("#amount").val($(this).closest("tr").find('td:eq(4)').text());
})

//DELETE
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "PaymentAPI",   
		type : "DELETE",   
		data : "PaymentID=" + $(this).data("itemid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onPaymentDeleteComplete(response.responseText, status);   
		}  
	}); 
});
 

function onPaymentDeleteComplete(response, status) 
{  

	
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 
		

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 

			$("#divPaymentGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	} 
	$("#hidPaymentIdSave").val("");  
	$("#formPayment")[0].reset();
	
	
}



//FORM VALIDATE
function validatePaymentForm()
{
	if($("#userID").val().trim() == ""){
		return "insert Use ID.";
	}
	
	if($("#projectID").val().trim() == ""){
		return "insert project ID.";
	}
	
	if($("#method").val().trim() == ""){
		return "Insert method.";
	}
	
	if($("#amount").val().trim() == ""){
		return "Insert amount.";
	}

	if (!$.isNumeric($("#userID").val().trim())) {
		return "Incorrect user ID";
	}
	
	if (!$.isNumeric($("#projectID").val().trim())) {
		return "Incorrect project ID";
	}
	
	if (!$.isNumeric($("#amount").val().trim())) {
		return "Incorrect amount";
	}
	
	return true;
}




  









