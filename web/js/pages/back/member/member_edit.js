window.onload = function() {
	bind(ele("member.mid"),"blur",function(){
		validateMid() ;
	}) ;
	bind(ele("member.tel"),"blur",function(){
		validateTel() ;
	}) ;
	bind(ele("myform"),"submit",function(e){
		if (validateForm()) {
			this.submit() ;
		} else {
			stopFormSubmit(e) ;
		}
	}) ;
} 
function validateForm() {
	return validateMid() & validateTel() ;
}
function validateMid() {
	return validateEmpty("member.mid") ;
}
function validateTel() {
	return validateEmpty("member.tel") ;
}