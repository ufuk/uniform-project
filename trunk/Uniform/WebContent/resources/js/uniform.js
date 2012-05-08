// for automatic block and unblock while ajax request is processing 
$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

$(document).ready(function () {
	// for user -dropdown- menu
	$('.dropdown-toggle').dropdown();
	
	// for tooltip links
	$("[rel=tooltip]").tooltip();
	
	// for block ui while ajax request is processing
	$(".submitDiv > a").each(function(index) {
		$(this).click(function() {
			$.blockUI({
	        	message: '<img src="resources/img/loader.gif"></img>',
	        	css: { 
	                border: 'none',
	                padding: '15px',
	                backgroundColor: '',
	                opacity: 1,
	                cursor: ''
	            },
	            overlayCSS: {
	            	backgroundColor: '#000',
	            	opacity: 0.05,
	            	cursor: ''
	            }
	        });
		});
	});
});
