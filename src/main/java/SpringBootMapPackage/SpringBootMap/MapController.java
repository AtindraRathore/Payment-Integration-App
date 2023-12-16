package SpringBootMapPackage.SpringBootMap;

import com.itextpdf.text.DocumentException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Properties;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FilterOutputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Properties;



@RestController
@RequestMapping("/user")
public class MapController {

@Autowired
JavaMailSender sender;

 String amount,PaymentId,uemail;
    @GetMapping("/showMap")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @PostMapping("/create_order")
    @ResponseBody
    public String createOrder(@RequestBody HashMap<String,Object> data) throws RazorpayException {
     System.out.println("Function hit");
        int amt= Integer.parseInt(data.get("amount").toString());
       var client = new RazorpayClient("rzp_test_i56NyuHJff8lqH","QIlHGAm09aJoF5rtxKmfDcBk");
    //old    key="rzp_test_aQ3M58fE39RqzO",secret="YYxzAx0xzEmMUxss0C4bnbFE"
        JSONObject obj = new JSONObject();
        obj.put("amount",amt*100);
        obj.put("currency","INR");
        obj.put("receipt","order_rcptid_11");
        Order order = client.orders.create(obj);
            amount = data.get("amount").toString();
        System.out.println(order);
        return order.toString();
    }




//    Document document=new Document();
//          PdfWriter.getInstance(document,new FileOutputStream("Invoice.pdf"));
//          document.open();
//          document.add(new Paragraph("Invoice message"));
//          document.add(new Paragraph("Invoice generated on "+new Date()));
//          document.add(new Paragraph(invoicemessage));
//    Message message=new MimeMessage(sender.createMimeMessage(),true);

    @PostMapping("/sendmail")
    public String sendmail(@RequestBody body bd) throws DocumentException, FileNotFoundException, MessagingException {
//         this.PaymentId = bd.getpaymentId();
        String invoicemessage = "Hi," + "\n"+
      "I,m just getting in touch to follow up our invoice"+"\n"+"Your Payment details-"+"\n"+"From:Atindra Corp"+"\n"+"Amount : "+amount+"\n"+"This invoice mail is confirmation of amount is deducted from bank account and payment successfull";

        System.out.println("sendemail method hit");

          String to = bd.getmailID();

        // Sender's email ID needs to be mentioned
        String from = "atindrasinghrathore1906@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("atindrasinghrathore1906@gmail.com", "qdvxqlwuenxrppij");
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Invoice Message From Atindra Corp !");

            // Now set the actual message
            message.setText(invoicemessage);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "Sent message successfully";
    }
}
