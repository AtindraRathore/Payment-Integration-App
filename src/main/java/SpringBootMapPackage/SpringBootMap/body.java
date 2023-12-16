package SpringBootMapPackage.SpringBootMap;


public class body {

    private String paymentID;
    private String  mailId;
    body(){

    }

    public body(String mailId) {

        this.mailId = mailId;
    }

    public String getpaymentId(){
        return this.paymentID;
    }
    public String getmailID(){
        return this.mailId;
    }

}
