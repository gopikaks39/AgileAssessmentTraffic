package org.friendassessment.traffic;
public class EChallan {private final String id;private final Offence offence;private final double amount;private PaymentState state=PaymentState.UNPAID;
 public EChallan(String id,Offence offence,double amount){if(id==null||id.isBlank()||offence==null||amount<=0)throw new IllegalArgumentException("Invalid challan");this.id=id;this.offence=offence;this.amount=amount;}
 public String id(){return id;}public Offence offence(){return offence;}public double amount(){return amount;}public PaymentState state(){return state;}
 public void markPaid(){if(state==PaymentState.PAID)throw new TrafficException("Challan already paid");state=PaymentState.PAID;}
}
