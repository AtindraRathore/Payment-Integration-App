const paymentStart=()=>{

    console.log("payment started");
    var amount = document.getElementById('payment-field').value;
     var useremail = document.getElementsByName("useremail").value;
    console.log("this is"+amount);
    if(amount == null || amount == ''){
        swal("amount is required !!");
        return;
    }
     $.ajax(
         {
          url:'/user/create_order',
         data:JSON.stringify({amount:amount,info:'order_request'}),
          contentType:'application/json',
          type:'POST',
        dataType:'json',
         success:function(response){
           console.log(response);
           console.log("Success method");
           var options = {
    "key": 'rzp_test_i56NyuHJff8lqH',
    "amount": response.amount,
    "currency": "INR",
    "order_id": response.id,
    "name": "Atindra Corp",
    "description": "Donation",
    "payment_Id" : response.razorpay_payment_id,
    "useremail" : response.useremail
    "image": "https://cdn.razorpay.com/logos/F9Yhfb7ZXjXmIQ_medium.jpg",
    "handler": function (response){
        console.log(response.razorpay_payment_id);
        console.log(response.razorpay_order_id);
        console.log(response.razorpay_signature)
        alert(response.razorpay_payment_id+" Payment Successfull");
    },
    "prefill": {
        "name": "",
        "email": "",
        "contact": ""
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
let rzp = new Razorpay(options);

  rzp.on('payment.failed',function(response){
  console.log(response.error.code);
  console.log(response.error.description);
   console.log(response.error.source);
   console.log(response.error.reason);
   console.log(response.error.metadata.payment._id);
  swal("Oops! payment Failed");
 })
 rzp.open();
           },
           error:function(xhr,error,status){
           console.log(xhr);
            console.log(error);
            console.log(status);
           swal("something went wrong !!");
           }

       }

    )
}